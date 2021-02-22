package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;

public abstract class Sprite {

    private Image image;
    private Node node;

    public Sprite(Image image, Node node) {
        this.image = image;
        this.node = node;
    }

    public void render(int x, int y) {
        //TODO implement render function
    }

    //detect collisions?
}
