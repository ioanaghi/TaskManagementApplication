package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class TaskNumberController {

    @FXML
    private TextField idField;

    @FXML
    private TextField idField11;

    @FXML
    private TextField idField1;

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
        String employeeIdText = idField.getText();
        if (employeeIdText.isEmpty()) {
            showAlert("Error", "Employee ID cannot be empty.");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdText);
            int[] tasks = taskManagement.getNrOfTasks(employeeId);



            idField1.setText(String.valueOf(tasks[0]));
            idField11.setText(String.valueOf(tasks[1]));
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Employee ID format. Please enter a number.");
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