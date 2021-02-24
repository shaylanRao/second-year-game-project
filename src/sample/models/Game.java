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
        getPlayerCar().render(Main.maxWidth / 2, Main.maxHeight / 2);
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

                double dy = 0, rot = 0;

                double forwardVelocity = getPlayerCar().getForwardVelocity();
                double turningSpeed = getPlayerCar().getTurningSpeed();

                if (getPlayerCar().isGoingForward()) {
                    dy -= forwardVelocity;
                }
                if (getPlayerCar().isGoingBackward()) {
                    dy += 1;
                }
                if (getPlayerCar().isTurnLeft()){
                    rot -= turningSpeed;
                }
                if (getPlayerCar().isTurnRight()) {
                    rot += turningSpeed;
                }

                // implement collision detection over here

                getPlayerCar().moveCarBy(dy);
                getPlayerCar().turn(rot);
            }
        };
        timer.start();
    }
}
