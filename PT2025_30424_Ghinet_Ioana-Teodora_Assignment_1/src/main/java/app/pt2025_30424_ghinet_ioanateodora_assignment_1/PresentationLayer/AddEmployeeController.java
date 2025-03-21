package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.Employee;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEmployeeController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private Button doneButton;

    private TaskManagement taskManagement;
    private AppController appController; // Reference to main controller

    // Method to set reference to AppController
    public void setAppController(AppController appController, TaskManagement taskManagement) {
        this.appController = appController;
        this.taskManagement = taskManagement;

        if (this.taskManagement == null) {
            System.err.println("ERROR: taskManagement is NULL in AddEmployeeController!");
        } else {
            System.out.println("askManagement is correctly set in AddEmployeeController.");
        }
    }

    @FXML
    private void handleDone(ActionEvent event) {
        try {
            if (taskManagement == null) {
                showAlert("Error", "Task management system is not initialized.");
                return;
            }

            // Get input values
            String idText = idField.getText().trim();
            String name = nameField.getText().trim();

            // Validate input
            if (idText.isEmpty() || name.isEmpty()) {
                showAlert("Validation Error", "Please enter both ID and Name.");
                return;
            }

            int idEmployee;
            try {
                idEmployee = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                showAlert("Validation Error", "Employee ID must be a number.");
                return;
            }

            // Check if employee already exists
            if (taskManagement.getMap().keySet().stream().anyMatch(emp -> emp.getIdEmployee() == idEmployee)) {
                showAlert("Duplicate Error", "An employee with this ID already exists.");
                return;
            }

            // Create new employee
            taskManagement.addEmployee(idEmployee, name);

            // Save data
            SerializationOperations.saveData(taskManagement,"project.dat");

            // Ensure AppController exists and refresh employee table
            if (appController != null) {
                System.out.println("Refresh tabl;e");
                appController.refreshEmployeeTable();
            } else {
                System.err.println("cannot refresh employee table.");
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