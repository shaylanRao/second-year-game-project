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
	final double im2x = 80;
	final double im3x = 130;
	final double y = 30;
	
	/*for multiplayer
	final double im1x = 530;
	final double im2x = 380;
	final double im3x = 630;
	*/
	
	Pane gameBackground;
	
	public PowerUpBar(Pane gameBackground)
	{
		this.gameBackground = gameBackground;
        this.powerupsImagesInBar = new LinkedList<>();
	}
	
	public void addPowerUpToBar(int locationInBar, Powerup powerup) {
		if (powerup instanceof BananaPowerup)
		{
			BananaBar banana = new BananaBar(gameBackground);
			banana.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(banana);
		}
		else if (powerup instanceof OilGhostPowerup)
		{
			OilGhostBar oil = new OilGhostBar(gameBackground);
			oil.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(oil);
		}
		else if (powerup instanceof SpeedboosterPowerup)
		{
			SpeedboosterBar speed = new SpeedboosterBar(gameBackground);
			speed.render(addPowerupsToCord(locationInBar), this.y);
			this.powerupsImagesInBar.add(speed);
		}
    }
	
	public double addPowerupsToCord (int location) {
		//if player 1
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
		
		//if player 2
	}
	
	public void removeFirstPowerup() {
		this.powerupsImagesInBar.peekFirst().deactivate();
		this.powerupsImagesInBar.removeFirst();

		int i = 1;
		for (Powerup powerup : powerupsImagesInBar) {
			powerup.getImage().relocate(addPowerupsToCord(i), this.y);
			i++;
		}
	}
}