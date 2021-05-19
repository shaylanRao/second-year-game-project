package sample.models;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Screen;
import sample.Main;
import sample.models.ai.AICar;
import sample.models.audio.SoundManager;
import sample.controllers.ui.LeaderboardScreen;

import java.util.*;
/**
 * The class that contains the main game loop
 */
//todo convert arrays of length 2 to Points
public class Game {

    private boolean is_showing_leaderboard = false;
    private boolean is_race_completed = false;

    private PlayerCar playerCar;
    private PlayerCar playerCar2;
    private ArrayList<PlayerCar> players;
    private ArrayList<Powerup> powerupsOnMap;
    private boolean speedBoost = false;
    private GameManager gameManager;
    private IntroCountdown[] intro;
    private int intro_frame = 0;
    private long dt = 0;
    private long start_time = 0;
    private GameManager gameManager2;
    private double[] distances;

    private double[] distances2;

    private double[] distanceToCar;

    private boolean raceStart = false;

    private AnimationTimer timer;
    private AICar aiCar;

    public AnimationTimer getTimer() {
        return timer;
    }

    public void updateTime() {
        Date date = new Date();
        long current_time = date.getTime();

        this.dt += (current_time - this.start_time);
        this.start_time = current_time;
    }

    public long getTimePassed() {
        return this.dt;
    }

    private double car1Height;
    private double car1Width;

    private double car2Height;
    private double car2Width;
    private double diagLen = 0;

    private double[] startXY;
    private double[] start2XY;


    public PlayerCar getPlayerCar() {
        return playerCar;
    }

    public PlayerCar getPlayerCar2() {
        return playerCar2;
    }

    /**
     * Sets initial game state
     */
    private void initialiser() {
        this.startXY = this.getCar1SpawnPoint(Main.track.getFinishLine());
        car1Height = playerCar.getCarHeightWidth()[0];
        car1Width = playerCar.getCarHeightWidth()[1];
        diagLen = (Math.sqrt((Math.pow(car1Height, 2) + Math.pow(car1Width, 2)) / 4));
        playerCar.render(startXY[0], startXY[1]);
        playerCar.getImageView().setRotate(90);

        if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            this.start2XY = this.getCar2SpawnPoint(Main.track.getFinishLine());
            car2Height = playerCar.getCarHeightWidth()[0];
            car2Width = playerCar.getCarHeightWidth()[1];
            playerCar2.render(start2XY[0], start2XY[1]);
            playerCar2.getImageView().setRotate(90);
        } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {

        }
        Random random = new Random();
        ArrayList<Point> spawnPoints = Main.track.getPowerupSpawns();
        Point spawnPoint;
        double x;
        double y;

        for (Powerup bananaPowerup : powerupsOnMap) {
            spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));
            spawnPoints.remove(spawnPoint);
            x = spawnPoint.getXConverted();
            y = spawnPoint.getYConverted();
            x -= 25;
            y -= 25;
            bananaPowerup.render(x, y);
        }
        intro[intro_frame].render((Screen.getPrimary().getBounds().getWidth() / 2) - (intro[intro_frame].getImage().getFitWidth() / 2), (Screen.getPrimary().getBounds().getHeight() / 2) - (intro[intro_frame].getImage().getFitHeight() * 0.6));
    }


    private double[] getCar1SpawnPoint(Line finishLine) {
        double[] xyValues = new double[2];
        double distance = (finishLine.getStartX()) - (finishLine.getEndX());
        xyValues[0] = finishLine.getEndX() + (distance / 3) - (playerCar.getImage().getBoundsInParent().getWidth() / 2);
        xyValues[1] = finishLine.getStartY() + (playerCar.getImage().getBoundsInParent().getHeight());
        return xyValues;
    }

    private double[] getCar2SpawnPoint(Line finishLine) {
        //todo use polymorphism, i.e., Car car2 = new AICar or = new PlayerCar depending on game mode
        double[] xyValues = new double[2];
        double distance = (finishLine.getStartX()) - (finishLine.getEndX());
        xyValues[0] = finishLine.getStartX() - (distance / 3) - (playerCar.getImage().getBoundsInParent().getWidth() / 2);
        xyValues[1] = finishLine.getStartY() + (playerCar.getImage().getBoundsInParent().getHeight());
        return xyValues;
    }


    public void initialiseGameObjects(Pane gameBackground) {


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
            gameManager2 = new GameManager(gameBackground, playerCar2);
            gameManager2.wordBar(1650, 60);
            gameManager2.fixBar(1800, 80);
            this.playerCar2.playerNumber = 2;
            this.players.add(playerCar2);
            gameManager2.lapTimer();
            gameManager2.updateBar(1745, 80);
        } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {
            //todo add vehicle selection for AI car
            aiCar = new AICar(gameBackground, Main.settings.getVehicleType());
            start2XY = getCar2SpawnPoint(Main.track.getFinishLine());
            aiCar.render(start2XY[0], start2XY[1]);
            aiCar.getRaycaster().setPos(new Point(Point.unconvertX(aiCar.getImage().getLayoutX() + (car1Height/2)), Point.unconvertY(aiCar.getImage().getLayoutY() + (car1Width/2))));
            gameManager2 = new GameManager(gameBackground, aiCar);
            gameManager2.wordBar(1650, 60);
            gameManager2.fixBar(1800, 80);
            this.aiCar.playerNumber = 2;
            gameManager2.lapTimer();
            gameManager2.updateBar(1745, 80);
        }
        //		this.playerCar.powerupsDischarge = new ArrayList<>();
        gameManager = new GameManager(gameBackground, playerCar);
        gameManager.wordBar(0, 60);
        gameManager.fixBar(150, 80);
        gameManager.updateBar(95, 80);
        gameManager.lapTimer();

        // generates powerups
        int maxPowerups = 2;
        this.powerupsOnMap = new ArrayList<>();
        for (int i = 0; i < maxPowerups; i++) {
            this.powerupsOnMap.add(new BananaPowerup(gameBackground));
            this.powerupsOnMap.add(new SpeedboosterPowerup(gameBackground));
            this.powerupsOnMap.add(new OilGhostPowerup(gameBackground));
        }
    }

    /**
     * The game loop. This deals with game logic such as handling collisions and moving the car
     */
    public synchronized void gameLoop() {
        this.initialiser();
        timer = new AnimationTimer() {

            @Override
            public void handle(long now) {
                castRays();


                double rot = 0;
                double dy = 0;
                carMovement(playerCar, dy, rot, distances);

                if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
                    double dy2 = 0;
                    double rot2 = 0;
                    carMovement(playerCar2, dy2, rot2, distances2);
                    carOnCarColl();
                } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {
                    double dy2 = 0;
                    double rot2 = 0;
                    aiCar.chooseAction();
                    carMovement(aiCar, dy2, rot2, distances2);
                }

                leaderboard();

                renderIntroCountdown();

                powerupPickup();

                usePowerup();

                powerupDrop();

                lapSystem();
                //System.out.println("x: " + aiCar.getImageView().getLayoutX() + " y: " + aiCar.getImageView().getLayoutY());

            }
        };
        timer.start();
    }


    private void renderIntroCountdown() {
        // case : we have played all the intro countdown, so we just return
        if (intro_frame > 3) {
            return;
        }
        updateTime();
        long time_passed = getTimePassed();
        if (time_passed > 5000) // an error case preventing just for the first frame
        {
            time_passed = 0;
            dt = 0;
        }

        // if time passed on current frame >= 1000 (1second)
        // go to next frame
        if (time_passed >= 1000) {
            intro[intro_frame].deactivate(); // deactivate previous frame
            intro_frame++;                     // increment frame index
            dt = 0;                             // reset dt (time passed)

            // render the new frame
            try {
                if (intro_frame <= 3 && intro_frame != 0) {
                    intro[intro_frame].render((Screen.getPrimary().getBounds().getWidth() / 2) - (intro[intro_frame].getImage().getFitWidth() / 2), (Screen.getPrimary().getBounds().getHeight() / 2) - (intro[intro_frame].getImage().getFitHeight() * 0.6));// render( x, y )
                } else {
                    startBoost(playerCar);
                    if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
                        startBoost(playerCar2);
                    }
                }
            } catch (Exception ignored) {
            }
        }
    }


    private void startBoost(PlayerCar player) {
        if (player.getForwardSpeed() < 2.6 && player.getForwardSpeed() > 2.2) {
            player.setForceSpeed(player.getForceSpeed() * 0.6);
        } else {
            player.setForceSpeed(0);
            player.setSpeed(0);
        }
        raceStart = true;

    }

    int counter = 0;

    private void carMovement(Car player, double coordPos, double coordRot, double[] rcDistances) {
        double forwardVelocity;
            if (player.wallCollision(rcDistances)) {
                double sumBackwards = rcDistances[0] + rcDistances[1] + rcDistances[2];
                double sumForwards = rcDistances[7] + rcDistances[6] + rcDistances[5];
                double oldSpeed = player.getForceSpeed() / 6;

                if (rcDistances[1] <= diagLen || rcDistances[2] <= diagLen) {
                    if (player.isGoingForward()) {
                        player.setForceSpeed(-oldSpeed);
                        coordPos -= 2;
                    }
                } else if (rcDistances[5] <= diagLen || rcDistances[6] <= diagLen) {
                    if (player.isGoingBackward()) {
                        player.setForceSpeed(-oldSpeed);
                        coordPos += 2;
                    }
                } else if (sumBackwards > sumForwards) {
                    if (player.isGoingBackward()) {
                        player.setForceSpeed(-oldSpeed);
                        coordPos += oldSpeed;
                    }
                } else {
                    if (player.isGoingForward()) {
                        player.setForceSpeed(-oldSpeed);
                        coordPos += oldSpeed;
                    }
                }
            }
        else if (speedBoost) {
            forwardVelocity = player.getForwardSpeed() * 2;
            counter++;
            if (counter > 100) {
                speedBoost = false;
                forwardVelocity = player.getForwardSpeed() / 2;
                counter = 0;
            }
            coordPos -= forwardVelocity;
        } else {
            checkLapsOver(player);
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

        if (!raceStart && Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            this.initialColl(player, rcDistances);
        }

        if (raceStart) {
            //todo probably not great beacause it keeps setting them to true. only need to do it once
            if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)){
                aiCar.setGoingForward(true);
                aiCar.setAccelerate(true);
            }
            //moves around screen
            player.moveCarBy(coordPos);
            //rotates the car image
            player.turn(coordRot);
        }
    }


    private void leaderboard() {
        boolean has_player2_finished = true;
        if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            has_player2_finished = gameManager2.finishedLaps() && raceStart;
        }

        boolean has_player1_finished = gameManager.finishedLaps() && raceStart;
        is_race_completed = has_player1_finished && has_player2_finished;

        // if race is completed => show leaderboard Screen
        if (is_race_completed && !is_showing_leaderboard && Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            try {

                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/LeaderboardScreen.fxml"));
                Parent root = loader.load();
                Main.sceneManager.setCurrentRoot(root);

                LeaderboardScreen leaderboard_controller = loader.getController();
                leaderboard_controller.setUp(gameManager.lapCounter, gameManager.totalTime(),
                        gameManager2.lapCounter, gameManager2.totalTime());


                is_showing_leaderboard = true;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (is_race_completed && !is_showing_leaderboard) {
            //single player leaderboard
        }
    }

    private void checkLapsOver(Car player) {
        if (player == playerCar) {
            if (gameManager.finishedLaps()) {
                playerCar.setAccelerate(false);
                playerCar.setGoingBackward(false);
            }
        } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {
            if (gameManager2.finishedLaps()) {
                aiCar.setAccelerate(false);
                aiCar.setGoingBackward(false);
            }
        } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            if (gameManager2.finishedLaps()) {
                playerCar2.setAccelerate(false);
                playerCar2.setGoingBackward(false);
            }
        }
    }

    private void initialColl(Car player, double[] rcDistances) {
        if (player.wallCollision(rcDistances)) {
            if (player == playerCar) {
                startXY[0] += player.getCarHeightWidth()[1] / 2;
                player.getImage().relocate(startXY[0], startXY[1]);
            } else {
                start2XY[0] -= player.getCarHeightWidth()[1] / 2;
                player.getImage().relocate(start2XY[0], start2XY[1]);
            }
        }

        if (this.carOnCarColl()) {
            startXY[0] -= player.getCarHeightWidth()[1] / 3;
            start2XY[0] += player.getCarHeightWidth()[1] / 3;
            playerCar.getImage().relocate(startXY[0], startXY[1]);
            playerCar2.getImage().relocate(start2XY[0], start2XY[1]);
        }

    }


    private int collCounter = 0;

    private boolean carOnCarColl() {
        ProjectionRectangle rect1 = new ProjectionRectangle(playerCar, playerCar.getRaycaster().getRayRect().get(0));
        ProjectionRectangle rect2 = new ProjectionRectangle(playerCar2, playerCar2.getRaycaster().getRayRect().get(0));
        if (playerCar.testCollision(rect1, rect2)) {
            collCounter += 1;

            if (collCounter % 15 == 0) {
                collCounter = 0;
                double[][] values = playerCar.momCollCalc(playerCar, playerCar2);
                if (Math.abs(values[0][0]) < 1) {
                    playerCar.setForceSpeed(0);
                } else {
                    playerCar.setForceSpeed(values[0][0]);
                }
                if (Math.abs(values[1][0]) < 1) {
                    playerCar2.setForceSpeed(0);
                } else {
                    playerCar2.setForceSpeed(values[1][0]);
                }
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if one of the players has picked up a powerup and sends it to Car.handleMapPowerups()
     *
     * @return void
     */
    private void powerupPickup() {
				/*
				 ShouldCollide is a boolean that helps solve a bug (when a car collides with a powerup and the discharge powerup is created,
				 then the powerup is set to visible(false), but the collision is still happening so a discharge powerup keeps popping on the screen)
				*/

        for (Powerup powerup : powerupsOnMap) {
            if (powerup instanceof BananaPowerup || powerup instanceof OilGhostPowerup || powerup instanceof SpeedboosterPowerup) {
                if (playerCar.collisionDetection(powerup)) {
                    playerCar.handleMapPowerups(powerup);
                } else {
                    if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
                        playerCar2.handleMapPowerups(powerup);
                    }
                }
            }
        }
    }

    /**
     * When one of the players presses the power-up activation button, the button can't be used for 2 seconds.
     *
     * @return void
     */
    private void usePowerup() {
        for (PlayerCar player : players) {
            if (player.isActivatedPowerup()) {
                if (!player.getPowerups().isEmpty()) {
                    if ((player.getPickedUpPwrtime() + 2000) < System.currentTimeMillis()) {
                        if (player.getPowerups().getFirst() instanceof SpeedboosterPowerup) {
                            speedBoost = true;
                            playerCar.getPowerups().pop();
                            playerCar.powerUpBar.removeFirstPowerup(playerCar.playerNumber);
                            playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                        } else {
                            checkPowerupNotNull(player.usePowerup());
                        }
                    }
                }
            }
        }
    }

    /**
     * Controls the movement of the car when the car bumps into one of the defensive power-ups on the track, such as banana peel
     * or oil spill.
     *
     * @return void
     */
    private void powerupDrop() {
        try {
            for (Powerup pwr : powerupsOnMap) {
                for (PlayerCar player : players) {
                    if (player.collisionDetection(pwr) && pwr.shouldCollide) {
                        if (pwr instanceof OilSpillPowerup) {
                            pwr.deactivate();
                            player.movementPowerup("carSlide");
                        } else if (pwr instanceof BananaDischargePowerup) {
                            pwr.deactivate();
                            SoundManager.play("bananaFall");
                            player.movementPowerup("carSpin");
                        }
                        powerupsOnMap.remove(pwr);
                    }
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.print(" ");
        }
    }

    private void castRays() {
        if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            distances2 = playerCar2.getRaycaster().castRays(Main.track.getTrackLines(), false);
        } else if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {

            distances2 = aiCar.getRaycaster().castRays(Main.track.getTrackLines(), false);
            aiCar.setDistances(distances2);
        }

        //this is the array of distances measured by the raycaster that we will use to train the RL algorithm
        distances = playerCar.getRaycaster().castRays(Main.track.getTrackLines(), false);
    }

    private void lapSystem() {
        //Gets ray cast for next gate
        double[] gateDistances = playerCar.getRaycaster().castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[gameManager.getNextGate()])), false);
        gameManager.setGateDistances(gateDistances);
        gameManager.hitGate();
        if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            double[] gateDistances2 = playerCar2.getRaycaster().castRays(new ArrayList<>(Collections.singletonList(Main.track.getGates()[gameManager2.getNextGate()])), false);
            gameManager2.setGateDistances(gateDistances2);
            gameManager2.hitGate();
        }
    }


    /**
     * Method used to prevent a NullPointerException.
     *
     * @return void
     */
    private void checkPowerupNotNull(Powerup powerup) {
        if (powerup != null) {
            powerupsOnMap.add(powerup);
        }
    }


}

