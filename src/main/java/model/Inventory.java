package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Model for Inventory Parts and Products.
 *
 * This class provides continuous data for the application.
 *
 * @author Bryan Yang.
 * */

public class Inventory {

    //Part Section
    /** Variable: ID for a part for unique part IDs. */
    private static int partId = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** Gets the list of all Parts in Inventory.
     *
     * @return list of Part Objects.
     * */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Adds part to the Inventory
     *
     * @param newPart new added Part object.
     * */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Generates new Part ID.
     *
     * @return Unique Part ID.
     * */
    public static int getNewPartId(){
        return partId++;
    }

    /** Searches Part by ID from the Inventory List
     * Overload Search Function with ID & Name
     * @param partId Part ID.
     * @return If found, show the Part object. Else, null (not found).
     * */
    public static Part searchPart(int partId){
        Part partFound = null;

        for(Part part : allParts){
            if(part.getId() == partId){
                partFound = part;
            }
        }

        return partFound;
    }

    /** Searches Parts by Name from the Inventory List
     *  Overload Search Function with ID & Name
     * @param partName Part Name.
     * @return If found, show all the Parts object.
     * */
    public static ObservableList<Part> searchPart(String partName){
        ObservableList<Part> partsFound = FXCollections.observableArrayList();

        for(Part part : allParts){
            if(part.getName().equals(partName)){
                partsFound.add(part);
            }
        }
        return partsFound;
    }

    /** Modify/Replace Part in the Inventory List.
     *
     * @param index Index of the part being replaced.
     * @param selectedPart part being used for replacement.
     * */
    public static void modifyPart (int index, Part selectedPart){

        allParts.set(index, selectedPart);
    }

    /** Delete/Remove Part from the Inventory List.
     *
     * @param selectedPart part being deleted.
     * @return Boolean: stats of part being removed.
     * */
    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }else{
            return false;
        }
    }

    //Product Section
    /** Variable: ID for a product for unique product IDs. */
    private static int productId = 0;

    /** List: all Parts in Inventory. */

    /** List: all Products in Inventory. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Gets the list of all Products in Inventory.
     *
     * @return list of Product Objects.
     * */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Adds Product to the Inventory.
     *
     * @param newProduct new added Product object.
     * */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Generates new Product ID.
     *
     * @return Unique Product ID.
     * */
    public static int getNewProductId(){
        return productId++;
    }

    /** Searches Product by ID from the Inventory List
     * Overload Search Function with ID & Name
     * @param productId Product ID.
     * @return If found, show the Product object. Else, null (not found).
     * */
    public static Product searchProduct(int productId){
        Product productFound = null;

        for(Product product : allProducts) {
            if(product.getId() == productId){
                productFound = product;
            }
        }

        return productFound;
    }

    /** Searches Products by Name from the Inventory List
     *  Overload Search Function with ID & Name
     * @param productName Product Name.
     * @return If found, show all the Product objects.
     * */
    public static ObservableList<Product> searchProduct(String productName){
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for(Product product : allProducts){
            if(product.getName().equals(productName)){
                productsFound.add(product);
            }
        }

        return productsFound;
    }

    /** Modify/Replace Product in the Inventory List.
     *
     * @param index Index of the product being replaced.
     * @param selectedProduct product being used for replacement.
     * */
    public static void modifyProduct (int index, Product selectedProduct){

        allProducts.set(index, selectedProduct);
    }

    /** Delete/Remove Product from the Inventory List.
     *
     * @param selectedProduct product being deleted.
     * @return Boolean: stats of product being removed.
     * */
    public static boolean deleteProduct(Product selectedProduct){
        if(allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }else{
            return false;
        }
    }


}

