package sample.models;

import javafx.animation.AnimationTimer;
import sample.Main;

public class Game {

    private Car playerCar;
    private Powerup[] powerups;

    public Car getPlayerCar() {
        return playerCar;
    }
    private static final String TRACK_IMAGE_LOC = "src/sample/resources/images/racingtrack.png";


    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }

    public void gameLoop(){
        // initialise car, power ups and check for user clicks inside the game loop
        // place powerups and do other initialising stuff here

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
