package sample.models;

import javafx.scene.image.ImageView;

public abstract class Sprite {

    private ImageView image;

    public Sprite(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    public void render(int x, int y) {
        //TODO implement render function
    }

    //detect collisions?
}
