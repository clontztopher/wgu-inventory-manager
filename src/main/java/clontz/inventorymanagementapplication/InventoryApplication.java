package clontz.inventorymanagementapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Inventory Application
 *
 * JavaFX app that tracks the input of inventory products and their associated parts.
 *
 * FUTURE ENHANCEMENT: I would refactor the product and part search methods to
 * combine them into a single method that would check for both the name and the
 * id in the same loop iteration. This would cut down on the number of methods
 * in the respective classes and would reduce the number of iterations needed
 * to complete each search.
 *
 * @author Chris Clontz
 */
public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("main-form-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}