package sample.models;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Game {

    private Car playerCar;

    public Car getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }

    public Game() {
        gameLoop();
    }

    public void gameLoop(){
        // initialise car, power ups and check for user clicks in a forever loop -> break condition
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                getPlayerCar().getImageView().relocate(640, 360);

                double dy = 0, rot = 0;

                double accelerationFactor = getPlayerCar().accelerationCalculator(getPlayerCar().getAccelerationFactor());

                double forwardVelocity = accelerationFactor * 1;
                double turningSpeed = accelerationFactor * 1;

                if (getPlayerCar().isGoingForward()) {
                    dy -= forwardVelocity;
                } else if (getPlayerCar().isGoingBackward()) {
                    dy += 1;
                } else if (getPlayerCar().isTurnLeft()){
                    rot -= turningSpeed;
                } else if (getPlayerCar().isTurnRight()) {
                    rot += turningSpeed;
                } else if (getPlayerCar().isRacing()){
                    dy *= 3;
                }

                getPlayerCar().moveCarBy(dy);
                getPlayerCar().turn(rot);
            }
        };
        timer.start();
    }
}
