package app.pt2025_30424_ghinet_ioanateodora_assignment_1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("AppView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1800, 800);
        stage.setTitle("Assignment 1-Task Management Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}