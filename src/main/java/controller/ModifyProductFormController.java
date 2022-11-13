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


/** Controller class to provide control logic of the modify product form of the application.
 *
 * @author Bryan Yang
 * */
public class ModifyProductFormController implements Initializable {

    /** The selected product object in MainFormController. */
    Product selectedProduct;

    /** List of associated parts with the product. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    /** Associated part ID column of the associated parts table. */
    @FXML
    private TableColumn<Part, Integer> assocPartIdCol;

    /** Associated part inventory column of the associated parts table. */
    @FXML
    private TableColumn<Part, String> assocPartInvCol;

    /** Associated part name column of the associated parts table. */
    @FXML
    private TableColumn<Part, String> assocPartNameCol;

    /** Assosicated part price column of the associated parts table. */
    @FXML
    private TableColumn<Part, Double> assocPartPriceCol;

    /** Associted part table view. */
    @FXML
    private TableView<Part> assocPartTableView;

    /** Part ID column of the part table. */
    @FXML
    private TableColumn<Part, Integer> partIdCol;

    /** Part inventory column of the part table. */
    @FXML
    private TableColumn<Part, String> partInvCol;

    /** Part name column of the part table. */
    @FXML
    private TableColumn<Part, String> partNameCol;

    /** Part price column of the part table. */
    @FXML
    private TableColumn<Part, Double> partPriceCol;

    /** Part search text field. */
    @FXML
    private TextField partSearchTxt;

    /** All part table view. */
    @FXML
    private TableView<Part> partTableView;

    /** Product inventory text field. */
    @FXML
    private TextField prodInvTxt;

    /** Product maximum text field. */
    @FXML
    private TextField prodMaxTxt;

    /** Product minimum text field. */
    @FXML
    private TextField prodMinTxt;

    /** Product name text field. */
    @FXML
    private TextField prodNameTxt;

    /** Product price text field. */
    @FXML
    private TextField prodPriceTxt;

    /** Product text field. */
    @FXML
    private TextField prodIdTxt;

    /** Adds selected part object in all parts table in the associated parts table.
     *
     * Diplays error window when no part is selected.
     *
     * @param event Add action button.
     * */
    @FXML
    void addBtnAction(ActionEvent event) {

        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

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
    void cancelBtnAction(ActionEvent event) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("Do you wish to cancel changes and return to the main form?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            returnToMainForm(event);

        }

    }

    /** Displays confirmation window and removes selected part in the associated parts table.
     *
     * Displays error window when no part is selected.
     *
     * @param event Remove action button.
     * */
    @FXML
    void removeAssocBtnAction(ActionEvent event) {

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

    /** Replaces/Saves product in the inventory and loads MainFormController.
     *
     * Text fields validated with error window to prevent empty and/or invalid values.
     *
     * @param event Save action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBtnAction(ActionEvent event) throws IOException{

        try{
            int id = selectedProduct.getId();
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

                    Inventory.addProduct(newProduct);
                    Inventory.deleteProduct(selectedProduct);
                    returnToMainForm(event);

                }
            }

        }catch(Exception e){

            displayAlert(1);

        }

    }

    /** Initiates a search based on search text field value and updates
     * the parts table view with the result.
     *
     * Parts searched by ID or Name.
     *
     * @param event Part search action button.
     * */
    @FXML
    void searchBtnAction(ActionEvent event) {

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

    /** Updates part table view to display all parts if parts search text field is empty.
     *
     * @param event Part search text field action button.
     * */
    @FXML
    void searchKeyPressed(KeyEvent event) {

        if(partSearchTxt.getText().isEmpty()){

            partTableView.setItems(Inventory.getAllParts());

        }

    }

    /** Loads MainFormController.
     *
     * @param event Passed via parent method.
     * @throws IOException from FXMLLoader.
     * */
    private void returnToMainForm(ActionEvent event) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /** Validates min is greater than 0 and less than maximum.
     *
     * @param min Minimum value of the part.
     * @param max Maximum value of the part.
     * @return Boolean indicating when minimum is valid.
     * */
    private boolean validMin(int min, int max){

        boolean isValid = true;

        if(min <= 0 || min >= max){

            isValid = false;
            displayAlert(3);

        }

        return isValid;

    }

    /** Validates inventory level is equal to or between min and max level.
     *
     * @param min Minimum level of the part.
     * @param max Maximum level of the part.
     * @param stock Inventory level of the part.
     * @return Boolean Inventory is valid or not.
     * */
    private boolean validInv(int min, int max, int stock){

        boolean isValid = true;

        if(stock < min || stock > max){
            isValid = false;
            displayAlert(4);
        }

        return isValid;

    }

    /** Shows various alert messages.
     *
     * @param alertType Alert message switch.
     * */
    private void displayAlert(int alertType){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertType){

            case 1:
                alert.setTitle("Error 1");
                alert.setHeaderText("Error Modifying Product");
                alert.setContentText("Form has blank text fields or invalid values.");
                alert.showAndWait();
                break;

            case 2:
                alertInfo.setTitle("Information 1");
                alertInfo.setHeaderText("No part found");
                alertInfo.setContentText("No part was found for the search.");
                alertInfo.showAndWait();
                break;

            case 3:
                alert.setTitle("Error 2");
                alert.setHeaderText("Invalid Minimum value");
                alert.setContentText("Form has blank text fields or invalid values.");
                alert.showAndWait();
                break;

            case 4:
                alert.setTitle("Error 3");
                alert.setHeaderText("Invalid Inventory value");
                alert.setContentText("Form has blank text fields or invalid values.");
                alert.showAndWait();
                break;

            case 5:
                alertInfo.setTitle("Information 2");
                alertInfo.setHeaderText("No part selected");
                alertInfo.setContentText("No part was selected for the search.");
                alertInfo.showAndWait();
                break;

            case 6:
                alert.setTitle("Error 4");
                alert.setHeaderText("Empty name");
                alert.setContentText("Name text field cannot be empty.");
                alert.showAndWait();
                break;

        }

    }

    /** Initializes controller and populates text fields with selected product in MainFormController.
     *
     * @param url URL address used to resolve relative paths of the root object or null when the address is unknown.
     * @param resourceBundle Resource used to localize the root object or null when the object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainFormController.getProductToModify();
        assocParts = selectedProduct.getAllAssocParts();

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(Inventory.getAllParts());

        assocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartTableView.setItems(assocParts);

        prodIdTxt.setText(String.valueOf(selectedProduct.getId()));
        prodNameTxt.setText(selectedProduct.getName());
        prodInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        prodPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        prodMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        prodMinTxt.setText(String.valueOf(selectedProduct.getMin()));

    }
}
