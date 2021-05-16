package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import sample.Main;

import java.io.FileInputStream;
import java.io.InputStream;

public class BananaPowerup extends Powerup {

    public BananaPowerup(Pane gameBackground) {
        super(gameBackground, generateBananaImageView());
    }

    private static ImageView generateBananaImageView() {
        try {
            InputStream bananaImageFile = Main.class.getResourceAsStream("/images/banana.png");
            Image bananaImage = new Image(bananaImageFile);
            return new ImageView(bananaImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
