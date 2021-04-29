package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerCar extends Car {

    public PlayerCar(Pane gameBackground, Settings.VehicleType vehicleType) {
        super(gameBackground, generateCarImageView(vehicleType), vehicleType);
    }

    private static ImageView generateCarImageView(Settings.VehicleType vehicleType) {
        String imageName;
        try {
            if(vehicleType.equals(Settings.VehicleType.VEHICLE1)){
                imageName = "src/sample/resources/images/original_car.png";
            }
            else if (vehicleType.equals(Settings.VehicleType.VEHICLE2)){
                imageName = "src/sample/resources/images/vehicleTwo.png";
            }
            else{
                imageName = "src/sample/resources/images/vehicleThree.png";
            }
            FileInputStream carImageFile = new FileInputStream(imageName);
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }



}
