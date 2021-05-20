package com.turboracer.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import com.turboracer.Main;

import java.io.InputStream;

public class IntroCountdown extends Sprite{


    public IntroCountdown(Pane gameBackground, int frame) {
        super(gameBackground, generateImage(frame), 1);
    }

    public void activate() {
        getImage().setVisible(true);
    }
    public void deactivate() {
        getImage().setVisible(false);
    }


    private static ImageView generateImage(int frame) {
        try {

            String filePath = "";
            if(frame == 0)
                filePath = "/images/Picture3.png";
            else if(frame == 1)
                filePath = "/images/Picture2.png";
            else if(frame == 2)
                filePath = "/images/Picture1.png";
            else if(frame == 3)
                filePath = "/images/Picture4.png";

            InputStream file = Main.class.getResourceAsStream(filePath);
            Image img = new Image(file);
            return new ImageView(img);
        } catch (Exception ex) {
            System.out.println("Error when loading intro image : "+ frame);
        }
        return null;
    }
}
