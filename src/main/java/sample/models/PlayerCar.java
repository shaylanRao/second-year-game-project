package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.audio.SoundManager;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayerCar extends Car {

    public int getCarNumber(){
        return this.playerNumber;
    }

    /**
     * {@inheritDoc}
     */
    public PlayerCar(Pane gameBackground, Settings.VehicleType vehicleType) {
        super(gameBackground, generateCarImageView(vehicleType), vehicleType);
        this.powerups = new LinkedList<>();
        this.powerUpBar = new PowerUpBar(gameBackground);
    }




    private final LinkedList<Powerup> powerups;
    public PowerUpBar powerUpBar;

    /**
     * Checks if a player has less than 3 power-ups. If there are less than 3, then the new power-up is added to the linked list and
     * the power-up bar. Otherwise, it removes the least recent power-up and adds it at the end.
     * @param powerup the powerup used to add
     */
    public void addPowerup(Powerup powerup) {
        if (getPowerups().size() >= 3) {
            this.powerups.pop();
            this.powerUpBar.removeFirstPowerup(playerNumber);
        }
        this.getPowerups().add(powerup);
        this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup, playerNumber);
    }

    /**
     * Getter method for the power-ups stored in each player's list.
     * @return LinkedList<Powerup>
     */
    public LinkedList<Powerup> getPowerups()
    {
        return powerups;
    }

    /**
     * Checks if a player has passed through a power-up on the track, passes the power-up to addPowerup(), and deactivates the power-up.
     * After 7 seconds, the power-up is set to re-spawn on the screen as long as there is no player in the same spot.
     * @param powerup the powerup used to be checked
     */
    public void handleMapPowerups(Powerup powerup) {
        if (powerup.shouldCollide && collisionDetection(powerup))
        {
            SoundManager.play("prop");
            addPowerup(powerup);
            powerup.deactivate();
        }
        else if (!powerup.shouldCollide) {
            if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
                if(!collisionDetection(powerup)) {
                    powerup.activate();
                }
            }
        }
    }

    /**
     * This method handles power-ups when a player chooses to use them. Thus, it checks the power-up that it is being used and renders
     * the new object if it is the case. (banana peel when using a banana, etc)
     * @return Powerup
     */
    public Powerup usePowerup () {
        SoundManager.play("powerUp");
        if (powerups.getFirst() instanceof BananaPowerup) {
            BananaDischargePowerup drop = new BananaDischargePowerup(powerups.getFirst().getGameBackground());
            drop.render(getPowerupLoc()[0], getPowerupLoc()[1]);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof OilGhostPowerup) {
            OilSpillPowerup drop = new OilSpillPowerup(powerups.getFirst().getGameBackground());
            drop.render(getPowerupLoc()[0], getPowerupLoc()[1]);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        }
        return powerups.getFirst();
    }

    /**
     * Renders the activated power-ups behind the car.
     * @return double[]
     */
    private double[] getPowerupLoc(){
        double[] location = new double[2];
        double hyp = carHeight*2;
        car1Angle = this.getImageView().getRotate();
        if ((car1Angle > -90 && car1Angle < 90) || car1Angle < -270 || car1Angle > 270) {
            location[0] = Math.cos( Math.toRadians(car1Angle)) * hyp + this.getImage().getLayoutX() + carWidth;
        } else {
            location[0] = Math.cos( Math.toRadians(car1Angle)) * hyp + this.getImage().getLayoutX();
        }
        location[1] = (Math.sin( Math.toRadians(car1Angle)) * hyp) + this.getImage().getLayoutY();

        return location;
    }



}
