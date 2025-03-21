package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RemoveTaskController {

    @FXML
    private TextField idField;

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
        String taskIdText = idField.getText();
        if (taskIdText.isEmpty()) {
            showAlert("Error", "Task ID cannot be empty.");
            return;
        }

        try {
            int taskId = Integer.parseInt(taskIdText);
            if (taskManagement.searchTask(taskId) == null) {
                showAlert("Error", "Task ID not found.");
            } else {
                taskManagement.removeTask(taskId);
                closeWindow();
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Task ID format. Please enter a number.");
        }
        if (appController != null) {
            appController.refreshSimpleTaskTable();
            appController.refreshComplexTaskTable();
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
