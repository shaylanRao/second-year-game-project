package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class BananaDischargePowerup extends Powerup {

    public BananaDischargePowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            FileInputStream bananapeelImageFile = new FileInputStream("src/sample/resources/images/bananapeel.png");
            Image bananapeelImage = new Image(bananapeelImageFile);
            return new ImageView(bananapeelImage);
        } catch (Exception ex) {
            System.out.println("Error when loading banana peel image");
        }
        return null;
    }
}
