package clontz.inventorymanagementapplication;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * PartFormController
 *
 * Controls form inputs for adding and modifying parts in the application.
 *
 * @author Chris Clontz
 */
public class PartFormController implements Initializable {
    private static int selectedPartIndex;
    public RadioButton radioInHousePart;
    public RadioButton radioOutsourcedPart;

    public TextField partId;
    public TextField partName;
    public TextField partInventory;
    public TextField partPrice;
    public TextField partMax;
    public TextField extraFieldInput;
    public TextField partMin;
    public Button addPartSave;
    public Button addPartCancel;
    public Text extraField;

    /**
     * Set selected part passed from another controller.
     * @param i - array index of part to be modified
     */
    static public void setSelectedPartIndex(int i) {
        selectedPartIndex = i;
    }

    /**
     * Updates extraField's text for in-house part
     * @param actionEvent - Radio toggle event for in-house part
     */
    public void toggleInHousePart(ActionEvent actionEvent) {
        extraField.setText("Machine ID");
    }

    /**
     * Updates extraField's text for outsourced part selection
     * @param actionEvent - Radio toggle event for outsourced part
     */
    public void toggleOutsourcedPart(ActionEvent actionEvent) {
        extraField.setText("Company Name");
    }

    /**
     * Saves new or updated part after validating form field values.
     * @param actionEvent - Save button click event
     * @throws IOException - Throws on exception when navigating back to main form.
     */
    public void savePart(ActionEvent actionEvent) throws IOException {
        int id;
        String name;
        double price;
        int stock;
        int min;
        int max;
        int machineId = 0;
        String companyName = "";

        /* ---------------------------------
         * Validate fields and assign values
         * --------------------------------- */
        try {
            String nameText = partName.getText();
            String stockText = partInventory.getText();
            String priceText = partPrice.getText();
            String maxText = partMax.getText();
            String minText = partMin.getText();
            String extraFieldText = extraFieldInput.getText();

            if (
                    nameText.isBlank()
                    || stockText.isBlank()
                    || priceText.isBlank()
                    || maxText.isBlank()
                    || minText.isBlank()
                    || extraFieldText.isBlank()
            ) {
                throw new Exception("All fields are required to add or modify parts.");
            }

            name = nameText;
            stock = Integer.parseInt(stockText);
            price = Double.parseDouble(priceText);
            max = Integer.parseInt(maxText);
            min = Integer.parseInt(minText);

            if (stock > max || stock < min) {
                throw new Exception("Inventory must be above minimum value and below maximum value.");
            }

            if (radioInHousePart.isSelected()) {
                machineId = Integer.parseInt(extraFieldText);
            } else {
                companyName = extraFieldText;
            }

        } catch(NumberFormatException numE) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Incorrect input type.");
            alert.showAndWait();
            return;
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
            return;
        }

        /*  ----------------
         *  Add/Update parts
         *  ---------------- */
        Part part;
        int selectedPartIndex = PartFormController.selectedPartIndex;

        id = selectedPartIndex > -1
                ? Inventory.getAllParts().get(selectedPartIndex).getId()
                : Part.getNextId();

        if (radioInHousePart.isSelected()) {
            part = new InHouse(id, name, price, stock, min, max, machineId);
        } else {
            part = new Outsourced(id, name, price, stock, min, max, companyName);
        }

        if (selectedPartIndex > -1) {
            Inventory.updatePart(selectedPartIndex, part);
        } else {
            Inventory.addPart(part);
        }

        // Navigate back to main form
        Parent root = FXMLLoader.load(getClass().getResource("main-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void cancelAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-form-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Initializes controller and updates form if selected part has been set.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (PartFormController.selectedPartIndex > -1) {
            Part part = Inventory.getAllParts().get(PartFormController.selectedPartIndex);
            partId.setText(String.valueOf(part.getId()));
            partName.setText(part.getName());
            partPrice.setText(String.valueOf(part.getPrice()));
            partInventory.setText(String.valueOf(part.getStock()));
            partMin.setText(String.valueOf(part.getMin()));
            partMax.setText(String.valueOf(part.getMax()));

            if (part.getClass() == InHouse.class) {
                radioInHousePart.setSelected(true);
                extraField.setText("Machine ID");
                extraFieldInput.setText(String.valueOf(((InHouse) part).getMachineId()));
                return;
            }

            radioOutsourcedPart.setSelected(true);
            extraField.setText("Company Name");
            extraFieldInput.setText(((Outsourced) part).getCompanyName());
        }
    }
}
