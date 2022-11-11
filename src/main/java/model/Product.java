package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Model for product and associated parts.
 *
 * @author Bryan Yang
 * */

public class Product {

    /** The ID of the Product. */
    private int id;

    /** The Name of the Product. */
    private String name;

    /** The Price of the Product. */
    private double price;

    /** The Inventory Level of the Product. */
    private int stock;

    /** The Minimum Level of the Product. */
    private int min;

    /** The Maximum Level of the Product. */
    private int max;

    /** A list of the associated Parts of the Product. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /** Constructor for a new instance of a Product.
     *
     * @param id the ID of the Product
     * @param name the Name of the Product
     * @param price the Price of the Product
     * @param stock the Inventory Level of the Product
     * @param min the Minimum Level of the Product
     * @param max the Maximum Level of the Product
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Getter for the ID.
     *
     * @return id of the Product.
     * */
    public int getId() {
        return id;
    }

    /** Setter for the ID.
     *
     * @param id The ID of the Product.
     * */
    public void setId(int id) {
        this.id = id;
    }

    /** Getter for the Name.
     *
     * @return Name of the Product.
     * */
    public String getName() {
        return name;
    }

    /** Setter for the Name.
     *
     * @param name The Name of the Product.
     * */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the Price.
     *
     * @return Price of the Product.
     * */
    public double getPrice() {
        return price;
    }

    /** Setter for the Price.
     *
     * @param price The Price of the Product.
     * */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Getter for the Stock.
     *
     * @return The Stock of the Product.
     * */
    public int getStock() {
        return stock;
    }

    /** Setter for the Stock.
     *
     * @param stock The Inventory Level of the Product.
     * */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Getter for the Minimum.
     *
     * @return The Minimum Level of the Product.
     * */
    public int getMin() {
        return min;
    }

    /** Setter for the Minimum.
     *
     * @param min the Minimum Level of the Product.
     * */
    public void setMin(int min) {
        this.min = min;
    }

    /** Getter for the Maximum.
     *
     * @return The Maximum Level of the Product.
     * */
    public int getMax() {
        return max;
    }

    /** Setter for the Maximum.
     *
     * @param max the Maximum Level of the Product.
     * */
    public void setMax(int max) {
        this.max = max;
    }

    /** Gets A List of Associated Parts for the Product.
     *
     * @return A List of Parts.
     * */
    public ObservableList<Part> getAssocParts(){return assocParts;}

    /** Adds a Part to the Associated Parts List for the Product.
     *
     * @param part The Associated Part to Add.
     * */
    public void addAssocParts(Part part) {
        assocParts.add(part);
    }

    /** Deletes a Part from the Associated Parts List for the Product.
     *
     * @param selectedAssocPart the Associated Part to Delete
     * @return A Boolean status of part removal.
     * */
    public boolean deleteAssocPart(Part selectedAssocPart){
        if(assocParts.contains(selectedAssocPart)){
            assocParts.remove(selectedAssocPart);
            return  true;
        }else{
            return false;
        }
    }

    /** Get list of all assciated parts for the product.
     *
     * @return a list of all parts.
     * */
    public ObservableList<Part> getAllAssocParts(){return assocParts;}

}

