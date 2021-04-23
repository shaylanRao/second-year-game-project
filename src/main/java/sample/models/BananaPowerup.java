package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class BananaPowerup extends Powerup {

    public BananaPowerup(Pane gameBackground) {
        super(gameBackground, generateBananaImageView());
    }

    private static ImageView generateBananaImageView() {
        try {
            FileInputStream bananaImageFile = new FileInputStream("target/classes/images/banana.png");
            Image bananaImage = new Image(bananaImageFile);
            return new ImageView(bananaImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
