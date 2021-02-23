package sample.models;

import javafx.animation.AnimationTimer;

public class Game {

    private Car playerCar;
    private Powerup[] powerups;

    public Car getPlayerCar() {
        return playerCar;
    }

    public void setPlayerCar(Car playerCar) {
        this.playerCar = playerCar;
    }


    public void gameLoop(){
        // initialise car, power ups and check for user clicks in a forever loop -> break condition

        // just to check if the car was a
        final boolean[] initialised = {false};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // the car was getting relocated every time the loop ran so it looked like it was stuck
                if (!initialised[0]) {
                    getPlayerCar().render(640, 360);
                    initialised[0] = true;
                }

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

                // implement collision detection over here

                getPlayerCar().moveCarBy(dy);
                getPlayerCar().turn(rot);
            }
        };
        timer.start();
    }
}
