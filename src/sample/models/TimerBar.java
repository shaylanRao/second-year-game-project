package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TimerBar extends Sprite {

    public TimerBar(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView, 1);
    }

    public void deactivate(Image image) {
        this.getImage().setVisible(false);
    }
}
