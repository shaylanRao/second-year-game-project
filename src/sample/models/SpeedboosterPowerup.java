package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class SpeedboosterPowerup extends Powerup {

    public SpeedboosterPowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream speedImageFile = new FileInputStream("src/sample/resources/images/powerupSpeedbooster.png");
            Image speedImage = new Image(speedImageFile);
            return new ImageView(speedImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}