package clontz.inventorymanagementapplication;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TableView<Part> mainFormPartTable;
    public TextField mainFormPartSearch;
    public Button mainformAddPartBtn;
    public Button mainformModPartBtn;
    public Button mainformDelPartBtn;
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

    public void handlePartSearch(ActionEvent actionEvent) {
        String searchText = mainFormPartSearch.getText();

        if (searchText == "") {
            mainFormPartTable.setItems(Inventory.getAllParts());
        }

        ObservableList<Part> filteredParts;

        filteredParts = Inventory.getAllParts().filtered(part -> {
            if (part.getName().contains(searchText)) {
                return true;
            }

            try {
                if (part.getId() == Integer.parseInt(searchText)) {
                    return true;
                }
            } catch (NumberFormatException e) {}

            return false;
        });

        mainFormPartTable.setItems(filteredParts);
    }

    public void handleProductSearch(ActionEvent actionEvent) {
        System.out.println("Product search action");
    }

    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("part-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("part-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Update Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeletePart(ActionEvent actionEvent) {
        Part part = (Part)mainFormPartTable.getSelectionModel().getSelectedItem();

        if (part == null) {
            return;
        }

        Inventory.deletePart(part);
    }

    public void onAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("product-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("product-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Update Product");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteProduct(ActionEvent actionEvent) {
        Product product = (Product)mainFormProductTable.getSelectionModel().getSelectedItem();

        if (product == null) {
            return;
        }

        Inventory.deleteProduct(product);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainFormPartTable.setItems(Inventory.getAllParts());
        mainFormProductTable.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}