package sample.models;


import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class Powerup extends Sprite {
	
	protected boolean shouldCollide = true;

    public Powerup(BorderPane gameBackground, ImageView imageView) {
        super(gameBackground, imageView);
    }

    public void deactivate() {
        this.getImage().setVisible(false);
        this.shouldCollide = false;
    }
    
}
