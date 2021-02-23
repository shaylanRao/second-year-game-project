package sample.models;


import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Powerup extends Sprite {

    private ArrayList<Powerup> visible;

    public Powerup(ImageView imageView) {
        super(imageView);
    }
}
