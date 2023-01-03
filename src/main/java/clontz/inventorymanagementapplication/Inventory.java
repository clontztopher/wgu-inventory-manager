package clontz.inventorymanagementapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory
 *
 * A class that is not meant to be instantiated.
 * Instead, the class holds the applications only inventory
 * lists of parts and products. It also contains their
 * associated retrieval, sorting, and removal methods.
 *
 *
 *
 * @author Chris Clontz
 */
public class Inventory {
    static private ObservableList<Part> allParts = FXCollections.observableArrayList();
    static private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds new part to part inventory list.
     * @param newPart - new (InHouse|Outsourced) Part
     */
    static public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds new product to product inventory list.
     * @param newProduct - new product for inventory addition.
     */
    static public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Part lookup for finding part by ID.
     * @param partId - ID of part to find.
     * @return Found part.
     */
    static public Part lookupPart(int partId) {
        for( Part part: allParts ) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Part lookup for finding part by name.
     * @param partName - name of part to find.
     * @return Found part.
     */
    static public ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part ->
                part.getName().toLowerCase().contains(partName.toLowerCase())
        );
    }

    /**
     * Product lookup for finding product by ID.
     * @param productId - ID of product to find.
     * @return Found product.
     */
    static public Product lookupProduct(int productId) {
        for ( Product product: allProducts ) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Product lookup for finding product by name.
     * @param productName - name of product to find.
     * @return Found product.
     */
    static public ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product ->
                product.getName().toLowerCase().contains(productName.toLowerCase())
        );
    }

    /**
     * Updates part at selected index.
     * @param index - Index of part to update.
     * @param selectedPart - Part to update.
     */
    static public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product at selected index.
     * @param index - Index of product to update.
     * @param newProduct - Product to update.
     */
    static public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes selected part from inventory.
     * @param selectedPart - Part selected for removal.
     * @return - Whether the part deletion was successful or not.
     */
    static public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Removes product from inventory only if the product doesn't have
     * any parts associated with it, otherwise returns false.
     * @param selectedProduct - Product selected for removal.
     * @return - Whether the product deletion was successful or not.
     */
    static public boolean deleteProduct(Product selectedProduct) {
        if ( selectedProduct.getAllAssociatedParts().isEmpty() ) {
            return allProducts.remove(selectedProduct);
        }
        return false;
    }

    /**
     * Getter for parts inventory list.
     * @return allParts
     */
    static public ObservableList<Part> getAllParts() { return allParts; }

    /**
     * Getter for products inventory list.
     * @return allProducts
     */
    static public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /* Initializes data for testing. Remove or comment out before submission.
    static {
        Part part1 = new InHouse(1, "Wheel", 10, 20, 5, 50, 7);
        Part part2 = new InHouse(2, "Windshield", 12, 15, 5, 50, 8);
        Part part3 = new Outsourced(3, "Engine", 10, 20, 5, 50, "Honda");

        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);

        Product p1 = new Product(3, "Car", 99, 60, 10, 100);
        Product p2 = new Product(4, "Truck", 99, 60, 10, 100);
        Product p3 = new Product(5, "Van", 99, 60, 10, 100);

        p1.addAssociatedPart(part1);
        p1.addAssociatedPart(part2);
        p3.addAssociatedPart(part3);

        Inventory.addProduct(p1);
        Inventory.addProduct(p2);
        Inventory.addProduct(p3);
    } */
}
