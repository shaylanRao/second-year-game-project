package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

    private final ImageView image;
    private final Pane gameBackground;

    public Sprite(Pane gameBackground, ImageView imageView, double ratio) {
        this.gameBackground = gameBackground;
        image = imageView;
        image.setFitWidth(image.getBoundsInParent().getWidth()*ratio);
        image.setFitHeight(image.getBoundsInParent().getHeight()*ratio);

    }

    protected Pane getGameBackground() {
        return gameBackground;
    }

    protected ImageView getImage() {
        return image;
    }

    /**
     * Renders the sprite's image on the screen.
     * @param d - x coordinate
     * @param e - y coordinate
     */
    public void render(double d, double e) {
        // this method will place an item on the screen at an specified coordinate
        gameBackground.getChildren().add(getImage());
        getImage().relocate(d, e);
    }

}



