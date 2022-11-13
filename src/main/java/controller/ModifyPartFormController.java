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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller class to provide control logic of the modify part form of the applicaiton. */
public class ModifyPartFormController implements Initializable {

    /** Part object selected in the MainFormController. */
    private Part selectedPart;

    /** InHouse Radio Button. */
    @FXML
    private RadioButton inHouseRBtn;

    /** Outsourced Radio Button. */
    @FXML
    private RadioButton outsourcedRBtn;

    /** Part ID name label. */
    @FXML
    private Label partIdNameLbl;

    /** Part name text field. */
    @FXML
    private TextField partNameTxt;

    /** Part ID text field. */
    @FXML
    private TextField partIdTxt;

    /** Part Inventory text field. */
    @FXML
    private TextField partInvTxt;

    /** Part maximum text field. */
    @FXML
    private TextField partMaxTxt;

    /** Part minimum text field. */
    @FXML
    private TextField partMinTxt;

    /** Machine ID/Company Name text field. */
    @FXML
    private TextField partIdNameTxt;


    /** Part price text field. */
    @FXML
    private TextField partPriceTxt;

    /** Part toggle group for the raido button. */
    @FXML
    private ToggleGroup tgPartType;

    /** Displays confirmation windows and loads MainFormController.
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

    /** Sets Machine ID/Company Name Label to "Machine ID".
     *
     * @param event InHouse action radio button.
     * */
    @FXML
    void inHouseRBtnAction(ActionEvent event) {

        partIdNameLbl.setText("Machine ID");

    }

    /** Sets Machine ID/Company Name Label to "Company Name".
     *
     * @param event Outsourced action radio button.
     * */
    @FXML
    void outsourcedRBtnAction(ActionEvent event) {

        partIdNameLbl.setText("Company Name");

    }

    /** Replaces part from inventory and loads MainFormController.
     *
     * Text fields validated with error message preventing from empty and/or invalid values.
     *
     * @param event Save action button.
     * @throws IOException from FXMLLoader.
     * */
    @FXML
    void saveBtnAction(ActionEvent event) throws IOException{

        try{
            int id = selectedPart.getId();
            String name = partNameTxt.getText();
            Double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int machineId;
            String companyName;
            boolean partAddSuccessful = false;

            if(validMin(min, max) && validInv(min, max, stock)){

                if(inHouseRBtn.isSelected()){
                    try{
                        machineId = Integer.parseInt(partIdNameTxt.getText());
                        InHouse newInHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(newInHousePart);
                        partAddSuccessful = true;
                    }catch(Exception e){
                        displayAlert(2);
                    }
                }

                if (outsourcedRBtn.isSelected()){
                    companyName = partIdNameTxt.getText();
                    Outsourced newOutsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newOutsourcedPart);
                    partAddSuccessful = true;
                }

                if(partAddSuccessful){
                    Inventory.deletePart(selectedPart);
                    returnToMainForm(event);
                }

            }
        } catch(Exception e){

            displayAlert(1);
        }

    }

    /** Loads MainFormController.
     *
     * @param event Passed from parent method.
     * @throws IOException from FXMLLoader.
     * */
    private void returnToMainForm(ActionEvent event) throws IOException{

            Parent parent = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
    }

    /** Validates Min > 0 and Min < Max.
     *
     * @param min Minimum value for the part.
     * @param max Maximum value for the part.
     * @return Boolean to indicate if min is valid.
     * */
    private boolean validMin(int min, int max){

        boolean isValid = true;

        if(min <= 0 || min >= max){
            isValid = false;
            displayAlert(3);
        }

        return isValid;

    }

    /** Validates inventory level is equal to or between Min and Max.
     *
     * @param min minimum value of the part.
     * @param max maximum value of the part.
     * @param stock inventory level of the part.
     * @return Boolean to indicate if inventory is valid.
     * */
    private boolean validInv(int min, int max, int stock){

        boolean isValid = true;

        if(stock < min || stock > max){
            isValid = false;
            displayAlert(4);
        }

        return isValid;

    }

    /** Displays various alert messages.
     *
     * @param alertType Alert message switch.
     * */
    private void displayAlert(int alertType){

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType){
            case 1:
                alert.setTitle("Error 1");
                alert.setHeaderText("Error Modifying Part");
                alert.setContentText("Form contains blank fields or invalid values.");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error 2");
                alert.setHeaderText("Invalid value entered for Machine ID");
                alert.setContentText("Machine ID only contains number");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error 3");
                alert.setHeaderText("Invalid value entered for Min");
                alert.setContentText("Minimum should be greater than 0 and less than Maximum.");
                alert.showAndWait();
                break;
            case 4:
                alert.setTitle("Error 4");
                alert.setHeaderText("Invalid value entered for Max");
                alert.setContentText("Maximum should be equal to or between Min nad Max.");
                alert.showAndWait();
                break;

        }

    }



    /** Initialize controller and populates text fields with selected parts from MainFormController.
     *
     * @param url Url address used to reslove relative paths for the root object or null when the location is unknown.
     * @param resourceBundle resource used to localize the root object or null when the root object was not localized.
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedPart = MainFormController.getPartToModify();


        if(selectedPart instanceof InHouse){
            inHouseRBtn.setSelected(true);
            partIdNameLbl.setText("Machine ID");
            partIdNameTxt.setText(String.valueOf(((InHouse)selectedPart).getMachineId()));
        }

        if(selectedPart instanceof Outsourced){
            inHouseRBtn.setSelected(true);
            partIdNameLbl.setText("Company Name");
            partIdNameTxt.setText(String.valueOf(((Outsourced)selectedPart).getCompanyName()));
        }

        partIdTxt.setText(String.valueOf(selectedPart.getId()));
        partNameTxt.setText(selectedPart.getName());
        partInvTxt.setText(String.valueOf(selectedPart.getStock()));
        partPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        partMinTxt.setText(String.valueOf(selectedPart.getMin()));
        partMaxTxt.setText(String.valueOf(selectedPart.getMax()));

    }
}
