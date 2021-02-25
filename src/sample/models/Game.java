package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

/**
 * The class that contains the main game loop
 */
public class Game {

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
        //TODO place powerup initialiser
    }

    public void initialise(ImageView playerOneImage) {
        // TODO
        // this method should take in all the necessary info from the GameController and initialise the playerCars
        this.setPlayerCar(new Car(playerOneImage));
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

                // implement collision detection over here

                playerCar.moveCarBy(dy);
                playerCar.turn(rot);
            }
        };
        timer.start();
    }
}
