package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerCar extends Car {

    public PlayerCar(Pane gameBackground) {
        super(gameBackground, generateCarImageView());
    }

    private static ImageView generateCarImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/original_car.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
