module app.pt2025_30424_ghinet_ioanateodora_assignment_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    exports app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer;
    opens app.pt2025_30424_ghinet_ioanateodora_assignment_1.PresentationLayer to javafx.fxml;

    opens app.pt2025_30424_ghinet_ioanateodora_assignment_1 to javafx.fxml;
    exports app.pt2025_30424_ghinet_ioanateodora_assignment_1;
}