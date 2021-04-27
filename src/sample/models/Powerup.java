package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Powerup extends Sprite {
	
	protected boolean shouldCollide = true;
	protected long pickUptime = -1;

    public Powerup(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView);
    }

    public void deactivate() {
        this.getImage().setVisible(false);
        this.shouldCollide = false;
        this.pickUptime = System.currentTimeMillis();
    }

    public void activate() {
    	this.getImage().setVisible(true);
    	this.shouldCollide = true;
        this.pickUptime = -1;
    }
}
