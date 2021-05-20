package com.turboracer.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.turboracer.Main;

import java.io.InputStream;

public class OilGhostPowerup extends Powerup {

    public OilGhostPowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            InputStream oilImageFile = Main.class.getResourceAsStream("/images/oilghost.png");
            Image oilImage = new Image(oilImageFile);
            return new ImageView(oilImage);
        } catch (Exception ex) {
            System.out.println("Error when loading oil ghost image");
        }
        return null;
    }
}
