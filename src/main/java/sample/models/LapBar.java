package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.LinkedList;

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
