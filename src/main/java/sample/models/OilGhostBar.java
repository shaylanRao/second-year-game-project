package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class OilGhostBar extends Powerup {

    public OilGhostBar(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream oilImageFile = new FileInputStream("target/classes/images/oilghostBar.png");
            Image oilImage = new Image(oilImageFile);
            return new ImageView(oilImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
