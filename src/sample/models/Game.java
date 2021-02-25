package sample.models;

import javafx.animation.AnimationTimer;
import sample.Main;

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
        playerCar.render(Main.maxWidth / 2, Main.maxHeight / 2);
        //TODO place powerup initialiser

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
