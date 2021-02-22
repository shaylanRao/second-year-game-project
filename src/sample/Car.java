package sample;


import javafx.scene.Node;
import javafx.scene.image.Image;

public class Car extends Sprite implements Movement {


    public Car(Image image, Node node) {
        super(image, node);
    }

    @Override
    public void moveSprite(double x, double y) {

    }

    @Override
    public void turnSprite(double angle) {

    }
}
