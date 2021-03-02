package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;

public class BananaDischargePowerup extends Powerup {

    public BananaDischargePowerup(BorderPane gameBackground) {
        super(gameBackground, generateImageView());
        this.type = "bananapeel";
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/bananapeel.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}