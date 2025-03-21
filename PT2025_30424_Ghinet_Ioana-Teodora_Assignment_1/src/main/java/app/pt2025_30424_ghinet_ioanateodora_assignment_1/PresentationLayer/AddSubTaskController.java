package app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.BusinessLayer.TaskManagement;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel.*;
import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddSubTaskController {

    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField cidField;
    @FXML
    private TextField sidField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private TextField startField;
    @FXML
    private TextField finishField;
    @FXML
    private Button doneButton;

    private TaskManagement taskManagement;
    private AppController appController; // Reference to main controller

    public void setAppController(AppController appController, TaskManagement taskManagement) {
        this.appController = appController;
        this.taskManagement = taskManagement;
    }


    @FXML
    public void initialize() {
        typeChoiceBox.getItems().addAll("Simple", "Complex");
        statusChoiceBox.getItems().addAll("Not Completed", "Completed");
    }

    @FXML
    private void handleDoneButton() {
        String type = typeChoiceBox.getValue();
        String status = statusChoiceBox.getValue();

        if (type == null || status == null || cidField.getText().isEmpty() || sidField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all required fields.");
            return;
        }

        try {
            int complexTaskId = Integer.parseInt(cidField.getText());
            int subtaskId = Integer.parseInt(sidField.getText());

            if (type.equals("Simple")) {
                int startHour = Integer.parseInt(startField.getText());
                int finishHour = Integer.parseInt(finishField.getText());
                taskManagement.addSubtask(complexTaskId,subtaskId, status, startHour, finishHour);
            } else {
                taskManagement.addSubtask(complexTaskId, subtaskId, status);
            }
            SerializationOperations.saveData(taskManagement,"project.dat");

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter valid numeric values.");
        }

        if (appController != null) {
            appController.refreshSimpleTaskTable();
            appController.refreshComplexTaskTable();
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