package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
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
	private ArrayList<PlayerCar> players;
	private ArrayList<Powerup>	powerupsOnMap;
	private boolean				ok;
	private boolean speedb = false;
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
		playerCar.render(300, 450);
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

		for (Powerup bananaPowerup : powerupsOnMap)
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
		this.players = new ArrayList<>();
		this.playerCar = new PlayerCar(gameBackground);
		this.playerCar.playerNumber = 1;
		this.players.add(playerCar);

		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.playerCar2 = new PlayerCar(gameBackground);
			this.playerCar2.playerNumber = 2;
			this.players.add(playerCar2);
		}

//		this.playerCar.powerupsDischarge = new ArrayList<>();
//		this.playerCar2.powerupsDischarge = new ArrayList<>();

		// generates powerups
		int maxPowerups = 2;
		this.powerupsOnMap = new ArrayList<>();
		for (int i = 0; i < maxPowerups; i++)
		{
			this.powerupsOnMap.add(new BananaPowerup(gameBackground));
			this.powerupsOnMap.add(new SpeedboosterPowerup(gameBackground));
			this.powerupsOnMap.add(new OilGhostPowerup(gameBackground));
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
			int counter;
			int j = 0;

			@Override
			public void handle(long now){
				if (j == 0) {
					gameManager.lapTimer();
					j++;
				}
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
				double forwardVelocity;
				if (speedb) {
					forwardVelocity = playerCar.getForwardSpeed()*2;
					counter++;
					if (counter > 100) {
						speedb = false;
						forwardVelocity = playerCar.getForwardSpeed() / 2;
						counter =0;
					}
				} else {
					forwardVelocity = playerCar.getForwardSpeed();
				}
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

				for (Powerup powerup : powerupsOnMap)
				{
					if (powerup instanceof BananaPowerup || powerup instanceof OilGhostPowerup || powerup instanceof SpeedboosterPowerup) {
				    	if (powerup.shouldCollide)
						{ 
							if (playerCar.collisionDetection(powerup)) {
								playerCar.handleMapPowerups(powerup);
							} else if (playerCar2.collisionDetection(powerup)) {
								playerCar2.handleMapPowerups(powerup); 
							}
						} else if (!powerup.shouldCollide) {
							if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
								powerup.activate();
							}
						}
					}		
				}
			}

			private void usePowerup(){
				for (PlayerCar player : players) {
					if (player.isActivatedPowerup()) {
						if (!player.getPowerups().isEmpty()) {
							if ((player.getPickedUpPwrtime() + 2000) < System.currentTimeMillis()) {
								/*if (playerCar.powerups.getFirst() instanceof SpeedboosterPowerup) {
									speedb = true;
									playerCar.powerups.pop();
									playerCar.powerUpBar.removeFirstPowerup(playerCar.playerNumber);
									playerCar.setPickedUpPwrtime(System.currentTimeMillis());
								} else {
								}*/
								checkPowerupNotNull(player.usePowerup());

							}
						}
					}
				}
			}


			private void powerupDrop(){
				for (Powerup pwr : powerupsOnMap)
				{
					for (PlayerCar player : players) {
						if (player.collisionDetection(pwr) && pwr.shouldCollide)
						{
							if (pwr instanceof OilSpillPowerup)
							{
								pwr.deactivate();
								player.activatePowerup("carSlide");
							}
							else if (pwr instanceof BananaDischargePowerup)
							{
								pwr.deactivate();
								SoundManager.play("bananaFall");
								player.activatePowerup("carSpin");
							}
							powerupsOnMap.remove(pwr);
						}
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

	public void checkPowerupNotNull (Powerup powerup) {
		if (powerup != null) {
			powerupsOnMap.add(powerup);
		}
	}


}
