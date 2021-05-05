package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

import java.util.LinkedList;

public class LapBar extends Sprite {

    double currentlap;
    double maxlap;
    double x1 = 30;
    double x2 = 80;
    double y = 60;
    ImageView imageView;
    boolean start = true;

    public LapBar(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView, 1);
    }

    public void addLapCounterInOrder(){
        this.getImage().setVisible(true);
        this.start = true;
    }

}
