package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.ai.GameEnv;
import sample.models.audio.SoundManager;

import java.util.*;

import javafx.scene.shape.Line;
import javafx.stage.Screen;

/**
 * The class that contains the main game loop
 */

public class Game {

	private final Pane pane;
	private PlayerCar playerCar;
    private PlayerCar playerCar2;
    private Car aiCar;

    private boolean ok;
    private boolean speedb = false;
    private GameManager gameManager;
	private ArrayList<PlayerCar> players;
	private ArrayList<Powerup>	powerupsOnMap;
	private boolean speedBoost = false;
	private IntroCountdown[] intro;
	private int  intro_frame   = 0;
	private long dt = 0;
	private long start_time = 0;
	private GameManager g2;
	private double[] distances;
	private double[] distances2;

	private double[] distanceToCar;

	private boolean raceStart = false;

	private AnimationTimer timer;
	private float baseReward;

	public AnimationTimer getTimer() {
		return timer;
	}

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

	private double[] startXY;
	private double[] start2XY;


	public double[] getDistances() {
        return distances;
    }


    public PlayerCar getPlayerCar() {
        return playerCar;
    }

    public PlayerCar getPlayerCar2() {
        return playerCar2;
    }

    public Car getAiCar() {
        return aiCar;
    }

    /**
     * Sets initial game state
     */

    public Game(Pane pane) {
    	this.pane = pane;
        gameManager = new GameManager(pane);
        switch (Main.settings.getPlayMode()) {
			case AI_TRAIN:
                System.out.println("ai train mode");
				aiCar = new Car(pane, Settings.VehicleType.VEHICLE1);
				car1Height= aiCar.getCarHeightWidth()[0];
				car1Width = aiCar.getCarHeightWidth()[1];
				startXY = getCar1SpawnPoint(Main.track.getFinishLine());
				aiCar.render(startXY[0], startXY[1]);
				aiCar.getRaycaster().setPos(new Point(Point.unconvertX(aiCar.getImage().getLayoutX() + (car1Height/2)), Point.unconvertY(aiCar.getImage().getLayoutY() + (car1Width/2))));
				distances = new double[8];
                raceStart = true;
                break;
            case AI:
                System.out.println("ai mode");
                this.playerCar = new PlayerCar(pane, Main.settings.getVehicleType());
                playerCar.render(1400, 700);
                //playerCar.getImageView().setRotate(90);

                aiCar = new Car(pane, Settings.VehicleType.VEHICLE1);
                aiCar.render(350, 500);
				aiCar.getRaycaster().setPos(new Point(Point.unconvertX(aiCar.getImage().getLayoutX() + (car1Height/2)), Point.unconvertY(aiCar.getImage().getLayoutY() + (car1Width/2))));
				distances = new double[8];
                //TrainCar.test();
                break;
			default:
				initialiseGameObjects(pane);
				//initialiser();
        }

    }

    public void resetAICar() {
		//System.out.println("time: " + gameManager.getTimeElapsed());
		System.out.println("car reset");
		pane.getChildren().remove(aiCar.getImageView());
		aiCar.getRaycaster().removeLines();
		startXY = getCar1SpawnPoint(Main.track.getFinishLine());
		aiCar = new Car(pane, Settings.VehicleType.VEHICLE1);
		aiCar.render(startXY[0], startXY[1]);
		aiCar.getRaycaster().setPos(new Point(Point.unconvertX(aiCar.getImage().getLayoutX() + (car1Height/2)), Point.unconvertY(aiCar.getImage().getLayoutY() + (car1Width/2))));
	}

	private void initialiser()
	{
		this.startXY = this.getCar1SpawnPoint(Main.track.getFinishLine());
		car1Height= playerCar.getCarHeightWidth()[0];
		car1Width = playerCar.getCarHeightWidth()[1];
		//playerCar.render(startXY[0], startXY[1]);
		playerCar.render(startXY[0], startXY[1]);
		System.out.println(String.format("x: %f, y: %f", startXY[0], startXY[1]));
		//playerCar.getImageView().setRotate(90);
		//todo consider converting these to use rot and pos
		playerCar.getRaycaster().setPos(new Point(Point.unconvertX(playerCar.getImage().getLayoutX() + (car1Height/2)), Point.unconvertY(playerCar.getImage().getLayoutY() + (car1Width/2))));
		playerCar.getRaycaster().setRot(playerCar.getImageView().getRotate());
		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.start2XY = this.getCar2SpawnPoint(Main.track.getFinishLine());
			car2Height= playerCar2.getCarHeightWidth()[0];
			car2Width = playerCar2.getCarHeightWidth()[1];
			playerCar2.render(start2XY[0], start2XY[1]);
			//playerCar2.getImageView().setRotate(90);
			playerCar2.getRaycaster().setPos(new Point(Point.unconvertX(playerCar2.getImageView().getLayoutX() + (car2Height/2)), Point.unconvertY(playerCar2.getImageView().getLayoutY() + (car2Width/2))));
			playerCar2.getRaycaster().setRot(playerCar2.getImageView().getRotate());
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
    	if (finishLine==null) {
			System.out.println("finish line was null");
		} else if(playerCar==null) {
			//System.out.println("player car null");
		}
		double[] xyValues = new double[2];
		double distance = (finishLine.getStartX()) - (finishLine.getEndX());
		xyValues[0] = finishLine.getEndX() + (distance/3) - (car1Width/2);
		//xyValues[1]= finishLine.getStartY() + (car1Height);
		xyValues[1]= finishLine.getStartY() - ((car1Height/2)-5);
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
		intro[0] = new IntroCountdown(gameBackground, 0);
		intro[1] = new IntroCountdown(gameBackground, 1);
		intro[2] = new IntroCountdown(gameBackground, 2);
		intro[3] = new IntroCountdown(gameBackground, 3);

		// this method should take in all the necessary info from the GameController and initialise the playerCars
		this.players = new ArrayList<>();

		//todo Change vehicle type to player 1 vehicle type
		this.playerCar = new PlayerCar(gameBackground, Main.settings.getVehicleType());
		this.playerCar.playerNumber = 1;
		this.players.add(playerCar);

		if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {

			//todo Change vehicle type to player 2 vehicle type
			this.playerCar2 = new PlayerCar(gameBackground, Main.settings.getVehicle2Type());
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
     */
    private double dy;
    private double rot;
    private double dy2;
    private double rot2;
    private int counter;
    private int j = 0;

    public void gameLoopAI() {
        if (j == 0) {
            gameManager.lapTimer();
            j++;
        }

		castRays();

		carMovement(aiCar, 0, 0, distances);

        //powerupPickup();

        //usePowerup();

        //powerupDrop();


        lapSystem();
        //we don't handle reward for the car dying or crossing the gate here

		if (!aiCar.isDead()) {
			//GameEnv.setCurrentReward((float) ((80000/gameManager.getDistanceToNextGate(aiCar)) - Math.pow(0.01*gameManager.getTimeElapsed(), 1.01)));
			GameEnv.setCurrentReward(calculateReward());
			//GameEnv.setCurrentReward(1);
		}
    }

    private float calculateReward() {
    	//return inverse of distance to next gate plus 1000 * the last gate so that reward keeps going up as car goes through gates
    	return (float) (1/gameManager.getDistanceToNextGate(aiCar)) + (gameManager.getNextGate()-1);
	}

    public synchronized void gameLoop() throws InterruptedException {
        this.initialiser();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
				if (j == 0) {
					gameManager.lapTimer();
					j++;
				}

				castRays();

				double rot = 0;
				double dy = 0;
				carMovement(playerCar, dy, rot, distances);

				if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					double dy2 = 0;
					double rot2 = 0;
					carMovement(playerCar2, dy2, rot2, distances2);
					//System.out.println("RUNS");
					carOnCarColl();
				}

				renderIntroCountdown();

				powerupPickup();

				usePowerup();

				powerupDrop();

				lapSystem();


			}
        };

        timer.start();
    }

	private int  collCounter = 0;

	private boolean carOnCarColl(){
		ProjectionRectangle rect1 = new ProjectionRectangle(playerCar, playerCar.getRaycaster().getRayRect().get(0));
		ProjectionRectangle rect2 = new ProjectionRectangle(playerCar2, playerCar2.getRaycaster().getRayRect().get(0));
		collCounter += 1;
		if(collCounter % 20 == 0) {
			collCounter = 0;
			if (playerCar.testCollision(rect1, rect2)) {
//             System.out.println(playerCar.getForwardSpeed());
				//playerCar.setSpeed(-playerCar.getForwardSpeed());
				//todo add physics
				double[][] values = playerCar.momCollCalc(playerCar, playerCar2);
//             playerCar.setForceSpeed(22);
				playerCar.setForceSpeed(values[0][0]);
				playerCar.getImageView().setRotate(values[0][1]);
				playerCar2.setForceSpeed(values[1][0]);
				playerCar2.getImageView().setRotate(values[1][1]);
				System.out.println("Player car 1: " + playerCar.getForceSpeed());
				System.out.println("Player car 1: " + values[0][1]);
				System.out.println("Player car 2: " + playerCar2.getForceSpeed());
				System.out.println("Player car 2: " + values[1][1]);

//             if ((playerCar.getMass() * playerCar.getForwardSpeed()) > (playerCar2.getMass() * playerCar2.getForwardSpeed())) {
//                playerCar.setSpeed(playerCar.getForwardSpeed() * -0.6);
//                playerCar.setForceSpeed(playerCar.getForceSpeed() * -0.6);
//             } else {
//                playerCar2.setSpeed(playerCar2.getForwardSpeed() * -0.6);
//                playerCar2.setForceSpeed(playerCar2.getForceSpeed() * -0.6);
//             }

//             playerCar.setSpeed(2);
//             playerCar2.setForceSpeed(1);

				return true;
			}
		}
		return false;
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
			player.setForceSpeed(player.getForceSpeed()*0.6);
		}
		else{
			player.setForceSpeed(0);
			player.setSpeed(0);
		}
		raceStart = true;
	}


	private void carMovement(Car player, double coordPos, double coordRot, double[] rcDistances) {
		double forwardVelocity;
		if (player.wallCollision(rcDistances)) {
			if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
				//reset time because episode is over when car crashes
				gameManager.resetMillis();
				gameManager.resetGateStack();
				player.die();
			}
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

		if(!raceStart && Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
			this.initialColl(player, rcDistances);
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


	private void powerupPickup() {
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


			private void castRays(){



//				distanceToCar = RandomTrackScreen.raycaster.castRays(Main.track.getTrackLines(), true);


				//this is the array of distances measured by the raycaster that we will use to train the RL algorithm
				if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
					distances = aiCar.getRaycaster().castRays(Main.track.getTrackLines(), false);

				} else {
					distances = playerCar.getRaycaster().castRays(Main.track.getTrackLines(), false);
					if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
						distances2 = playerCar2.getRaycaster().castRays(Main.track.getTrackLines(), false);
					}
				}
			}

			private void lapSystem(){
				//Gets ray cast for next gate
				double[] gateDistances;
				if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
					gateDistances = aiCar.getRaycaster().castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[gameManager.getNextGate()])), true);
				} else {
					gateDistances = playerCar.getRaycaster().castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[gameManager.getNextGate()])), false);
				}

				gameManager.setGateDistances(gateDistances);
				gameManager.hitGate();
				if(Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
					double[] gateDistances2 = playerCar.getRaycaster().castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[g2.getNextGate()])), false);
					g2.setGateDistances(gateDistances2);
					g2.hitGate();
				}
			}

	public void checkPowerupNotNull (Powerup powerup) {
		if (powerup != null) {
			powerupsOnMap.add(powerup);
		}
	}

	private void initialColl(Car player, double[] rcDistances){
		if (player.wallCollision(rcDistances)){
			if(player == playerCar){
				startXY[0] += player.getCarHeightWidth()[1]/2;
				player.getImage().relocate(startXY[0], startXY[1]);
			}
			else{
				start2XY[0] -= player.getCarHeightWidth()[1]/2;
				player.getImage().relocate(start2XY[0], start2XY[1]);
			}
		}

		if(this.carOnCarColl()){
			startXY[0] -= player.getCarHeightWidth()[1]/3;
			start2XY[0] += player.getCarHeightWidth()[1]/3;
			playerCar.getImage().relocate(startXY[0], startXY[1]);
			playerCar2.getImage().relocate(start2XY[0], start2XY[1]);
		}

	}


}
