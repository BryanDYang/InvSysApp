package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class to provide control logics for the add part form of the application.
 *
 * @author Bryan Yang
 * */
public class AddPartFormController implements Initializable {

    /** The toggle group for the In-House and Outsourced Radio Buttons. */
    @FXML
    private ToggleGroup TGPartType;

    /** In-House Radio Button. */
    @FXML
    private RadioButton inHouseRBtn;

    /** Outsourced Radio Button. */
    @FXML
    private RadioButton outsourcedRBtn;

    /** The machinery ID/Company name label for parts. */
    @FXML
    private Label partIdNameLbl;

    /** The part ID/Company name text field. */
    @FXML
    private TextField partIdNameTxt;

    /** The part ID text field. */
    @FXML
    private TextField partIdTxt;

    /** The part inventory text field. */
    @FXML
    private TextField partInvTxt;

    /** The part maximum level text field. */
    @FXML
    private TextField partMaxTxt;

    /** The part minimum level text field. */
    @FXML
    private TextField partMinTxt;

    /** The part name text field. */
    @FXML
    private TextField partNameTxt;

    /** The part price text field. */
    @FXML
    private TextField partPriceTxt;

    /** Displays confirmation window and loads MainFormController.
     *
     * @param event Cancel button action.
     * @throws IOException from FXMLLoader.
     *
     * */
    @FXML
    void cancelBtnAction(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert!");
        alert.setContentText("You are about to cancel changes and go to the main form, do you wan to proceed?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            returnToMainForm(event);
        }
    }

    /** Sets the machinery ID/Company name label for Machine ID.
     *
     * @param event In-House radio button action.
     * */
    @FXML
    void inHouseRBtnAction(ActionEvent event) {

        partIdNameLbl.setText("Machine ID");

    }

    /** Sets the machinery ID/Company name label for Company Name.
     *
     * @param event Outsourced radio button action.
     * */
    @FXML
    void outsourcedRBtnAction(ActionEvent event) {

        partIdNameLbl.setText("Company Name");

    }

    /** Adds new part to inventory then loads MainFormController.
     *
     * Text fields are being confirmed with displayed error messages and preventing empty and/or invalid value fields.
     *
     * @param event Save button action.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBtnAction(ActionEvent event) throws IOException {

        try{
            int id = 0;
            String name = partNameTxt.getText();
            Double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int machineId;
            String companyName;
            boolean partAddedSuccessful = false;

            if(name.isEmpty()){
                displayAlert(5);
            } else {
                if(validMin(min, max) && validInv(min, max, stock)) {

                    if(inHouseRBtn.isSelected()){
                        try{
                            machineId = Integer.parseInt(partIdNameTxt.getText());
                            InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            newInHousePart.setId(Inventory.getNewPartId());
                            Inventory.addPart(newInHousePart);
                            partAddedSuccessful = true;
                        } catch (Exception e) {
                            displayAlert(2);
                        }
                    }

                    if(outsourcedRBtn.isSelected()){
                        companyName = partIdNameTxt.getText();
                        Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                        newOutsourcedPart.setId(Inventory.getNewProductId());
                        Inventory.addPart(newOutsourcedPart);
                        partAddedSuccessful = true;
                    }

                    if(partAddedSuccessful) {
                        returnToMainForm(event);
                    }
                }
            }

        } catch(Exception e) {
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

        switch(alertType){
            case 1:
                alert.setTitle("Error 1");
                alert.setHeaderText("Error Adding Part");
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
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name may not be empty.");
                alert.showAndWait();
                break;
        }

    }

    /** Initialize controller and sets In-House Radio Button to true.
     *
     * @param url, The url location used to resolve the relative paths for the root object, or null if the url is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRBtn.setSelected(true);
    }
}
