package com.turboracer.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.turboracer.Main;

import java.io.InputStream;

public class SpeedboosterPowerup extends Powerup {

    public SpeedboosterPowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            InputStream speedImageFile = Main.class.getResourceAsStream("/images/powerupSpeedbooster.png");
            Image speedImage = new Image(speedImageFile);
            return new ImageView(speedImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
