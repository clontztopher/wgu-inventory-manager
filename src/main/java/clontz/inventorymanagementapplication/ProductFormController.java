package clontz.inventorymanagementapplication;

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
 * ProductFormController
 *
 * Controls the product entry and update form.
 *
 * @author Chris Clontz
 */
public class ProductFormController implements Initializable {
    static private int selectedProductIndex;
    public TableColumn<String, Integer> availPartId;
    public TableColumn<String, String> availPartName;
    public TableColumn<String, Integer> availInventoryLevel;
    public TableColumn<String, Double> availPrice;
    public TableColumn<String, Integer> assocPartId;
    public TableColumn<String, String> assocPartName;
    public TableColumn<String, Integer> assocInventoryLevel;
    public TableColumn<String, Double> assocPricePerUnit;

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInventoryField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TextField partSearchField;
    public TableView<Part> availablePartsTable;
    public Button addSelectedPartBtn;
    public TableView<Part> associatedPartsTable;
    public Button removeAssociatedPartBtn;
    public Button saveProductBtn;
    public Button cancelAddProductBtn;

    /**
     * Searches available parts table
     * @param keyEvent - key up event on search box
     */
    public void searchParts(KeyEvent keyEvent) {
        String searchText = partSearchField.getText().toLowerCase();

        // Reset table if field is blank
        if (searchText == "") {
            availablePartsTable.setItems(Inventory.getAllParts());
        }

        ObservableList<Part> filteredParts = FXCollections.observableArrayList();

        filteredParts.addAll(Inventory.lookupPart(searchText));

        if (filteredParts.isEmpty()) {
            try {
                Part part = Inventory.lookupPart(Integer.parseInt(searchText));
                filteredParts.add(part);
            } catch (Exception ignored) {}
        }

        availablePartsTable.setItems(filteredParts);
    }

    /**
     * Adds part selected from available parts table to associated parts list
     * @param actionEvent - Search button click event
     */
    public void addPart(ActionEvent actionEvent) {
        Part focusedPart = availablePartsTable.getSelectionModel().getSelectedItem();

        if (focusedPart == null) {
            return;
        }

        // Prevent duplicates
        if (!associatedParts.filtered(part -> part.getId() == focusedPart.getId()).isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selected part already associated with product.");
            alert.showAndWait();
            return;
        }

        associatedParts.add(focusedPart);
    }

    /**
     * Removes selected part from associated parts table and inventory list
     * @param actionEvent - Delete button click event
     */
    public void removeAssociatedPart(ActionEvent actionEvent) {
        Part focusedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (focusedPart != null) {
            associatedParts.remove(focusedPart);
        }
    }

    /**
     * Saves new or updated product after validating form fields
     * @param actionEvent - save button click event
     * @throws IOException - throws in case of error during navigation back to main form
     */
    public void saveProduct(ActionEvent actionEvent) throws IOException {
        int id;
        String name;
        int inventory;
        double price;
        int max;
        int min;

        /* ---------------
         * Validate fields
         * --------------- */
        try {
            String nameText = productNameField.getText();
            String stockText = productInventoryField.getText();
            String priceText = productPriceField.getText();
            String maxText = productMaxField.getText();
            String minText = productMinField.getText();

            if (
                    nameText.isBlank()
                    || stockText.isBlank()
                    || priceText.isBlank()
                    || maxText.isBlank()
                    || minText.isBlank()
            ) {
                throw new Exception("All fields are required to add or modify products.");
            }

            name = nameText;
            inventory = Integer.parseInt(stockText);
            price = Double.parseDouble(priceText);
            max = Integer.parseInt(maxText);
            min = Integer.parseInt(minText);

        } catch (NumberFormatException numE) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect input type.");
            alert.showAndWait();
            return;
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
            return;
        }

        /* ------------------
         * Add/Update Product
         * ------------------ */
        Product product;
        int selectedProductIndex = ProductFormController.selectedProductIndex;

        id = selectedProductIndex > -1
            ? Inventory.getAllProducts().get(selectedProductIndex).getId()
            : Product.getNextId();

        product = new Product(id, name, price, inventory, min, max);

        for (Part part: associatedParts) {
            product.addAssociatedPart(part);
        }

        if (selectedProductIndex > -1) {
            Inventory.updateProduct(selectedProductIndex, product);
        } else {
            Inventory.addProduct(product);
        }

        Parent root = FXMLLoader.load(getClass().getResource("main-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cancels product form interaction and returns user to main form
     * @param actionEvent - Cancel button click event
     * @throws IOException - Throws in event that something goes wrong with FXML file read
     */
    public void cancelAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Static method for controllers to pass selected product index before initialization
     * @param i - selected product index
     */
    static public void setSelectedProductIndex(int i) { selectedProductIndex = i; }

    /**
     * Initializes controller with data and updates form fields
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load available parts table
        availablePartsTable.setItems(Inventory.getAllParts());

        availPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Clear out associated parts each time controller is loaded
        if (associatedParts.size() > 0) {
            associatedParts.clear();
        }

        // Load associated parts table
        associatedPartsTable.setItems(associatedParts);

        assocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set up for loaded product
        if (ProductFormController.selectedProductIndex > -1) {
            Product selectedProduct = Inventory.getAllProducts().get(ProductFormController.selectedProductIndex);
            productIdField.setText(String.valueOf(selectedProduct.getId()));
            productNameField.setText(selectedProduct.getName());
            productInventoryField.setText(String.valueOf(selectedProduct.getStock()));
            productPriceField.setText(String.valueOf(selectedProduct.getPrice()));
            productMaxField.setText(String.valueOf(selectedProduct.getMax()));
            productMinField.setText(String.valueOf(selectedProduct.getMin()));

            associatedParts.addAll(selectedProduct.getAllAssociatedParts());
        }


    }
}
