package clontz.inventorymanagementapplication;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MainController
 *
 * The top-level controller class for the application. Forms for
 * individual parts and products are accessed from this controller.
 *
 * RUNTIME ERROR: I ran into a few errors while learning how to
 * associate controllers, properties and methods from SceneBuilder
 * and FXML. Usually, the event handler wouldn't exist when it was
 * called.
 *
 * @author Chris Clontz
 */
public class MainController implements Initializable {

    public TableView<Part> mainFormPartTable;
    public TextField mainFormPartSearch;
    public Button mainFormAddPartBtn;
    public Button mainFormModPartBtn;
    public Button mainFormDelPartBtn;
    public TableView<Product> mainFormProductTable;
    public TextField mainFormProductSearch;
    public Button mainFormAddProductBtn;
    public Button mainFormModProductBtn;
    public Button mainFormDelProductBtn;
    public TableColumn<String, Integer> partId;
    public TableColumn<String, String> partName;
    public TableColumn<String, Integer> partInventory;
    public TableColumn<String, Double> partPrice;
    public TableColumn<String, Integer> productId;
    public TableColumn<String, String> productName;
    public TableColumn<String, Integer> productInventory;
    public TableColumn<String, Double> productPrice;

    /**
     * Event handler for searching parts table based on name or ID.
     * @param keyEvent - Keyup event triggered by typing in search box
     */
    public void handlePartSearch(KeyEvent keyEvent) {
        String searchText = mainFormPartSearch.getText().toLowerCase();

        // Reset table if field is blank
        if (searchText == "") {
            mainFormPartTable.setItems(Inventory.getAllParts());
        }

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        filteredParts.addAll(Inventory.lookupPart(searchText));

        if (filteredParts.isEmpty()) {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchText));
                filteredParts.add(part);
            } catch (Exception ignored) {}
        }

        mainFormPartTable.setItems(filteredParts);
    }

    /**
     * Event handler for searching products table based on name or ID.
     * @param keyEvent - Keyup event for product search box.
     */
    public void handleProductSearch(KeyEvent keyEvent) {
        String searchText = mainFormProductSearch.getText();

        if (searchText == "") {
            mainFormProductTable.setItems(Inventory.getAllProducts());
        }

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

        filteredProducts.addAll(Inventory.lookupProduct(searchText));

        if (filteredProducts.isEmpty()) {
            try {
                Product product = Inventory.lookupProduct(Integer.parseInt(searchText));
                filteredProducts.add(product);
            } catch (Exception ignore) {}
        }

        mainFormProductTable.setItems(filteredProducts);
    }

    /**
     * Listens for a button click to add a part. Navigates to the "Add Part" form.
     * @param actionEvent - Button click event.
     * @throws IOException - Throws IOException in case something goes wrong with FXML file read.
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        PartFormController.setSelectedPartIndex(-1);

        Parent root = FXMLLoader.load(getClass().getResource("part-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Listens for a button click to modify a part. Navigates to the "Update Part" form.
     * @param actionEvent - Button click event.
     * @throws IOException - Throws IOException in case something goes wrong with FXML file read.
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        int focusedItemIndex = mainFormPartTable.getSelectionModel().getSelectedIndex();

        if (focusedItemIndex != -1) {
            PartFormController.setSelectedPartIndex(focusedItemIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected.");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("part-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Update Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Removes selected part from the part inventory based on parts table selection.
     * @param actionEvent - Button click event triggering part deletion.
     */
    public void onDeletePart(ActionEvent actionEvent) {
        Part part = (Part)mainFormPartTable.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No part selected.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected part?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = Inventory.deletePart(part);
                if (!success) {
                    Alert failAlert = new Alert(Alert.AlertType.WARNING, "Part could not be deleted.");
                    failAlert.showAndWait();
                }
            }
        });

        Inventory.deletePart(part);
    }

    /**
     * Event listener for navigating to the "Add Product" form.
     * @param actionEvent - Button click event.
     * @throws IOException - Exception in event of FXML file error.
     */
    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        ProductFormController.setSelectedProductIndex(-1);
        Parent root = FXMLLoader.load(getClass().getResource("product-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event listener for navigation to the "Update Part" form. Passes selected part to form controller class.
     * @param actionEvent - Button click event
     * @throws IOException - Throws in case of file access error.
     */
    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        int focusedProductIndex = mainFormProductTable.getSelectionModel().getSelectedIndex();

        if (focusedProductIndex > -1) {
            ProductFormController.setSelectedProductIndex(focusedProductIndex);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No product selected.");
            alert.showAndWait();
            return;
        }

        Parent root = FXMLLoader.load(getClass().getResource("product-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Update Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event handler that removes selected product on button click.
     * @param actionEvent - Product delete button click event.
     */
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product product = (Product)mainFormProductTable.getSelectionModel().getSelectedItem();

        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No product selected.");
            alert.showAndWait();
            return;
        }

        if (!product.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot delete product with associated parts. Please remove parts first.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected product?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean success = Inventory.deleteProduct(product);
                if (!success) {
                    Alert failAlert = new Alert(Alert.AlertType.WARNING, "Product could not be deleted.");
                    failAlert.showAndWait();
                }
            }
        });
    }

    /**
     * Runs after setup of FXML properties to allow for initialization of controller data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainFormPartTable.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainFormProductTable.setItems(Inventory.getAllProducts());

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void exitApplication(ActionEvent actionEvent) {
        Platform.exit();
    }
}