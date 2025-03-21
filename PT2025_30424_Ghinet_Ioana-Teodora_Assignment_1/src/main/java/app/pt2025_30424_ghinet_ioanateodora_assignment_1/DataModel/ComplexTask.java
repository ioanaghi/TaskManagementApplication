package app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Task> subtasks;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        subtasks = new ArrayList<>();
    }

    public List<Task> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Task> subtasks) {
        this.subtasks = subtasks;
    }

    public int estimateDuration() {
        int duration = 0;
        for (Task subtask : subtasks) {
            duration += subtask.estimateDuration();
        }
        return duration;
    }

    public void addTask(Task subtask) {
        subtasks.add(subtask);
    }

    public void deleteTask(Task subtask) {
        subtasks.remove(subtask);
    }

    @Override
    public String toString() {
        return "ComplexTask{" +
                "idTask=" + getIdTask() +
                ", status=" + getStatusTask() +
                ", subTasks=" + subtasks +
                '}';
    }
}