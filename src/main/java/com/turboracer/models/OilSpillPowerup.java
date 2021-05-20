package com.turboracer.models;

import com.turboracer.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.InputStream;

public class OilSpillPowerup extends Powerup {

    public OilSpillPowerup(Pane gameBackground) {
        super(gameBackground, generateImageView());
    }

    private static ImageView generateImageView() {
        try {
            InputStream oilspillImageFile = Main.class.getResourceAsStream("/images/oilspill.png");
            Image oilspillImage = new Image(oilspillImageFile);
            return new ImageView(oilspillImage);
        } catch (Exception ex) {
            System.out.println("Error when loading oil spill image");
        }
        return null;
    }
}
