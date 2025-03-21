package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.*;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddComplexTaskController {
    @FXML
    private TextField taskIdField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private Button doneButton;

    private TaskManagement taskManagement;
    private AppController appController; // Reference to main controller

    public void setAppController(AppController appController, TaskManagement taskManagement) {
        this.appController = appController;
        this.taskManagement = taskManagement;
    }

    @FXML
    private void handleDone(ActionEvent event) {
        try {
            if (taskManagement == null) {
                showAlert("Error", "Task management system is not initialized.");
                return;
            }

            // Read user inputs
            String idText = taskIdField.getText().trim();
            String status = statusChoiceBox.getValue();

            if (idText.isEmpty() || status == null) {
                showAlert("Validation Error", "Please enter all  fields.");
                return;
            }

            int idTask;
            try {
                idTask = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Task ID must be a number.");
                return;
            }

            // Check if task with this ID already exists
            if (taskManagement.getMap().values().stream().flatMap(list -> list.stream()).anyMatch(task -> task.getIdTask() == idTask)) {
                showAlert("Duplicate Error", "A task with this ID already exists.");
                return;
            }



            taskManagement.addComplexTasks(idTask, status);

            SerializationOperations.saveData(taskManagement,"project.dat");

            // Refresh tables
            if (appController != null) {
                appController.refreshComplexTaskTable();
            } else {
                System.err.println("Cannot refresh complex task tables.");
            }

            // Close the window
            Stage stage = (Stage) doneButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert("Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
