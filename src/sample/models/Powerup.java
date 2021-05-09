package sample.models;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Powerup extends Sprite {
	
	protected boolean shouldCollide = true;
	protected long pickUptime = -1;

    public Powerup(Pane gameBackground, ImageView imageView) {
        super(gameBackground, imageView, 0.6);
    }
    
    
    /**
     * The power-up is hidden from the track and cannot be collided into.
     * @return void
     */
    public void deactivate() {
        this.getImage().setVisible(false);
        this.shouldCollide = false;
        this.pickUptime = System.currentTimeMillis();
    }
    
    /**
     * The power-up (re)appears on the track and players can pick it up again.
     * @return void
     */
    public void activate() {
    	this.getImage().setVisible(true);
    	this.shouldCollide = true;
        this.pickUptime = -1;
    }
}
