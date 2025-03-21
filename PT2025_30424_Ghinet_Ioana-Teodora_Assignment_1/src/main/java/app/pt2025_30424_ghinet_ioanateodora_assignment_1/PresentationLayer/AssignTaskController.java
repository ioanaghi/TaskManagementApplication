package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.Task;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssignTaskController {

    @FXML
    private TextField idFieldEm;

    @FXML
    private TextField idFieldT;

    @FXML
    private Button doneButton;

    private TaskManagement taskManagement;
    private AppController appController; // Reference to main controller

    public void setAppController(AppController appController, TaskManagement taskManagement) {
        this.appController = appController;
        this.taskManagement = taskManagement;
    }

    @FXML
    private void handleDone() {
        String employeeIdText = idFieldEm.getText();
        String taskIdText = idFieldT.getText();

        if (employeeIdText.isEmpty() || taskIdText.isEmpty()) {
            showAlert("Error", "Both Employee ID and Task ID are required.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdText);
            int taskId = Integer.parseInt(taskIdText);

            if (taskManagement.searchEmployee(employeeId) == null) {
                showAlert("Error", "Employee ID not found.");
                return;
            }

            if (taskManagement.searchTask(taskId) == null) {
                showAlert("Error", "Task ID not found.");
                return;
            }
            Task task = taskManagement.searchTask(taskId);

            taskManagement.assignTaskToEmployee(employeeId, task);
            SerializationOperations.saveData(taskManagement,"project.dat");
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid ID format. Please enter numeric values.");
        }
        if (appController != null) {
            appController.refreshEmployeeTable();
        } else {
            System.err.println("Cannot refresh task tables.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
