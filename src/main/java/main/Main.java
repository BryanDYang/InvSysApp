package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;


/** The Inventory System application that implements a program for inventory management of parts and products.
 *
 * Future feature can be added to duplicate a product or part plus control which one is allowed to be duplicated.
 *
 * @author Bryan Yang
 * */
public class Main extends Application {

    /** The start method makes the FXML stage then loads the initial scene.
     *
     * @param stage
     * @throws Exception
     * */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Inventory System Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /** The main method is the entry point of this application.
     *
     * The main method inputs sample data at the launch of the application.
     *
     * @param args
     * */
    public static void main(String[] args) {

        //Sample Part dataset

        //InHouse parts
        int partId = Inventory.getNewPartId();
        InHouse brakes = new InHouse(partId, "Brakes", 15.00, 3, 1, 10, 101);

        partId = Inventory.getNewPartId();
        InHouse wheel = new InHouse(partId, "Wheel", 11.00, 16, 1, 20, 101);

        partId = Inventory.getNewPartId();
        InHouse seat = new InHouse(partId, "Seat", 15.00, 10, 1, 15, 101);

        //Outsourced part
        partId = Inventory.getNewPartId();
        Outsourced light = new Outsourced(partId,"Light", 20.00, 5, 1, 10, "Honeywell");

        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);
        Inventory.addPart(light);

        //Sample Product dataset
        int productId = Inventory.getNewProductId();
        Product giant_bike = new Product(productId, "Giant Bike", 299.99, 5, 1, 10);

        giant_bike.addAssocParts(brakes);
        giant_bike.addAssocParts(wheel);
        giant_bike.addAssocParts(seat);
        giant_bike.addAssocParts(light);
        Inventory.addProduct(giant_bike);

        productId = Inventory.getNewProductId();
        Product tricycle = new Product(productId, "Tricycle", 99.99, 3, 1, 5);

        tricycle.addAssocParts(brakes);
        tricycle.addAssocParts(wheel);
        tricycle.addAssocParts(seat);
        tricycle.addAssocParts(light);
        Inventory.addProduct(tricycle);

        launch(args);
    }
}