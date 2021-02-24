package sample.models;

import javafx.scene.image.ImageView;

public abstract class Sprite {

    private ImageView image;

    /**
     * Creates the sprite object
     * @param image
     */
    public Sprite(ImageView image) {
        this.image = image;
    }

    /**
     * Creates the sprite object and renders it at (x,y)
     * @param image
     * @param x
     * @param y
     */
    public Sprite(ImageView image, int x, int y) {
        this.image = image;
        this.render(x, y);
    }

    public ImageView getImage() {
        return image;
    }

    /**
     * This function will position this Sprite at the chosen coordinates
     * @param x
     * @param y
     * */
    public void render(int x, int y) {
        // this method will place an item on the screen at an specified coordinate
        getImage().relocate(x, y);
    }


}



