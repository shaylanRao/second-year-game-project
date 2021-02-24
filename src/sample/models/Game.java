package sample.models;

import javafx.animation.AnimationTimer;
import sample.Main;

public class Game {

    private Car playerCar;

    public Car getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }

    /**
     * The game loop
     *
     * */
    public void gameLoop(){

        getPlayerCar().render(Main.maxWidth / 2, Main.maxHeight / 2);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                double dy = 0, rot = 0;

                double accelerationFactor = getPlayerCar().accelerationCalculator(getPlayerCar().getAccelerationFactor());

                double forwardVelocity = accelerationFactor * 1;
                double turningSpeed = accelerationFactor * 1.5;

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
