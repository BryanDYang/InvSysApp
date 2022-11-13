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

/** Controller class to control logic for the add product screen of the application.
 *
 * @author Bryan Yang
 * */
public class AddProductFormController implements Initializable {

    /** List of parts associated with the product. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /** The Part ID Column for the associated parts table. */
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    /** The Part Inventory Column for the associated parts table. */
    @FXML
    private TableColumn<Part, Integer> assocPartInvCol;

    /** The Part Name Column for the associated parts table. */
    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    /** The Part Price Column for the associated parts table. */
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

    /** The Associated Parts table view. */
    @FXML
    private TableView<Part> assocPartTableView;

    /** The Part ID Column for all parts table. */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /** The Part Inventory Column for all parts table. */
    @FXML
    private TableColumn<Part, Integer> partInvCol;

    /** The Part Name Column for all parts table. */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /** The Part Price Column for all parts table. */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /** The part search text field. */
    @FXML
    private TextField partSearchTxt;

    /** The all parts table view. */
    @FXML
    private TableView<Part> partTableView;

    /** The Product ID text field. */
    @FXML
    private TextField prodIdTxt;

    /** The Product Inventory text field. */
    @FXML
    private TextField prodInvTxt;

    /** The Product Inventory Maximum text field. */
    @FXML
    private TextField prodMaxTxt;

    /** The Product Inventory Minimum text field. */
    @FXML
    private TextField prodMinTxt;

    /** The Product Name text field. */
    @FXML
    private TextField prodNameTxt;

    /** The Product Price text field. */
    @FXML
    private TextField prodPriceTxt;

    /** Adds part object selected in all parts table for the associated parts table.
     *
     * If no part is selected, display error message.
     *
     * @param event Add action button.
     * */
    @FXML
    void addBtnAction(ActionEvent event) {

        Part selectedPart = (Part) partTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            displayAlert(5);
        }else{
            assocParts.add(selectedPart);
            assocPartTableView.setItems(assocParts);
        }

    }

    /** Displays confirmation window and loads MainFormController.
     *
     * @param event Cancel action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void cancelBtnAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Do you wish to cancel changes and return to the main form?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            returnToMainForm(event);
        }
    }

    /** Begin search based on part search text field and refreshes the part list table view with the result.
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

    /** Show all parts when search text field is empty.
     *
     * @param event Parts search text field key action.
     *
     * */
    @FXML
    void partSearchKeyPressed(KeyEvent event) {

        if(partSearchTxt.getText().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /** Displays confirmation window and removes selected part from associated parts table.
     *
     * Displays error window if no part is selected.
     *
     * @param event Remove action button.
     * */
    @FXML
    void removeBtnAction(ActionEvent event) {

        Part selectedPart = assocPartTableView.getSelectionModel().getSelectedItem();

        if(selectedPart == null){
            displayAlert(5);
        }else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert!");
            alert.setContentText("Do you wish to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
                assocParts.remove(selectedPart);
                assocPartTableView.setItems(assocParts);
            }
        }
    }

    /** Adds new product to inventory and loads MainFormController.
     *
     * Text fields validated with alert message to prevent empty ad invalid values.
     *
     * @param event Save action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBtnAction(ActionEvent event) throws IOException{

        try{
            int id = 0;
            String name = prodNameTxt.getText();
            Double price = Double.parseDouble(prodPriceTxt.getText());
            int stock = Integer.parseInt(prodInvTxt.getText());
            int min = Integer.parseInt(prodMinTxt.getText());
            int max = Integer.parseInt(prodMaxTxt.getText());

            if(name.isEmpty()){
                displayAlert(6);
            }else{
                if(validMin(min, max) && validInv(min, max, stock)){

                    Product newProduct = new Product(id, name, price, stock, min, max);

                    for(Part part : assocParts){
                        newProduct.addAssocParts(part);
                    }

                    newProduct.setId(Inventory.getNewProductId());
                    Inventory.addProduct(newProduct);
                    returnToMainForm(event);
                }
            }
        }catch(Exception e){
            displayAlert(1);
        }

    }

    /** Loads MainFormController.
     *
     * @param event passed from parent method.
     * @throws IOException from FXMLLoader.
     * */
    private void returnToMainForm(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /** Validates Min > 0 && Min < Max.
     *
     * @param min The Minimum value of the part.
     * @param max The Maximum value of the part.
     * @return Boolean validating if the Min is valid.
     * */
    private boolean validMin(int min, int max){

        boolean isValid = true;

        if(min <= 0 || min >= max){
            isValid = false;
            displayAlert(3);
        }
        return isValid;
    }

    /** Validates Stock (Inventory Level) is equal to or between min and max levels.
     *
     * @param min the Minimum value of the part.
     * @param max the Maximum value of the part.
     * @param stock The Inventory level of the part.
     * @return Boolean validating if the Inventory is valid.
     * */
    private boolean validInv(int min, int max, int stock){

        boolean isValid = true;

        if(stock < min || stock > max){
            isValid = false;
            displayAlert(4);
        }
        return isValid;
    }

    /** Displays Alert Messages based on applicable case.
     *
     * @param alertType Alert Message selector.
     * */
    private void displayAlert(int alertType){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch(alertType){
            case 1:
                alert.setTitle("Error 1");
                alert.setHeaderText("Error Adding Product");
                alert.setContentText("The form contains blank fields or invalid values.");
                alert.showAndWait();
                break;

            case 2:
                alert.setTitle("Error 2");
                alert.setHeaderText("Invalid value for Machine ID");
                alert.setContentText("Machine ID contains numbers only.");
                alert.showAndWait();
                break;

            case 3:
                alert.setTitle("Error 3");
                alert.setHeaderText("Invalid value for Minimum Inventory Level");
                alert.setContentText("Minimum may be greater than 0 and less than Maximum.");
                break;

            case 4:
                alert.setTitle("Error 4");
                alert.setHeaderText("Invalid value for Inventory Level");
                alert.setContentText("Inventory may be equal to or between Minimum or Maximum.");
                alert.showAndWait();
                break;

            case 5:
                alert.setTitle("Error 5");
                alert.setHeaderText("No part selected");
                alert.setContentText("No part was selected.");
                alert.showAndWait();
                break;

            case 6:
                alert.setTitle("Error 6");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name may not be empty.");
                alert.showAndWait();
                break;
        }

    }



    /** Initializes controller and populates table views.
     *
     * @param url url address used to resolve the relative paths of the root object or null if the address is unknown.
     * @param resourceBundle resource bundle used to localize the root object or null if the address is not localize.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
