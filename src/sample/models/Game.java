package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controllers.audio.SoundManager;
import sample.controllers.game.RandomTrackScreen;

import java.util.*;

import static sample.controllers.game.RandomTrackScreen.r2;
import static sample.controllers.game.RandomTrackScreen.raycaster;


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
	private boolean speedBoost = false;
	private GameManager gameManager;
	private GameManager g2;
	private double distances[];
	private double distances2[];


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
		playerCar.render(1750, 600);
		playerCar.getImageView().setRotate(90);
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			playerCar2.render(1750, 600);
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
	}

	public void initialiseGameObjects(Pane gameBackground)
	{
		// this method should take in all the necessary info from the GameController and initialise the playerCars
		this.playerCar = new PlayerCar(gameBackground);
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.playerCar2 = new PlayerCar(gameBackground);
			g2 = new GameManager(gameBackground);
			g2.wordBar(1650,60,playerCar2);
			g2.fixBar(1800,80,playerCar2);
		}
		this.powerupsDischarge = new ArrayList<>();
		this.playerCar.powerupsDischarge = new ArrayList<>();
		gameManager = new GameManager(gameBackground);
		gameManager.wordBar(0,60,playerCar);
		gameManager.fixBar(150,80,playerCar);


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
			private double dy = 0;
			private double rot = 0;
			private double dy2 = 0;
			private double rot2 = 0;
			int counter;
			int j = 0;

			@Override
			public void handle(long now){
				if (j == 0) {
					gameManager.lapTimer();
					j++;
				}
				this.makeRandomTrack();

				this.carMovement(playerCar, this.dy, this.rot, distances);

				if(Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					this.carMovement(playerCar2, this.dy2, this.rot2, distances2);
				}

				this.powerupPickup();

				this.usePowerup();

				this.powerupDrop();

				this.lapSystem();

			}


			private void carMovement(PlayerCar player, double coordPos, double coordRot, double[] rcDistances){
				double forwardVelocity;

				if (player.wallCollision(rcDistances)){
					double sumBackwards = rcDistances[0] + rcDistances[1] + rcDistances[2];
					double sumForwards = rcDistances[7] + rcDistances[6] + rcDistances[5];
					player.setForceSpeed(0);
					if (sumBackwards > sumForwards) {
						if (player.isGoingBackward()) {
							forwardVelocity = player.getForwardSpeed();
//							System.out.println("BACKWARDS");
							coordPos -= forwardVelocity;
						}
					} else {
						if (player.isGoingForward()) {
							forwardVelocity = player.getForwardSpeed();
//							System.out.println("FORWARDS");
							if (forwardVelocity == 0){
								player.setSpeed(0.5);
							}
							coordPos -= forwardVelocity;
						}
					}
				}
				else if (speedBoost){
					forwardVelocity = player.getForwardSpeed()*2;
					counter++;
					if (counter > 100) {
						speedBoost = false;
						forwardVelocity = player.getForwardSpeed() / 2;
						counter = 0;
					}
					coordPos -= forwardVelocity;
				} else {
					forwardVelocity = player.getForwardSpeed();
//					System.out.println(forwardVelocity);
					coordPos -= forwardVelocity;
				}

				double turningSpeed = player.getTurningSpeed();

				if (player.isTurnLeft())
				{
					coordRot -= turningSpeed;
				}
				if (player.isTurnRight())
				{
					coordRot += turningSpeed;
				}

				player.moveCarBy(coordPos);
				if (coordRot > 2.3) {
					coordRot = 2.3;
				} else if (coordRot < - 2.3) {
					coordRot = - 2.3;
				}

				player.turn(coordRot);
				coordPos = 0;
				coordRot = 0;
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
								SoundManager.stop("powerUp");
								SoundManager.play("SpeedBoost");
								//playerCar.activatePowerup("speedBoost");
								speedBoost = true;
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
							SoundManager.play("OilFall");
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
				//set raycaster position and rotation = the car's position and rotation
				raycaster.setPos(new Point(Point.unconvertX(playerCar.getImageView().getLayoutX()+35),
						Point.unconvertY(playerCar.getImageView().getLayoutY()+18)));
				raycaster.setRot(playerCar.getImageView().getRotate());

				if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					r2.setPos(new Point(Point.unconvertX(playerCar2.getImageView().getLayoutX()+35),
							Point.unconvertY(playerCar2.getImageView().getLayoutY()+18)));
					r2.setRot(playerCar2.getImageView().getRotate());
					distances2 = r2.castRays(Main.track.getTrackLines(), true);
				}

				//this is the array of distances measured by the raycaster that we will use to train the RL algorithm
				distances = RandomTrackScreen.raycaster.castRays(Main.track.getTrackLines(), true);
			}

			private void lapSystem(){
				//Gets ray cast for next gate
				double gateDistances[] = raycaster.castRays(new ArrayList<>(Arrays.asList(Main.track.getGates()[gameManager.getNextGate()])), false);
				gameManager.setGateDistances(gateDistances);
				gameManager.hitGate();
				//System.out.println(gameManager.lapCounter);
				if(Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					double gateDistances2[] = r2.castRays(new ArrayList<>(Arrays.asList(Main.track.getGates()[g2.getNextGate()])), false);
					g2.setGateDistances(gateDistances2);
					g2.hitGate();
					//System.out.println(g2.lapCounter);
				}
			}

			private void boundaryCollision(){
				playerCar.wallCollision(distances);
				if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					playerCar2.wallCollision(distances2);
				}
			}

		};

		timer.start();
	}

}
