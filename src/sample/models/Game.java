package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
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
	private ArrayList<PlayerCar> players;
	private ArrayList<Powerup>	powerupsOnMap;
	private boolean speedBoost = false;
	private GameManager gameManager;
	private IntroCountdown[] intro;
	private int  intro_frame   = 0;
	private long dt = 0;
	private long start_time = 0;
	private GameManager g2;
	private double[] distances;
	private double[] distances2;

	private double[] distanceToCar;

	private boolean raceStart = false;


	public void updateTime()
	{
		Date date = new Date();
		long current_time = date.getTime();

		this.dt += (current_time - this.start_time);
		this.start_time = current_time;
	}
	public long getTimePassed()
	{
		return this.dt;
	}

	private double car1Height;
	private double car1Width;

	private double car2Height;
	private double car2Width;

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
		double[] startXY = this.getCar1SpawnPoint(Main.track.getFinishLine());
		car1Height= playerCar.getCarHeightWidth()[0];
		car1Width = playerCar.getCarHeightWidth()[1];


		playerCar.render(startXY[0], startXY[1]);
		playerCar.getImageView().setRotate(90);
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			startXY = this.getCar2SpawnPoint(Main.track.getFinishLine());
			car2Height= playerCar.getCarHeightWidth()[0];
			car2Width = playerCar.getCarHeightWidth()[1];
			playerCar2.render(startXY[0], startXY[1]);
			playerCar2.getImageView().setRotate(90);
		}
		Random random = new Random();
		ArrayList<Point> spawnPoints = Main.track.getPowerupSpawns();
		Point spawnPoint;
		double x;
		double y;

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
		intro[intro_frame].render((Screen.getPrimary().getBounds().getWidth()/2)-(intro[intro_frame].getImage().getFitWidth()/2), (Screen.getPrimary().getBounds().getHeight()/2)-(intro[intro_frame].getImage().getFitHeight()*0.6));
	}



	private double[] getCar1SpawnPoint(Line finishLine){
		double[] xyValues = new double[2];
		double distance = (finishLine.getStartX()) - (finishLine.getEndX());
		xyValues[0] = finishLine.getEndX() + (distance/3) - (playerCar.getImage().getBoundsInParent().getWidth()/2);
		xyValues[1]= finishLine.getStartY() + (playerCar.getImage().getBoundsInParent().getHeight());
		return xyValues;
	}

	private double[] getCar2SpawnPoint(Line finishLine){
		double[] xyValues = new double[2];
		double distance = (finishLine.getStartX()) - (finishLine.getEndX());
		xyValues[0] = finishLine.getStartX() - (distance/3) - (playerCar2.getImage().getBoundsInParent().getWidth()/2);
		xyValues[1]= finishLine.getStartY() + (playerCar2.getImage().getBoundsInParent().getHeight());
		return xyValues;
	}


	public void initialiseGameObjects(Pane gameBackground)
	{


		intro = new IntroCountdown[4];
		intro[0] = new IntroCountdown(gameBackground, 0) ;
		intro[1] = new IntroCountdown(gameBackground, 1);
		intro[2] = new IntroCountdown(gameBackground, 2) ;
		intro[3] = new IntroCountdown(gameBackground, 3);

		// this method should take in all the necessary info from the GameController and initialise the playerCars
		this.players = new ArrayList<>();
		this.playerCar = new PlayerCar(gameBackground);
		this.playerCar.playerNumber = 1;
		this.players.add(playerCar);

		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.playerCar2 = new PlayerCar(gameBackground);
			g2 = new GameManager(gameBackground);
			g2.wordBar(1650,60);
			g2.fixBar(1800,80);
			this.playerCar2.playerNumber = 2;
			this.players.add(playerCar2);
		}
		//		this.playerCar.powerupsDischarge = new ArrayList<>();
		gameManager = new GameManager(gameBackground);
		gameManager.wordBar(0,60);
		gameManager.fixBar(150,80);


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
	public synchronized void gameLoop() {
		this.initialiser();
		AnimationTimer timer = new AnimationTimer() {
			int counter;
			int j = 0;

			@Override
			public void handle(long now) {
				if (j == 0) {
					gameManager.lapTimer();
					j++;
				}

				this.makeRandomTrack();

				double rot = 0;
				double dy = 0;
				this.carMovement(playerCar, dy, rot, distances);

				if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					double dy2 = 0;
					double rot2 = 0;
					this.carMovement(playerCar2, dy2, rot2, distances2);
					this.carOnCarColl();
				}

				this.renderIntroCountdown();

				this.powerupPickup();

				this.usePowerup();

				this.powerupDrop();

				this.lapSystem();

			}


			private void renderIntroCountdown() {
				// case : we have played all the intro countdown, so we just return
				if(intro_frame > 3) {
					return;
				}
				updateTime();
				long time_passed = getTimePassed();
				if(time_passed > 5000) // an error case preventing just for the first frame
				{
					time_passed = 0;
					dt = 0;
				}

				// if time passed on current frame >= 1000 (1second)
				// go to next frame
				if(time_passed >= 1000)
				{
					intro[intro_frame].deactivate(); // deactivate previous frame
					intro_frame++;					 // increment frame index
					dt = 0;							 // reset dt (time passed)

					// render the new frame
					try
					{
						if(intro_frame <= 3 && intro_frame != 0)
						{
//							intro[intro_frame].render(500, 50);
//							System.out.println((int)Screen.getPrimary().getBounds().getWidth());
							intro[intro_frame].render((Screen.getPrimary().getBounds().getWidth()/2)-(intro[intro_frame].getImage().getFitWidth()/2), (Screen.getPrimary().getBounds().getHeight()/2)-(intro[intro_frame].getImage().getFitHeight()*0.6));// render( x, y )
						}
						else{
							startBoost(playerCar);
							if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
							startBoost(playerCar2);
							}

						}

					}
					catch(Exception ignored){}

				}

			}


			private void startBoost(PlayerCar player){
				if(player.getForwardSpeed() < 2.6 && player.getForwardSpeed() > 2.2){
					System.out.println("BOOST");
					player.setForceSpeed(player.getForceSpeed()*0.6);
				}
				else{
					System.out.println("NO BOOST");
					player.setForceSpeed(0);
					player.setSpeed(0);
				}
				raceStart = true;
			}

			private void carMovement(PlayerCar player, double coordPos, double coordRot, double[] rcDistances) {
				double forwardVelocity;
				if (player.wallCollision(rcDistances)) {
					double sumBackwards = rcDistances[0] + rcDistances[1] + rcDistances[2];
					double sumForwards = rcDistances[7] + rcDistances[6] + rcDistances[5];
					player.setForceSpeed(0);
					if (sumBackwards > sumForwards) {
						if (player.isGoingBackward()) {
							forwardVelocity = player.getForwardSpeed();
							coordPos -= forwardVelocity;
						}
					} else {
						if (player.isGoingForward()) {
							forwardVelocity = player.getForwardSpeed();
							if (forwardVelocity == 0) {
								player.setSpeed(0.5);
							}
							coordPos -= forwardVelocity;
						}
					}
				} else if (speedBoost) {
					forwardVelocity = player.getForwardSpeed() * 2;
					counter++;
					if (counter > 100) {
						speedBoost = false;
						forwardVelocity = player.getForwardSpeed() / 2;
						counter = 0;
					}
					coordPos -= forwardVelocity;
				} else {
					forwardVelocity = player.getForwardSpeed();
					coordPos -= forwardVelocity;
				}

				double turningSpeed = player.getTurningSpeed();

				if (player.isTurnLeft()) {
					coordRot -= turningSpeed;
				}
				if (player.isTurnRight()) {
					coordRot += turningSpeed;
				}


				if (coordRot > 2.3) {
					coordRot = 2.3;
				} else if (coordRot < -2.3) {
					coordRot = -2.3;
				}

				if (!gameManager.finishedLaps() && raceStart) {
					//moves around screen
					player.moveCarBy(coordPos);
					//rotates the car image
					player.turn(coordRot);
				}

				gameManager.updateBar(95, 80);
				if (player == playerCar2) {
					g2.updateBar(1745, 80);
				}
			}


			private	void carOnCarColl(){
//				if(playerCar.collisionDetection(playerCar2)){
//					System.out.println(playerCar.getImage().boundsInParentProperty());
//				}
				//ProjectionRectangle rect1 = new ProjectionRectangle(raycaster.getRayRect().get(0));
				//ProjectionRectangle rect2 = new ProjectionRectangle(r2.getRayRect().get(0));
				ProjectionRectangle rect1 = new ProjectionRectangle(playerCar);
				ProjectionRectangle rect2 = new ProjectionRectangle(playerCar2);

//				if(rect1.intersects(rect2.getLayoutBounds())){
//					System.out.println("CRASH");
//				}

				if (playerCar.testCollision(rect1, rect2)) {
					System.out.println("crash");
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
						playerCar.handleMapPowerups(powerup);
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
								player.movementPowerup("carSlide");
							}
							else if (pwr instanceof BananaDischargePowerup)
							{
								pwr.deactivate();
								SoundManager.play("bananaFall");
								player.movementPowerup("carSpin");
							}
							powerupsOnMap.remove(pwr);
						}
					}
				}
			}


			private void makeRandomTrack(){
				double[] xyCoord = this.getCarCoord(playerCar);

				//set raycaster position and rotation = the car's position and rotation
				raycaster.setPos(new Point(Point.unconvertX(xyCoord[0]),
						Point.unconvertY(xyCoord[1])));
				raycaster.setRot(playerCar.getImageView().getRotate());

				if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					xyCoord = this.getCarCoord(playerCar2);

					r2.setPos(new Point(Point.unconvertX(xyCoord[0]),
							Point.unconvertY(xyCoord[1])));
					r2.setRot(playerCar2.getImageView().getRotate());
					distances2 = r2.castRays(Main.track.getTrackLines(), true);
				}

//				distanceToCar = RandomTrackScreen.raycaster.castRays(Main.track.getTrackLines(), true);


				//this is the array of distances measured by the raycaster that we will use to train the RL algorithm
				distances = RandomTrackScreen.raycaster.castRays(Main.track.getTrackLines(), false);
			}



			public double[] getCarCoord(PlayerCar car){
				double[] xyCoord = new double[2];
				xyCoord[0] = car.getImageView().getLayoutX()+ (car1Width/2);
				xyCoord[1] = car.getImageView().getLayoutY()+ (car1Height/2);
				return xyCoord;
			}

			private void lapSystem(){
				//Gets ray cast for next gate
				double[] gateDistances = raycaster.castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[gameManager.getNextGate()])), false);
				gameManager.setGateDistances(gateDistances);
				gameManager.hitGate();
				if(Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					double[] gateDistances2 = r2.castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[g2.getNextGate()])), false);
					g2.setGateDistances(gateDistances2);
					g2.hitGate();
				}
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
