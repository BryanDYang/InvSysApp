package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/** Controller class for control logic of the main form of the application.
 *
 * A runtime error happens if no part is selected while the user press the modify button.
 * The runtime error happens when a null value is being passed on to the initialized method of
 * the ModifyPartFormController. An instance of preventing the runtime error can be found from
 * the partModifyAction() method of this class.
 *
 * @author Bryan Yang
 * */
public class MainFormController implements Initializable {

    /** Part object selected in the table view from the user. */
    private static Part partToModify;

    /** Product object selected in the table view from the user. */
    private  static Product productToModify;

    /** ID column of the parts table. */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /** Inventory column of the parts table. */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /** Name column of the parts table. */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /** Price column of the parts table. */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /** Search text field for the parts. */
    @FXML
    private TextField partSearchTxt;

    /** Table view of the parts table. */
    @FXML
    private TableView<Part> partTableView;

    /** ID column of the product table. */
    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    /** Inventory column of the product table. */
    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    /** Name column of the product table. */
    @FXML
    private TableColumn<Product, String> prodNameCol;

    /** Price column of the product table. */
    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    /** Search text filed of the product table. */
    @FXML
    private TextField prodSearchTxt;

    /** Table view of the product table. */
    @FXML
    private TableView<Product> prodTableView;

    /** Gets the user selected part object in the part table.
     *
     * @return Part object or null when no part selected.
     * */
    public static Part getPartToModify() {
        return partToModify;
    }

    /** Sets the user selected part object in the part table.
     *
     * @param set Part object or null if no product selected.
     * */
    public static void setPartToModify(Part partToModify) {
        MainFormController.partToModify = partToModify;
    }

    /** Gets the user selected product object in the product table.
     *
     * @return Product object or null when no product selected.
     * */
    public static Product getProductToModify() { return productToModify; }

    /** Exits the program.
     *
     * @param event Exit action button.
     * */
    @FXML
    void exitBtnAction(ActionEvent event) {

        System.exit(0);
    }

    /** Loads AddPartFormController.
     *
     *
     * @param event Add action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void partAddBtnAction(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Deletes the user selected part in the part table.
     *
     * The method displays an error window if no part is selected.
     *
     * @param event Part delete action button.
     * */
    @FXML
    void partDeleteBtnAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            displayAlert(3);
        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert!");
            alert.setContentText("Do you wish to delete the part selected?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /** Loads ModifyPartController.
     *
     * This method displays error message if a part is not selected.
     *
     * @param event Part modify button action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void partModifyBtnAction(ActionEvent event) throws IOException {

        partToModify = partTableView.getSelectionModel().getSelectedItem();

        /* Instance of correcting runtime error via preventing null from being passed on to the ModifyPartFormController.*/
        if(partToModify == null){

            displayAlert(3);
        }else{

            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyPartForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
    }

    /** Initialize search based on value in part search text field and displays the part table view with the search result.
     *
     * Parts searched by ID or Name.
     *
     * @param event Part search action button.
     * */
    @FXML
    void partSearchBtnAction(ActionEvent event) {

        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        String searchString = partSearchTxt.getText();

        for(Part part : allParts){
            if(String.valueOf(part.getId()).contains(searchString) || part.getName().contains(searchString)){
                foundParts.add(part);
            }
        }

        partTableView.setItems(foundParts);

        if(foundParts.size() == 0){
            displayAlert(1);
        }

    }

    /** Refresh part table view of all parts when search text field for part is empty.
     *
     * @param event Part search text field button pressed.
     * */
    @FXML
    void partSearchTextKeyPressed(KeyEvent event) {

        if(partSearchTxt.getText().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
        }

    }

    /** Loads AddProductFormController.
     *
     * @param event Add product action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void prodAddBtnAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Deletes the user selected product in the product table.
     *
     * This method displays error message when no product is selected with a confirmation window
     * prior to deleting the selected product to prevent the user from deleting a product with
     * any associated part(s).
     *
     * @param event Product delete action button.
     * */
    @FXML
    void prodDeleteBtnAction(ActionEvent event) {

        Product selectedProduct = prodTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct == null){
            displayAlert(4);
        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert!");
            alert.setContentText("Do you wish to delete the product selected?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){

                ObservableList<Part> assocParts = selectedProduct.getAllAssocParts();

                if(assocParts.size() >= 1){
                    displayAlert(5);
                }else{
                    Inventory.deleteProduct(selectedProduct);
                }

            }
        }


    }

    /** Load ModifyProductController.
     *
     * This method displays error message when no product is selected.
     *
     * @param event Product modify action button.
     * @throws IOException from FXMLLloader.
     * */
    @FXML
    void prodModifyBtnAction(ActionEvent event) throws IOException{

        productToModify = prodTableView.getSelectionModel().getSelectedItem();

        if(productToModify == null){
            displayAlert(4);
        }else{
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyProductForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

    /** Initialize a search for values in product search text field and display the product table view as result.
     *
     * Products searched by ID or Name.
     *
     * @param event Part search action button.
     * */
    @FXML
    void prodSearchBtnAction(ActionEvent event) {

        ObservableList<Product> allProducts = Inventory.getAllProducts();
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        String searchString = prodSearchTxt.getText();

        for(Product product : allProducts){
            if(String.valueOf(product.getId()).contains(searchString) || product.getName().contains(searchString)){
                foundProducts.add(product);
            }
        }

        prodTableView.setItems(foundProducts);

        if(foundProducts.size() == 0){
            displayAlert(2);
        }
    }

    /** Refreshes product table view to display all products when search text field is empty.
     *
     * @param event Products search text field key pressed.
     * */
    @FXML
    void prodSearchTextKeyPressed(KeyEvent event) {

        if(prodSearchTxt.getText().isEmpty()){
            prodTableView.setItems(Inventory.getAllProducts());
        }
    }

    /** Display alert messages.
     *
     * @param alertType Alert message switch.
     * */
    private void displayAlert(int alertType) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 1:
                alert.setTitle("Information 1");
                alert.setHeaderText("No part found");
                alert.showAndWait();
                break;

            case 2:
                alert.setTitle("Information 2");
                alert.setHeaderText("No product found");
                alert.showAndWait();
                break;

            case 3:
                alert.setTitle("Error 1");
                alert.setHeaderText("No part selected");
                break;

            case 4:
                alert.setTitle("Error 2");
                alert.setHeaderText("No product selected");
                alert.showAndWait();
                break;

            case 5:
                alert.setTitle("Error 3");
                alert.setHeaderText("Associated parts");
                alert.setContentText("All Parts need to be removed from product prior to deletion.");
                alert.showAndWait();
                break;
        }
    }

    /** Initializes controller and populates table views.
     *
     * @param url url address used to relative paths for the root objects or null if the address is unknown.
     * @param resourceBundle the resource used to localize the root object or null if the object is not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Populate parts table view
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Populate products table view
        prodTableView.setItems(Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
