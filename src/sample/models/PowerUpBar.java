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

	final double im1x1 = 30;
	final double im1x2 = 80;
	final double im1x3 = 130;
	
	final double im2x1 = 530;
	final double im2x2 = 580;
	final double im2x3 = 630;
	
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
	
	public void addPowerUpToBar(int locationInBar, Powerup powerup, int player) {
		if (powerup instanceof BananaPowerup)
		{
			BananaBar banana = new BananaBar(gameBackground);
			banana.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(banana);
		}
		else if (powerup instanceof OilGhostPowerup)
		{
			OilGhostBar oil = new OilGhostBar(gameBackground);
			oil.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(oil);
		}
		else if (powerup instanceof SpeedboosterPowerup)
		{
			SpeedboosterBar speed = new SpeedboosterBar(gameBackground);
			speed.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(speed);
		}
    }
	
	public double addPowerupsToCord (int location, int player) {
		if (player == 1) {
			if (location == 1) {
				return this.im1x1;
			}
			else if (location == 2) {
				return this.im1x2;
			}
			else if (location == 3) {
				return this.im1x3;
			}
		}
		
		if (player == 2) {
			if (location == 1) {
				return this.im2x1;
			}
			else if (location == 2) {
				return this.im2x2;
			}
			else if (location == 3) {
				return this.im2x3;
			}
		}
		
		return 0;
	}
	
	public void removeFirstPowerup(int player) {
		this.powerupsImagesInBar.peekFirst().deactivate();
		this.powerupsImagesInBar.removeFirst();

		int i = 1;
		for (Powerup powerup : powerupsImagesInBar) {
			powerup.getImage().relocate(addPowerupsToCord(i, player), this.y);
			i++;
		}
	}
}
