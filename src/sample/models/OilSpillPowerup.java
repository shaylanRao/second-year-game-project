package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class OilSpillPowerup extends Powerup {

    public OilSpillPowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream oilspillImageFile = new FileInputStream("src/sample/resources/images/oilspill.png");
            Image oilspillImage = new Image(oilspillImageFile);
            return new ImageView(oilspillImage);
        } catch (Exception ex) {
            System.out.println("Error when loading oil spill image");
        }
        return null;
    }
}
