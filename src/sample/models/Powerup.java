package sample.models;


import javafx.scene.image.ImageView;

public class Powerup extends Sprite {

    public Powerup(ImageView imageView) {
        super(imageView);
    }

    /* TODO
       Powerup spawn method
        • should randomly place the powerups on the screen
       Powerup deactivate method - needs a timer
        •
    */

    public void deactivate() {
        this.getImage().setVisible(false);
    }
}
