package sample.models;

import javafx.scene.image.ImageView;

public abstract class Sprite {

    private final ImageView image;

    public Sprite(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }


    /**
     * @param x
     * @param y
     */
    public void render(int x, int y) {
        // this method will place an item on the screen at an specified coordinate
        getImage().relocate(x, y);
    }


}
