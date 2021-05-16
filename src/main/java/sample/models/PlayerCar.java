package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerCar extends Car {

    public int getCarNumber(){
        return this.playerNumber;
    }

    public PlayerCar(Pane gameBackground, Settings.VehicleType vehicleType) {
        super(gameBackground, generateCarImageView(vehicleType), vehicleType);
    }

    private static ImageView generateCarImageView(Settings.VehicleType vehicleType) {
        String imageName;
        try {
            if(vehicleType.equals(Settings.VehicleType.VEHICLE1)){
                imageName = "/images/original_car.png";
            }
            else if (vehicleType.equals(Settings.VehicleType.VEHICLE2)){
                imageName = "/images/vehicleTwo.png";
            }
            else{
                imageName = "/images/vehicleThree.png";
            }
            InputStream carImageFile = Main.class.getResourceAsStream(imageName);
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }



}
