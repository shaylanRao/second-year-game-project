package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;

public class OilSpillPowerup extends Powerup {

    public OilSpillPowerup(BorderPane gameBackground) {
        super(gameBackground, generateImageView());
        this.type = "oilspill";
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/oilspill.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
