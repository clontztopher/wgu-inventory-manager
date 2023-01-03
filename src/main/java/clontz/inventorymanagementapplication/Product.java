package clontz.inventorymanagementapplication;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product
 *
 * Model class for products.
 *
 * @author Chris Clontz
 */
public class Product {
    private static int nextId = 0;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Product constructor
     * @param id - product id
     * @param name - product name
     * @param price - product price
     * @param stock - products in stock
     * @param min - required minimum products in stock
     * @param max - required maximum products in stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Generates and returns the next product ID
     * @return new product id
     */
    public static int getNextId() {
        return ++nextId;
    }

    /**
     * Getter for id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id
     * @param id - id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for product name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for product name
     * @param name - name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for product price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for product price
     * @param price - price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for stock
     * @param stock - stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for minimum stock
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for min stock
     * @param min - set minimum stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for maximum stock
     * @return - max
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for maximum stock
     * @param max - set max stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds part to associated parts list
     * @param part - part to add
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * Removes part from associated parts list
     * @param selectedAssociatedPart - part to remove
     * @return boolean of whether removal succeeded or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Getter for associated parts
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
