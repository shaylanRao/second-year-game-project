package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class that contains the main game loop
 */
public class Game {

    private ArrayList<Powerup> powerups;

    private Car playerCar;

    public Car getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }


    /**
     * Sets initial game state
     */
    private void initialiser(){
        playerCar.render(455, 287);

        Random random = new Random();

        for (Powerup powerup: powerups) {
            int x = random.nextInt(1280);
            int y = random.nextInt(720);
            powerup.render(x, y);
        }
    }

    public void initialiseGameObjects(ImageView playerOneImage, ArrayList<ImageView> powerupImages) {
        // this method should take in all the necessary info from the GameController and initialise the playerCars
        this.setPlayerCar(new Car(playerOneImage));
        this.powerups = new ArrayList<>();
        for (ImageView image:powerupImages) {
            this.powerups.add(new Powerup(image));
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

                for (Powerup powerup:powerups) {
                    if (playerCar.collisionDetection(powerup)) {
                        powerup.deactivate();
                    }
                }

                // implement collision detection over here

                playerCar.moveCarBy(dy);
                playerCar.turn(rot);
            }
        };
        timer.start();
    }
}
