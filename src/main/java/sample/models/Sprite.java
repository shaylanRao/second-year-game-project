package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;

public abstract class Sprite {

    private final ImageView image;
    private final Pane gameBackground;

    public Sprite(Pane gameBackground, ImageView imageView, double ratio) {
        if (imageView == null) {
            System.out.println("image view was null");
        }
        this.gameBackground = gameBackground;
        this.image = imageView;
        this.image.setFitWidth(this.image.getBoundsInParent().getWidth() * ratio);
        this.image.setFitHeight(this.image.getBoundsInParent().getHeight() * ratio);

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
        if (image == null) {
            System.out.println("image was null");
        }
        this.gameBackground.getChildren().add(image);


        getImage().relocate(d, e);
    }

}



