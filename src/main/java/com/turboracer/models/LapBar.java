package com.turboracer.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class LapBar extends Sprite {

    ImageView imageView;
    boolean start = true;

    public LapBar(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView, 1);
    }

    public void addLapCounterInOrder(){
        getImage().setVisible(true);
        start = true;
    }

}
