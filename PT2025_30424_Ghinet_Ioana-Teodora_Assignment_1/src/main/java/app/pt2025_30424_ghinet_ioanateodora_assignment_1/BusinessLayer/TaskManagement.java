package app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.*;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManagement implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<Employee, List<Task>> map=new HashMap<>();
    private List<Task> taskList=new ArrayList<>();

    public TaskManagement(boolean loadFromFile) {
        this.map = new HashMap<>();
        this.taskList = new ArrayList<>();
        if (loadFromFile) {
            TaskManagement loadedData = SerializationOperations.loadData("project.dat");
            this.map.putAll(loadedData.getMap());
            this.taskList.addAll(loadedData.getTaskList());
        }
    }

    public HashMap<Employee, List<Task>> getMap() {
        return map;
    }

    public void setMap(HashMap<Employee, List<Task>> map) {
        this.map = map;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }


    public void assignTaskToEmployee(int idEmployee, Task task) {
        Employee employee = searchEmployee(idEmployee);
        if (employee == null) {
            System.out.println("Employee not found");
            return;
        }
        map.putIfAbsent(employee, new ArrayList<>());

        if (!map.get(employee).contains(task)) {
            employee.addTask(task);
            map.get(employee).add(task);
        } else {
            System.out.println("Task already assigned to this employee.");
        }

        SerializationOperations.saveData(this, "project.dat");
    }

    public Employee searchEmployee(int idEmployee) {
        Employee employee = null;
        for (Employee e : map.keySet()) {
            if (e.getIdEmployee() == idEmployee) {
                employee = e;
            }
        }
        return employee;
    }

    public int calculateEmployeeWorkDuration(int idEmployee){
        Employee employee = searchEmployee(idEmployee);
        if (employee == null) {
            System.out.println("Employee not found");
            return -1;
        }
        else{
            int workDuration = 0;
            for (Task t : map.get(employee)) {
                if(t.getStatusTask()=="Completed")
                    workDuration+= t.estimateDuration();
            }
            SerializationOperations.saveData(this, "project.dat");
            return workDuration;
        }

    }

    public void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = searchEmployee(idEmployee);
        if (employee == null) {
            System.out.println("Employee not found");
            return;
        }
        List<Task> tasks = map.get(employee);
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("Employee has no assigned tasks");
            return;
        }

        Task taskToModify = null;
        for (Task task : tasks) {
            if (task.getIdTask() == idTask) {
                taskToModify = task;
                break;
            }
        }

        if (taskToModify == null) {
            System.out.println("Task not found");
            return;
        }

        taskToModify.setStatusTask("Completed");

        SerializationOperations.saveData(this, "project.dat");
    }

    public void modifyTaskStatus(int idTask) {
        int ok=0;
        for(Task t :taskList){
            if(t.getIdTask() == idTask){
                ok=1;
                t.setStatusTask("Completed");
                break;
            }
        }
        if(ok==0){
            System.out.println("Task not found");
            return;
        }
        else
            SerializationOperations.saveData(this, "project.dat");

    }

    public void addEmployee(int idEmployee, String name) {
        if(searchEmployee(idEmployee)==null){
            Employee employee = new Employee(idEmployee, name);
            map.putIfAbsent(employee, new ArrayList<>());
            SerializationOperations.saveData(this, "project.dat");
        }
        else{
            System.out.println("Employee with this id already exists");
        }
    }
    public void addSimpleTasks(int idTask, String status, int startHour, int endHour) {
        if(searchTask(idTask)==null){
            SimpleTask newSimpleTask = new SimpleTask(idTask, status, startHour, endHour);
            taskList.add(newSimpleTask);
            getSimpleTaskList().add(newSimpleTask);
            SerializationOperations.saveData(this, "project.dat");
            return;
        }

        System.out.println("Task with this id already exists");
        return;
    }

    public Task searchTask(int idTask) {
        for(Task t : taskList){
            if(t.getIdTask() == idTask){
                return t;
            }
        }
        return null;
    }
    public void addComplexTasks(int idTask, String status) {
        if(searchTask(idTask)==null){
            ComplexTask newComplexTask = new ComplexTask(idTask, status);
            taskList.add(newComplexTask);
            getComplexTaskList().add(newComplexTask);
            SerializationOperations.saveData(this, "project.dat");
            return;
        }
        System.out.println("Task with this id already exists");
        return;
    }

    public void addSubtask(int idComplexTask, int idTask, String status) {
        if(searchTask(idComplexTask)==null){
            System.out.println("Task not found");
            return;
        }
        if(searchTask(idTask)!=null){
            System.out.println("Task with this id already exists");
            return;
        }
        ComplexTask t = (ComplexTask)searchTask(idComplexTask);
        addComplexTasks(idTask, status);

        t.addTask(searchTask(idTask));
        SerializationOperations.saveData(this, "project.dat");

    }

    public void addSubtask(int idComplexTask,int idTask, String status,int startHour, int endHour) {
        if(searchTask(idComplexTask)==null){
            System.out.println("Task not found");
            return;
        }
        if(searchTask(idTask)!=null){
            System.out.println("Task with this id already exists");
            return;
        }
        ComplexTask t = (ComplexTask)searchTask(idComplexTask);
        addSimpleTasks(idTask, status, startHour, endHour);

        t.addTask(searchTask(idTask));
        SerializationOperations.saveData(this, "project.dat");

    }

    public void removeTask(int idTask) {
        if(searchTask(idTask)==null){
            System.out.println("Task not found");
            return;
        }
        taskList.removeIf(t -> t.getIdTask() == idTask);

        for (Task task : taskList) {
            if (task instanceof ComplexTask) {
                ((ComplexTask) task).getSubtasks().removeIf(subtask -> subtask.getIdTask() == idTask);
            }
        }

        for (Employee employee : map.keySet()) {
            map.get(employee).removeIf(task -> task.getIdTask() == idTask);
        }
        SerializationOperations.saveData(this, "project.dat");
    }

    public int[] getNrOfTasks(int idEmployee) {
        int[] a={0,0};
        if(searchEmployee(idEmployee)==null){
            System.out.println("Employee not found");
        }
        else {
            Employee employee = searchEmployee(idEmployee);

            for (Task t : map.get(employee)) {
                if (t.getStatusTask().equals("Completed")) {
                    a[0]++;
                }
                if (t.getStatusTask().equals("Not Completed")) {
                    a[1]++;
                }
            }
        }
        return a;
    }
    public List<SimpleTask> getSimpleTaskList() {
        List<SimpleTask> simpleTasks = taskList.stream()
                .filter(task -> task instanceof SimpleTask)
                .map(task -> (SimpleTask) task)
                .distinct()
                .collect(Collectors.toList());

        return simpleTasks;
    }


    public List<ComplexTask> getComplexTaskList() {
        List<ComplexTask> complexTask = taskList.stream()
                .filter(task -> task instanceof ComplexTask)
                .map(task -> (ComplexTask) task)
                .distinct()
                .collect(Collectors.toList());

        return complexTask;
    }
}