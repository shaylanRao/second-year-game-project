package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public abstract class Sprite {

    private final ImageView image;
    private final BorderPane gameBackground;

    public Sprite(BorderPane gameBackground, ImageView imageView) {
        this.gameBackground = gameBackground;
        this.image = imageView;
    }

    protected BorderPane getGameBackground() {
        return gameBackground;
    }

    protected ImageView getImage() {
        return image;
    }

    /**
     * @param d
     * @param e
     */
    public void render(double d, double e) {
        // this method will place an item on the screen at an specified coordinate
        this.gameBackground.getChildren().add(getImage());
        getImage().relocate(d, e);
    }

}



