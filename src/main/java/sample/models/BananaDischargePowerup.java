package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.Main;

import java.io.FileInputStream;
import java.io.InputStream;

public class BananaDischargePowerup extends Powerup {

    public BananaDischargePowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            InputStream bananapeelImageFile = Main.class.getResourceAsStream("/images/bananapeel.png");
            Image bananapeelImage = new Image(bananapeelImageFile);
            return new ImageView(bananapeelImage);
        } catch (Exception ex) {
            System.out.println("Error when loading banana peel image");
        }
        return null;
    }
}
