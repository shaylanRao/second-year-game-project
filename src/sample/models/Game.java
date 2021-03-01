package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class that contains the main game loop
 */
public class Game {

    private PlayerCar playerCar;
    private ArrayList<Powerup> powerups;

    public Car getPlayerCar() {
        return playerCar;
    }



    /**
     * Sets initial game state
     */
    private void initialiser(){
        Random random = new Random(42);

        playerCar.render(300,450);
        for (Powerup bananaPowerup: powerups) {
            int x = random.nextInt(1280);
            int y = random.nextInt(720);
            bananaPowerup.render(x, y);
        }
    }

    public void initialiseGameObjects(BorderPane gameBackground) {
        // this method should take in all the necessary info from the GameController and initialise the playerCars
        this.playerCar = new PlayerCar(gameBackground);
        this.powerups = new ArrayList<>();
        int maxPowerups = 10;
        for(int i = 0; i < maxPowerups; i++) {
            this.powerups.add(new BananaPowerup(gameBackground));
            this.powerups.add(new SpeedboosterPowerup(gameBackground));
        }
    }

    /**
     * The game loop. This deals with game logic such as handling collisions and moving the car
     *
     * */
    public void gameLoop(){

        this.initialiser();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //TODO powerup respawn


                double dy = 0, rot = 0;

                double forwardVelocity = playerCar.getForwardVelocity();
                double turningSpeed = playerCar.getTurningSpeed();

                if (playerCar.isGoingForward()) {
                    dy -= forwardVelocity;
                }
                //for car roll, needs to keep car moving forward
                else{
                    dy -= forwardVelocity;
                }
                if (playerCar.isGoingBackward()) {
                    dy += 1;
                }
                if (playerCar.isTurnLeft()){
                    rot -= turningSpeed;
                }
                if (playerCar.isTurnRight()) {
                    rot += turningSpeed;
                }

                for (Powerup powerup: powerups) {
                    if (playerCar.collisionDetection(powerup)) {
                        playerCar.addPowerup(powerup);
                        powerup.deactivate();
                    }
                }

                playerCar.moveCarBy(dy);
                playerCar.turn(rot);
            }
        };
        timer.start();
    }
}
