package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.Utility;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.*;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AppController {

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> columnIdEm;
    @FXML
    private TableColumn<Employee, String> columnName;
    @FXML
    private TableColumn<Employee, String> columnTasks;
    @FXML
    private TableColumn<Employee, Integer> columnWorkedHours;

    @FXML
    private TableView<SimpleTask> simpleTaskTable;
    @FXML
    private TableColumn<SimpleTask, Integer> columnIdTaskS;
    @FXML
    private TableColumn<SimpleTask, String> columnStatusS;
    @FXML
    private TableColumn<SimpleTask, String> columnStart;
    @FXML
    private TableColumn<SimpleTask, String> columnEnd;

    @FXML
    private TableView<ComplexTask> complexTaskTable;
    @FXML
    private TableColumn<ComplexTask, Integer> columnIdTaskC;
    @FXML
    private TableColumn<ComplexTask, String> columnStatusC;
    @FXML
    private TableColumn<ComplexTask, String> columnSubTasks;

    private TaskManagement taskManagement;
    private ObservableList<Employee> employeeList;
    private ObservableList<SimpleTask> simpleTaskList;
    private ObservableList<ComplexTask> complexTaskList;

    @FXML
    public void initialize() {
        try {
            // Ensure taskManagement is not null
            taskManagement = SerializationOperations.loadData("project.dat");
            if (taskManagement == null) {
                System.out.println("File not found. Initializing task management...");
                taskManagement = new TaskManagement(false);
            }

            employeeList = FXCollections.observableArrayList(taskManagement.getMap().keySet());
            employeeTable.setItems(employeeList);


            // Employee Table Columns
            columnIdEm.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getIdEmployee()).asObject());
            columnName.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getName()));

            columnTasks.setCellValueFactory(cellData -> {
                List<Task> tasks = taskManagement.getMap().getOrDefault(cellData.getValue(), new ArrayList<>());
                String taskListString = tasks.stream()
                        .map(t -> "ID: " + t.getIdTask() + " (" + t.getStatusTask() + ")")
                        .reduce((a, b) -> a + ", " + b).orElse("No Tasks");
                return new SimpleStringProperty(taskListString);
            });
            columnWorkedHours.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(taskManagement.calculateEmployeeWorkDuration(cellData.getValue().getIdEmployee())).asObject()
            );


            // Initialize Task Lists
            simpleTaskList = FXCollections.observableArrayList(taskManagement.getSimpleTaskList());
            complexTaskList = FXCollections.observableArrayList(taskManagement.getComplexTaskList());

            for (List<Task> tasks : taskManagement.getMap().values()) {
                for (Task task : tasks) {
                    if (task instanceof SimpleTask) {
                        simpleTaskList.add((SimpleTask) task);
                    } else if (task instanceof ComplexTask) {
                        complexTaskList.add((ComplexTask) task);
                    }
                }
            }

            if (simpleTaskList.isEmpty()) {
                System.out.println("No simple tasks found.");
            }
            if (complexTaskList.isEmpty()) {
                System.out.println("No complex tasks found.");
            }

            // 🛠️ Setup Simple Task Table Columns
            columnIdTaskS.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getIdTask()).asObject());
            columnStatusS.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getStatusTask()));
            columnStart.setCellValueFactory(cellData ->
                    new SimpleStringProperty(Integer.toString(cellData.getValue().getStartHour())));
            columnEnd.setCellValueFactory(cellData ->
                    new SimpleStringProperty(Integer.toString(cellData.getValue().getEndHour())));
            simpleTaskTable.setItems(simpleTaskList);

            // 🛠️ Setup Complex Task Table Columns
            columnIdTaskC.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getIdTask()).asObject());
            columnStatusC.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getStatusTask()));
            columnSubTasks.setCellValueFactory(cellData -> {
                List<Task> subTasks = cellData.getValue().getSubtasks();
                String subTaskString = subTasks.stream()
                        .map(t -> "ID: " + t.getIdTask() + " (" + t.getStatusTask() + ")")
                        .reduce((a, b) -> a + ", " + b).orElse("No Subtasks");
                return new SimpleStringProperty(subTaskString);
            });
            complexTaskTable.setItems(complexTaskList);

        } catch (Exception e) {
            System.err.println("initialize failed");
            e.printStackTrace();
        }
    }
    @FXML
    private Button addTaskButton, addSubtaskButton, removeTaskButton, assignTaskButton;

    @FXML
    private Button modifyTaskButton, taskNumberButton, showButton, resetViewButton;


    @FXML
    private void handleAddEmployee(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/AddEmployeeView.fxml"));
        Parent root = loader.load();

        AddEmployeeController addEmployeeController = loader.getController();

        addEmployeeController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Employee");
        stage.show();
    }

    @FXML
    private void handleAddSimpleTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/AddSimpleTaskView.fxml"));
        Parent root = loader.load();

        AddSimpleTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Simple Task");
        stage.show();
    }

    @FXML
    private void handleAddComplexTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/AddComplexTaskView.fxml"));
        Parent root = loader.load();

        AddComplexTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Complex Task");
        stage.show();
    }

    @FXML
    private void handleAddSubtask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/AddSubTaskView.fxml"));
        Parent root = loader.load();

        AddSubTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Add Subask");
        stage.show();

    }

    @FXML
    private void handleRemoveTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/RemovetaskView.fxml"));
        Parent root = loader.load();

        RemoveTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Remove Task");
        stage.show();
    }

    @FXML
    private void handleAssignTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/AssignTaskView.fxml"));
        Parent root = loader.load();

        AssignTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Assign TaskTask to Employee");
        stage.show();
    }


    @FXML
    private void handleModifyTask(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/ModifyTaskView.fxml"));
        Parent root = loader.load();

        ModifyTaskController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modify Task Status");
        stage.show();
    }

    @FXML
    private void handleTaskNumber(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/pt2025_30424_ghinet_ioanateodora_assignment_1/TaskNumberView.fxml"));
        Parent root = loader.load();

        TaskNumberController addTaskController = loader.getController();
        addTaskController.setAppController(this, taskManagement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Handle Task Number");
        stage.show();
    }


    @FXML
    private void handleShowWork40h(ActionEvent event) {
        if (taskManagement == null) {
            System.err.println("TaskManagement is NULL.");
            return;
        }

        List<Employee> filteredEmployees = Utility.filterEmployeesByWorkHours(taskManagement, 40);

        filteredEmployees.forEach(emp -> System.out.println("ID: " + emp.getIdEmployee() + ", Name: " + emp.getName() + ", Hours Worked: " + taskManagement.calculateEmployeeWorkDuration(emp.getIdEmployee())));


        javafx.application.Platform.runLater(() -> {
            employeeList.clear();
            employeeList.addAll(filteredEmployees);
            employeeTable.setItems(employeeList);
            employeeTable.refresh();
        });
    }


    public void refreshEmployeeTable() {
        if (employeeTable == null) {
            System.err.println("employeeTable is NULL when trying to refresh.");
            return;
        }

        if (taskManagement == null) {
            System.err.println(" taskManagement is NULL when trying to refresh.");
            return;
        }

        if (employeeList == null) {
            System.out.println("Initializing employeeList...");
            employeeList = FXCollections.observableArrayList();
        }

        // Ensure update happens on JavaFX thread
        javafx.application.Platform.runLater(() -> {
            employeeList.setAll(taskManagement.getMap().keySet());
            employeeTable.setItems(employeeList);
            employeeTable.refresh();
        });}

    public void refreshSimpleTaskTable() {
        if (simpleTaskTable == null) {
            System.err.println("simpleTaskTable is NULL when trying to refresh.");
            return;
        }
        Platform.runLater(() -> {
            List<SimpleTask> tasks = taskManagement.getSimpleTaskList();
            simpleTaskList.clear();
            simpleTaskList.addAll(tasks.stream().distinct().toList());

            simpleTaskTable.setItems(simpleTaskList);
            simpleTaskTable.refresh();
        });
    }

    public void refreshComplexTaskTable() {
        if (complexTaskTable == null) {
            System.err.println("complexTaskTable is NULL when trying to refresh.");
            return;
        }
        Platform.runLater(() -> {
            List<ComplexTask> tasks = taskManagement.getComplexTaskList();
            complexTaskList.clear();
            complexTaskList.addAll(tasks.stream().distinct().toList());

            complexTaskTable.setItems(complexTaskList);
            complexTaskTable.refresh();
        });
    }
}