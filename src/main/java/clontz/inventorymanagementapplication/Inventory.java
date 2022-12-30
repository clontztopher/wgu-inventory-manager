package clontz.inventorymanagementapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    static private ObservableList<Part> allParts = FXCollections.observableArrayList();
    static private ObservableList<Product> allProducts = FXCollections.observableArrayList();

    static public void addPart(Part newPart) {
        allParts.add(newPart);
    }

    static public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    static public Part lookupPart(int partId) {
        for( Part part: allParts ) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    static public Product lookupProduct(int productId) {
        for ( Product product: allProducts ) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    static public ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().contains(partName));
    }

    static public ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().contains(productName));
    }

    static public void updatePart(int index, Part selectedPart) {

    }

    static public void updateProduct(int index, Product newProduct) {

    }

    static public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    static public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    static public ObservableList<Part> getAllParts() { return allParts; }

    static public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    static {
        Part part1 = new InHouse(1, "Thingamajigger", 10, 20, 5, 50, 7);
        Part part2 = new InHouse(1, "Nimrod", 12, 15, 5, 50, 8);
        Part part3 = new Outsourced(1, "Doohickey", 10, 20, 5, 50, "Doohickies R Us");

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
    }
}
