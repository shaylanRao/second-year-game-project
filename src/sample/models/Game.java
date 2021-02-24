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
    private void initialiser(){
        playerCar.render(Main.maxWidth / 2, Main.maxHeight / 2);
        //TODO place powerup initialiser

    }


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
