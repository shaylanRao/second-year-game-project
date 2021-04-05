package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.util.LinkedList;

public class PowerUpBar
{
    private final LinkedList<Powerup> powerupsImagesInBar;

	final double im1x = 30;
	final double im2x = 90;
	final double im3x = 150;
	final double y = 30;
	
	Pane gameBackground;
	
	public PowerUpBar(Pane gameBackground)
	{
		this.gameBackground = gameBackground;
        this.powerupsImagesInBar = new LinkedList<>();
	}
	
	public void addPowerUpToBar(int locationInBar, Powerup powerup) {
		if (powerup instanceof BananaPowerup)
		{
			BananaPowerup banana = new BananaPowerup(gameBackground);
			banana.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(banana);
		}
		else if (powerup instanceof OilGhostPowerup)
		{
			OilGhostPowerup oil = new OilGhostPowerup(gameBackground);
			oil.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(oil);
		}
		else if (powerup instanceof SpeedboosterPowerup)
		{
			SpeedboosterPowerup speed = new SpeedboosterPowerup(gameBackground);
			speed.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(speed);
		}
    }
	
	public double addPowerupsToCord (int location) {
		if (location == 1) {
			return this.im1x;
		}
		else if (location == 2) {
			return this.im2x;
		}
		else if (location == 3) {
			return this.im3x;
		}
		return 0;
	}
	
	public void removeFirstPowerup() {
		System.out.println("bunny");
		System.out.println(this.powerupsImagesInBar);
		Powerup pwr = this.powerupsImagesInBar.getFirst();
		pwr.deactivate();
		this.powerupsImagesInBar.pop();
		System.out.println(this.powerupsImagesInBar);
		this.powerupsImagesInBar.get(0).render(addPowerupsToCord(1), this.y);
		this.powerupsImagesInBar.get(1).render(addPowerupsToCord(2), this.y);
	}
}
