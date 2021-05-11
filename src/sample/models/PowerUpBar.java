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
	
	final double im2x1 = 1680;
	final double im2x2 = 1730;
	final double im2x3 = 1780;
	
	final double y = 30;

	Pane gameBackground;
	
	public PowerUpBar(Pane gameBackground)
	{
		this.gameBackground = gameBackground;
        this.powerupsImagesInBar = new LinkedList<>();
	}
	
	/**
     * Adds a new power-up to a player's power-up bar.
     * @param locationInBar - checks where to render the new power-up in the power-up bar
     * @param player - number of player to add the power-up to
     * @return void
     */
	public void addPowerUpToBar(int locationInBar, Powerup powerup, int player) {
		if (powerup instanceof BananaPowerup)
		{
			BananaBar banana = new BananaBar(gameBackground);
			resize(banana);
			banana.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(banana);
		}
		else if (powerup instanceof OilGhostPowerup)
		{
			OilGhostBar oil = new OilGhostBar(gameBackground);
			resize(oil);
			oil.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(oil);
		}
		else if (powerup instanceof SpeedboosterPowerup)
		{
			SpeedboosterBar speed = new SpeedboosterBar(gameBackground);
			resize(speed);
			speed.render(addPowerupsToCord(locationInBar, player), this.y);
			this.powerupsImagesInBar.add(speed);
		}
    }

    private void resize(Sprite powerup){
		powerup.getImage().setFitWidth(powerup.getImage().getBoundsInParent().getWidth()/0.6);
		powerup.getImage().setFitHeight(powerup.getImage().getBoundsInParent().getHeight()/0.6);
	}
	
	/**
     * Calculates the coordinate of the power-up to add to power-up bar.
     * @param location - the number of the power-up in the bar
     * @param player - which player's power-up bar to add the new power-up to
     * @return double - coordinate for the x axis
     */
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
	
	/**
     * Removes the first power-up in the power-up bar after it's been used or replaced and renders the remaining power-ups in the right places.
     * @param player - states which player's power-up bar are the changes made to
     * @return void
     */
	public void removeFirstPowerup(int player) {
		this.powerupsImagesInBar.getFirst().deactivate();
		this.powerupsImagesInBar.removeFirst();

		int i = 1;
		for (Powerup powerup : powerupsImagesInBar) {
			powerup.getImage().relocate(addPowerupsToCord(i, player), this.y);
			i++;
		}
	}
}
