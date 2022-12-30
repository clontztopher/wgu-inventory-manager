package clontz.inventorymanagementapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {
    public TextField productIdField;
    public TextField productNameField;
    public TextField productInventoryField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TextField partSearchField;
    public TableView availablePartsTable;
    public Button addSelectedPartBtn;
    public TableView associatedPartsTable;
    public Button removeAssociatedPartBtn;
    public Button saveProductBtn;
    public Button cancelAddProductBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void searchParts(ActionEvent actionEvent) {
    }

    public void addPart(ActionEvent actionEvent) {
    }

    public void removeAssociatedPart(ActionEvent actionEvent) {
    }

    public void saveProduct(ActionEvent actionEvent) {
    }

    public void cancelAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
}
