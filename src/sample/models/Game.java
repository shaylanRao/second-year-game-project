package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.Main;
import sample.controllers.audio.SoundManager;
import sample.controllers.game.RandomTrackScreen;

import java.util.*;

/**
 * The class that contains the main game loop
 */

public class Game
{

	private PlayerCar			playerCar;
	private PlayerCar           playerCar2;
	private ArrayList<Powerup>	powerups;
	private ArrayList<Powerup>	powerupsDischarge;
	private boolean				ok;
	private GameManager gameManager;

	public PlayerCar getPlayerCar()
	{
		return playerCar;
	}
	public PlayerCar getPlayerCar2()
	{
		return playerCar2;
	}
	/**
	 * Sets initial game state
	 */
	private void initialiser()
	{
		playerCar.render(1400, 700);
		playerCar.getImageView().setRotate(90);
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			playerCar2.render(350, 500);
		}
		Random random = new Random();
		ArrayList<Point> spawnPoints = Main.track.getPowerupSpawns();
		Point spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));
		double x = spawnPoint.getXConverted();
		double y = spawnPoint.getYConverted();
		x -= 25;
		y -= 25;

		for (Powerup bananaPowerup : powerups)
		{
			spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));
			spawnPoints.remove(spawnPoint);
			x = spawnPoint.getXConverted();
			y = spawnPoint.getYConverted();
			//TODO find a way to get image dimensions programmatically
			x -= 25;
			y -= 25;
			bananaPowerup.render(x, y);
		}
		gameManager = new GameManager();
	}

	public void initialiseGameObjects(Pane gameBackground)
	{
		// this method should take in all the necessary info from the GameController and initialise the playerCars
		this.playerCar = new PlayerCar(gameBackground);
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.playerCar2 = new PlayerCar(gameBackground);
		}
		this.powerupsDischarge = new ArrayList<>();
		this.playerCar.powerupsDischarge = new ArrayList<>();

		// generates powerups
		int maxPowerups = 2;
		this.powerups = new ArrayList<>();
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
			private double dy;
			private double rot;
			private double dy2;
			private double rot2;

			@Override
			public void handle(long now){
				this.carMovement();

				this.powerupPickup();

				this.usePowerup();

				this.powerupDrop();

				this.makeRandomTrack();

				this.lapSystem();
			}

			private void carMovement(){
				dy = 0;
				rot = 0;

				double forwardVelocity = playerCar.getForwardSpeed();
				double turningSpeed = playerCar.getTurningSpeed();
				this.dy -= forwardVelocity;
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

				playerCar.moveCarBy(dy);
				if (rot > 2.3) {
					rot = 2.3;
				} else if (rot < - 2.3) {
					rot = - 2.3;
				}

				playerCar.turn(rot);

				if(Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					dy2 = 0;
					rot2 = 0;
					double forwardVelocity2 = playerCar2.getForwardSpeed();
					double turningSpeed2 = playerCar2.getTurningSpeed();
					if (playerCar2.isGoingForward()){
						dy2 -= forwardVelocity2;
					}
					if (playerCar2.isGoingBackward())
					{
						dy2 += 1;
					}
					if (playerCar2.isTurnLeft())
					{
						rot2 -= turningSpeed2;
					}
					if (playerCar2.isTurnRight())
					{
						rot2 += turningSpeed2;
					}

					playerCar2.moveCarBy(dy2);
					if (rot2 > 2.3) {
						rot2 = 2.3;
					} else if (rot2 < - 2.3) {
						rot2 = - 2.3;
					}

					playerCar2.turn(rot2);
				}


			}

			private void powerupPickup(){
				/*
				 ShouldCollide is a boolean that helps solve a bug (when a car collides with a powerup and the discharge powerup is created,
				 then the powerup is set to visible(false), but the collision is still happening so a discharge powerup keeps popping on the screen)
				*/
				for (Powerup powerup : powerups)
				{
					if (powerup.shouldCollide && playerCar.collisionDetection(powerup))
					{
						SoundManager.play("prop");
						playerCar.addPowerup(powerup);
						powerup.deactivate();

						// calculating the position of the powerup and playerCar to position it
						//                        double playerCarLayoutX = playerCar.getImage().getLayoutX();
						//                        double playerCarLayoutY = playerCar.getImage().getLayoutY();
						//                        double powerupWidth = powerup.getImage().getBoundsInLocal().getWidth();
						//                        double powerupHeight = powerup.getImage().getBoundsInLocal().getHeight();
						//
						//                        double x = playerCarLayoutX - powerupWidth;
						//                        double y = playerCarLayoutY - powerupHeight;
					}
					else if (!powerup.shouldCollide) {
						if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
							powerup.activate();
						}
					}
				}
			}

			private void usePowerup(){
				if (playerCar.isActivatedPowerup())
				{
					if((playerCar.getPickedUpPwrtime() + 2000) < System.currentTimeMillis()) {
						for (Powerup powerup : playerCar.powerupsDischarge) {
						SoundManager.play("powerUp");
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
								playerCar.powerupsDischarge.remove(powerup);
								playerCar.powerupsDischarge.add(ban);
								playerCar.powerUpBar.removeFirstPowerup();
							}
							else if (powerup instanceof OilGhostPowerup)
							{
								OilSpillPowerup oil = new OilSpillPowerup(powerup.getGameBackground());
								oil.render(x, y);
								playerCar.powerupsDischarge.remove(powerup);
								playerCar.powerupsDischarge.add(oil);
								playerCar.powerUpBar.removeFirstPowerup();
							}
							else if (powerup instanceof SpeedboosterPowerup)
							{
								playerCar.powerupsDischarge.remove(powerup);
								playerCar.activatePowerup("speedBoost");
								playerCar.powerUpBar.removeFirstPowerup();
							}

							playerCar.setPickedUpPwrtime(System.currentTimeMillis());
							playerCar.getPowerups().pop();
						}
					}
				}
			}

			private void powerupDrop(){
				for (Powerup pwr : playerCar.powerupsDischarge)
				{
					if (playerCar.collisionDetection(pwr) && pwr.shouldCollide)

					{
						pwr.deactivate();
						if (pwr instanceof OilSpillPowerup)
						{
							playerCar.activatePowerup("carSlide");

						}
						else if (pwr instanceof BananaDischargePowerup)
						{
							SoundManager.play("bananaFall");
							playerCar.activatePowerup("carSpin");
						}
						playerCar.powerupsDischarge.remove(pwr);
					}
				}
			}

			private void makeRandomTrack(){
				if (Main.settings.getTrack().equals(Settings.Track.TRACK3)) {
					//set raycaster position and rotation = the car's position and rotation
					RandomTrackScreen.raycaster.setPos(new Point(Point.unconvertX(playerCar.getImageView().getLayoutX()+35),
							Point.unconvertY(playerCar.getImageView().getLayoutY()+18)));

					RandomTrackScreen.raycaster.setRot(playerCar.getImageView().getRotate());

					//this is the array of distances measured by the raycaster that we will use to train the RL algorithm
					double distances[] = RandomTrackScreen.raycaster.castRays(Main.track.getTrackLines(), false);
				}
			}

			private void lapSystem(){
				double gateDistances[] = RandomTrackScreen.raycaster.castRays(new ArrayList<>(Arrays.asList(Main.track.getGates()[gameManager.getNextGate()])), true);
//					System.out.println(Arrays.toString(gateDistances));
				gameManager.setGateDistances(gateDistances);
				//System.out.println(Arrays.toString(distances));
				gameManager.hitGate();
			}
		};

		timer.start();
	}




}
