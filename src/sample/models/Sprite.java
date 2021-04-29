package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Sprite {

    private final ImageView image;
    private final Pane gameBackground;

    public Sprite(Pane gameBackground, ImageView imageView, double ratio) {
        this.gameBackground = gameBackground;
        this.image = imageView;
        this.image.setFitWidth(this.image.getBoundsInParent().getWidth()*ratio);
        this.image.setFitHeight(this.image.getBoundsInParent().getHeight()*ratio);

    }

    protected Pane getGameBackground() {
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



