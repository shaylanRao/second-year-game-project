package sample.models;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class that contains the main game loop
 */

public class Game {

    private PlayerCar playerCar;
    private ArrayList<Powerup> powerups;
    private ArrayList<Powerup> powerupsDischarge;

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
        this.powerupsDischarge = new ArrayList<>();
        int maxPowerups = 10;
        for(int i = 0; i < maxPowerups; i++) {
            this.powerups.add(new BananaPowerup(gameBackground));
            this.powerups.add(new SpeedboosterPowerup(gameBackground));
            this.powerups.add(new OilGhostPowerup(gameBackground));
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
                
                //shouldCollide is a boolean that helps solve a bug (when a car collides with a powerup and the discharge powerup is created,
                //then the powerup is set to visible(false), but the collision is still happening so a discharge powerup keeps popping on the screen)
                for (Powerup powerup: powerups) {
                    if (playerCar.collisionDetection(powerup) && powerup.shouldCollide==true) {
                        playerCar.addPowerup(powerup);
                        powerup.deactivate();
                        if(powerup.getType().equals("banana")) {
                        	BananaDischargePowerup ban = new BananaDischargePowerup(powerup.getGameBackground());
                        	ban.render(powerup.getImage().getLayoutX(), powerup.getImage().getLayoutY());
                        	powerupsDischarge.add(ban);
                        }
                        else if(powerup.getType().equals("oilghost")) {
                        	OilSpillPowerup oil = new OilSpillPowerup(powerup.getGameBackground());
                        	oil.render(powerup.getImage().getLayoutX(), powerup.getImage().getLayoutY());
                        	powerupsDischarge.add(oil);
                        }
                    }
                }
                
                for(Powerup pwr: powerupsDischarge) {
                	if(playerCar.collisionDetection(pwr)) {
                		pwr.deactivate();
                	}
                }

                playerCar.moveCarBy(dy);
                playerCar.turn(rot);
            }
        };
        timer.start();
    }
}
