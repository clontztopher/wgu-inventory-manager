module clontz.inventorymanagementapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens clontz.inventorymanagementapplication to javafx.fxml;
    exports clontz.inventorymanagementapplication;
}