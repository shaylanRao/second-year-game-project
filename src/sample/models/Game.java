package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class that contains the main game loop
 */

public class Game
{

	private PlayerCar			playerCar;
	private ArrayList<Powerup>	powerups;
	private ArrayList<Powerup>	powerupsDischarge;

	public PlayerCar getPlayerCar()
	{
		return playerCar;
	}

	/**
	 * Sets initial game state
	 */
	private void initialiser()
	{
		Random random = new Random();

		playerCar.render(300, 450);
		for (Powerup bananaPowerup : powerups)
		{
			int x = random.nextInt(1280);
			int y = random.nextInt(720);
			bananaPowerup.render(x, y);
		}
	}

	public void initialiseGameObjects(BorderPane gameBackground)
	{
		// this method should take in all the necessary info from the GameController and initialise the playerCars
		this.playerCar = new PlayerCar(gameBackground);
		this.powerups = new ArrayList<>();
		this.powerupsDischarge = new ArrayList<>();

		// generates powerups
		int maxPowerups = 2;
		for (int i = 0; i < maxPowerups; i++)
		{
			this.powerups.add(new BananaPowerup(gameBackground));
			this.powerups.add(new SpeedboosterPowerup(gameBackground));
			this.powerups.add(new OilGhostPowerup(gameBackground));
		}
	}

	/**
	 * The game loop. This deals with game logic such as handling collisions and moving the car
	 *
	 */
	public synchronized void gameLoop() throws InterruptedException
	{
		this.initialiser();

		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{

				double dy = 0, rot = 0;

				double forwardVelocity = playerCar.getForwardVelocity();
				double turningSpeed = playerCar.getTurningSpeed();

				if (playerCar.isGoingForward())
				{
					dy -= forwardVelocity;
				}
				else
				{ //for car roll, needs to keep car moving forward
					dy -= forwardVelocity;
				}
				if (playerCar.isGoingBackward())
				{
					dy += 1;
				}
				if (playerCar.isTurnLeft())
				{
					rot -= turningSpeed;
				}
				if (playerCar.isTurnRight())
				{
					rot += turningSpeed;
				}

				//shouldCollide is a boolean that helps solve a bug (when a car collides with a powerup and the discharge powerup is created,
				//then the powerup is set to visible(false), but the collision is still happening so a discharge powerup keeps popping on the screen)
				for (Powerup powerup : powerups)
				{
					if (playerCar.collisionDetection(powerup) && powerup.shouldCollide)
					{

						playerCar.addPowerup(powerup);
						powerup.deactivate();
						powerupsDischarge.add(powerup);

						// calculating the position of the powerup and playerCar to position it
						//                        double playerCarLayoutX = playerCar.getImage().getLayoutX();
						//                        double playerCarLayoutY = playerCar.getImage().getLayoutY();
						//                        double powerupWidth = powerup.getImage().getBoundsInLocal().getWidth();
						//                        double powerupHeight = powerup.getImage().getBoundsInLocal().getHeight();
						//
						//                        double x = playerCarLayoutX - powerupWidth;
						//                        double y = playerCarLayoutY - powerupHeight;
					}
				}
				for (Powerup powerup : powerupsDischarge)
				{
					if (playerCar.isActivatedPowerup())
					{
						double playerCarLayoutX = playerCar.getImage().getLayoutX();
						double playerCarLayoutY = playerCar.getImage().getLayoutY();
						double powerupWidth = powerup.getImage().getBoundsInLocal().getWidth();
						double powerupHeight = powerup.getImage().getBoundsInLocal().getHeight();

						double x = playerCarLayoutX - powerupWidth;
						double y = playerCarLayoutY - powerupHeight;
						if (powerup instanceof BananaPowerup)
						{
							BananaDischargePowerup ban = new BananaDischargePowerup(powerup.getGameBackground());
							ban.render(x, y);
							powerupsDischarge.remove(powerup);
							powerupsDischarge.add(ban);
						}
						else if (powerup instanceof OilGhostPowerup)
						{
							OilSpillPowerup oil = new OilSpillPowerup(powerup.getGameBackground());
							oil.render(x, y);
							powerupsDischarge.remove(powerup);
							powerupsDischarge.add(oil);
						}
						else if (powerup instanceof SpeedboosterPowerup)
						{
							playerCar.setMaximumVelocity(3);
							playerCar.setAccelerationFactor(playerCar.getMaxVelocity() * 100);
							playerCar.accelerateForward();
						}
					}
				}
				for (Powerup pwr : powerupsDischarge)
				{
//					double px = pwr.getImage().getLayoutX();
//					double py = pwr.getImage().getLayoutY();
					if (playerCar.collisionDetection(pwr) && pwr.shouldCollide)
					{
						pwr.deactivate();
						if (pwr instanceof OilSpillPowerup)
						{
							playerCar.decelerateForward();
							playerCar.setAccelerate(false);
							playerCar.turn(45);
//							playerCar.move(px,py);
						}
						else if (pwr instanceof BananaDischargePowerup)
						{
							
						}
					}
				}

				playerCar.moveCarBy(dy);
				playerCar.turn(rot);
			}
		};
		timer.start();
	}
}
