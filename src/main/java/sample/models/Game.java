package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.ai.TrainCar;
import sample.models.audio.SoundManager;
import sample.controllers.game.GameController;

import java.util.*;

/**
 * The class that contains the main game loop
 */

public class Game {

    private PlayerCar playerCar;
    private PlayerCar playerCar2;
    private Car aiCar;
    private ArrayList<Powerup> powerups;
    private ArrayList<Powerup> powerupsDischarge;
    private boolean ok;
    private boolean speedb = false;
    private GameManager gameManager;

    public double[] getDistances() {
        return distances;
    }

    private double[] distances;

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
        gameManager = new GameManager();
        switch (Main.settings.getPlayMode()) {
            case AI_TRAIN:
                System.out.println("ai train mode");
                aiCar = new Car(pane);
                aiCar.render(1400, 700);
                aiCar.getImageView().setRotate(90);
                distances = new double[8];
                break;
            case MULTIPLAYER:
                System.out.println("multiplayer mode");
                this.playerCar = new PlayerCar(pane);
                playerCar.render(1400, 700);
                playerCar.getImageView().setRotate(90);
                this.playerCar2 = new PlayerCar(pane);
                playerCar2.render(350, 500);
                initialisePowerups(pane);
                break;
            case TIMETRIAL:
                System.out.println("time trial mode");
                this.playerCar = new PlayerCar(pane);
                playerCar.render(1400, 700);
                playerCar.getImageView().setRotate(90);
                initialisePowerups(pane);
                break;
            case AI:
                System.out.println("ai mode");
                this.playerCar = new PlayerCar(pane);
                playerCar.render(1400, 700);
                playerCar.getImageView().setRotate(90);

                aiCar = new Car(pane);
                aiCar.render(350, 500);
                distances = new double[8];
                //TrainCar.test();
                break;
        }

    }

    private void initialisePowerups(Pane pane) {
        this.powerupsDischarge = new ArrayList<>();
        this.playerCar.powerupsDischarge = new ArrayList<>();

        // generates powerups
        int maxPowerups = 2;
        this.powerups = new ArrayList<>();
        for (int i = 0; i < maxPowerups; i++) {
            this.powerups.add(new BananaPowerup(pane));
            this.powerups.add(new SpeedboosterPowerup(pane));
            this.powerups.add(new OilGhostPowerup(pane));
        }

        Random random = new Random();
        ArrayList<Point> spawnPoints = Main.track.getPowerupSpawns();
        Point spawnPoint;
        double x, y;

        for (Powerup powerup : powerups) {
            spawnPoint = spawnPoints.get(random.nextInt(spawnPoints.size()));
            spawnPoints.remove(spawnPoint);
            x = spawnPoint.getXConverted();
            y = spawnPoint.getYConverted();
            //TODO find a way to get image dimensions programmatically
            x -= 25;
            y -= 25;
            powerup.render(x, y);
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
        //carMovement();

        //powerupPickup();

        //usePowerup();

        //powerupDrop();

        makeTrack();

        lapSystem();
    }

    public synchronized void gameLoop() throws InterruptedException {
        //this.initialiser();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (j == 0) {
                    gameManager.lapTimer();
                    j++;
                }
                carMovement();

                powerupPickup();

                usePowerup();

                powerupDrop();

                makeTrack();

                lapSystem();
            }
        };

        timer.start();
    }

    private void carMovement() {
        dy = 0;
        rot = 0;
        double forwardVelocity;
        if (speedb) {
            forwardVelocity = playerCar.getForwardSpeed() * 2;
            counter++;
            if (counter > 100) {
                speedb = false;
                forwardVelocity = playerCar.getForwardSpeed() / 2;
                counter = 0;
            }
        } else {
            forwardVelocity = playerCar.getForwardSpeed();
        }
        double turningSpeed = playerCar.getTurningSpeed();
        this.dy -= forwardVelocity;
        if (playerCar.isGoingBackward()) {
            dy += 1;
        }
        if (playerCar.isTurnLeft()) {
            rot -= turningSpeed;
        }
        if (playerCar.isTurnRight()) {
            rot += turningSpeed;
        }

        playerCar.moveCarBy(dy);
        if (rot > 2.3) {
            rot = 2.3;
        } else if (rot < -2.3) {
            rot = -2.3;
        }

        playerCar.turn(rot);

        if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
            dy2 = 0;
            rot2 = 0;
            double forwardVelocity2 = playerCar2.getForwardSpeed();
            double turningSpeed2 = playerCar2.getTurningSpeed();
            if (playerCar2.isGoingForward()) {
                dy2 -= forwardVelocity2;
            }
            if (playerCar2.isGoingBackward()) {
                dy2 += 1;
            }
            if (playerCar2.isTurnLeft()) {
                rot2 -= turningSpeed2;
            }
            if (playerCar2.isTurnRight()) {
                rot2 += turningSpeed2;
            }

            playerCar2.moveCarBy(dy2);
            if (rot2 > 2.3) {
                rot2 = 2.3;
            } else if (rot2 < -2.3) {
                rot2 = -2.3;
            }

            playerCar2.turn(rot2);
        }


    }

    private void powerupPickup() {
				/*
				 ShouldCollide is a boolean that helps solve a bug (when a car collides with a powerup and the discharge powerup is created,
				 then the powerup is set to visible(false), but the collision is still happening so a discharge powerup keeps popping on the screen)
				*/

        for (Powerup powerup : powerups) {
            if (powerup.shouldCollide && playerCar.collisionDetection(powerup)) {
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
            } else if (!powerup.shouldCollide) {
                if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
                    powerup.activate();
                }
            }
        }
    }

    private void usePowerup() {
        if (playerCar.isActivatedPowerup()) {
            if ((playerCar.getPickedUpPwrtime() + 2000) < System.currentTimeMillis()) {
                for (Powerup powerup : playerCar.powerupsDischarge) {
                    System.out.println("");
                    SoundManager.play("powerUp");
                    double playerCarLayoutX = playerCar.getImage().getLayoutX();
                    double playerCarLayoutY = playerCar.getImage().getLayoutY();
                    double powerupWidth = powerup.getImage().getBoundsInLocal().getWidth();
                    double powerupHeight = powerup.getImage().getBoundsInLocal().getHeight();

                    double x = playerCarLayoutX - powerupWidth;
                    double y = playerCarLayoutY - powerupHeight;

                    if (powerup instanceof BananaPowerup) {
                        BananaDischargePowerup ban = new BananaDischargePowerup(powerup.getGameBackground());
                        ban.render(x, y);
                        playerCar.powerupsDischarge.remove(powerup);
                        playerCar.powerupsDischarge.add(ban);
                        playerCar.powerUpBar.removeFirstPowerup();
                    } else if (powerup instanceof OilGhostPowerup) {
                        OilSpillPowerup oil = new OilSpillPowerup(powerup.getGameBackground());
                        oil.render(x, y);
                        playerCar.powerupsDischarge.remove(powerup);
                        playerCar.powerupsDischarge.add(oil);
                        playerCar.powerUpBar.removeFirstPowerup();
                    } else if (powerup instanceof SpeedboosterPowerup) {
                        playerCar.powerupsDischarge.remove(powerup);
                        SoundManager.stop("powerUp");
                        SoundManager.play("SpeedBoost");
                        //playerCar.activatePowerup("speedBoost");
                        speedb = true;
                        playerCar.powerUpBar.removeFirstPowerup();
                    }
                    playerCar.setPickedUpPwrtime(System.currentTimeMillis());

                    try {
                        playerCar.getPowerups().pop();
                    } catch (Exception e) {
                        System.out.println("usePowerup ERROR");
                        System.out.println(playerCar.getPowerups().peek());
                    }
                }
            }
        }
    }

    private void powerupDrop() {

        for (Powerup pwr : playerCar.powerupsDischarge) {
            if (playerCar.collisionDetection(pwr) && pwr.shouldCollide) {
                pwr.deactivate();
                if (pwr instanceof OilSpillPowerup) {
                    SoundManager.play("OilFall");
                    playerCar.activatePowerup("carSlide");

                } else if (pwr instanceof BananaDischargePowerup) {
                    SoundManager.play("bananaFall");
                    playerCar.activatePowerup("carSpin");
                }
                if (playerCar.activatePowerup()) {
                    playerCar.powerupsDischarge.remove(pwr);
                }
            }
        }
    }

    private void makeTrack() {
        if (Main.settings.getTrack().equals(Settings.Track.TRACK3)) {
            //set raycaster position and rotation = the car's position and rotation
            if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
                GameController.raycaster.setPos(new Point(Point.unconvertX(aiCar.getImageView().getLayoutX() + 35),
                        Point.unconvertY(aiCar.getImageView().getLayoutY() + 18)));

                GameController.raycaster.setRot(aiCar.getImageView().getRotate());
            } else {
                GameController.raycaster.setPos(new Point(Point.unconvertX(playerCar.getImageView().getLayoutX() + 35),
                        Point.unconvertY(playerCar.getImageView().getLayoutY() + 18)));

                GameController.raycaster.setRot(playerCar.getImageView().getRotate());
            }

            //this is the array of distances measured by the raycaster that we will use to train the RL algorithm
            distances = GameController.raycaster.castRays(Main.track.getTrackLines(), false);
        }
    }

    private void lapSystem() {
        double gateDistances[] = GameController.raycaster.castRays(new ArrayList<>(Arrays.asList(Main.track.getGates()[gameManager.getNextGate()])), true);
//					System.out.println(Arrays.toString(gateDistances));
        gameManager.setGateDistances(gateDistances);
        //System.out.println(Arrays.toString(distances));
        gameManager.hitGate();
    }
}