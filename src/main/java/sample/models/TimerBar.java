package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TimerBar extends Sprite {

    private final Pane gameBackground;

    public TimerBar(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView, 1);
        this.gameBackground = gameBackground;
    }

    public void deactivate() {
        getImage().setVisible(false);
        gameBackground.getChildren().remove(this);
    }
}
