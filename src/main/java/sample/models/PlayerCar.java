package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.audio.SoundManager;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerCar extends Car {

    private boolean powerup;
    private long pickedUpPwrtime = -1;

    public PlayerCar(Pane gameBackground, Settings.VehicleType vehicleType) {
        super(gameBackground, generateCarImageView(vehicleType), vehicleType);
        this.powerups = new LinkedList<>();
        this.powerUpBar = new PowerUpBar(gameBackground);
    }





    private final LinkedList<Powerup> powerups;
    //    public ArrayList<Powerup> powerupsDischarge;
    public PowerUpBar powerUpBar;


    public void addPowerup(Powerup powerup) {
        if (getPowerups().size() >= 3) {
            this.powerups.pop();
            this.powerUpBar.removeFirstPowerup(playerNumber);
        }
        this.getPowerups().add(powerup);
        this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup, playerNumber);
    }

    public LinkedList<Powerup> getPowerups()
    {
        return powerups;
    }
    /**
     * Should handle powerup activate here, like changing speed of the car or something
     * */
    public void activatePowerup() {
        if (!(this.getPowerups().isEmpty())) {
            Powerup powerup = getPowerups().pop();
            if (powerup instanceof BananaPowerup) {
                System.out.println("detected banana powerup");
            } else if (powerup instanceof SpeedboosterPowerup) {
                System.out.println("detected speed boosted powerup");
            } else if (powerup instanceof OilGhostPowerup) {
                System.out.println("detected oil ghost powerup");
            }
        }

    }

    public void handleMapPowerups(Powerup powerup) {
        if (powerup.shouldCollide && collisionDetection(powerup))
        {
            SoundManager.play("prop");
            addPowerup(powerup);
            powerup.deactivate();
        }
        else if (!powerup.shouldCollide) {
            if ((powerup.pickUptime + 7000) < System.currentTimeMillis()) {
                powerup.activate();
            }
        }
    }

    public Powerup usePowerup () {
        SoundManager.play("powerUp");
        double playerCarLayoutX = getImage().getLayoutX();
        double playerCarLayoutY = getImage().getLayoutY();
        double powerupWidth = powerups.getFirst().getImage().getBoundsInLocal().getWidth();
        double powerupHeight = powerups.getFirst().getImage().getBoundsInLocal().getHeight();

        double x = playerCarLayoutX - powerupWidth;
        double y = playerCarLayoutY - powerupHeight;

        if (powerups.getFirst() instanceof BananaPowerup) {
            BananaDischargePowerup drop = new BananaDischargePowerup(powerups.getFirst().getGameBackground());
            drop.render(x, y);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof OilGhostPowerup) {
            OilSpillPowerup drop = new OilSpillPowerup(powerups.getFirst().getGameBackground());
            drop.render(x, y);
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return drop;
        } else if (powerups.getFirst() instanceof SpeedboosterPowerup) {
            movementPowerup("speedBoost");
            powerups.pop();
            powerUpBar.removeFirstPowerup(playerNumber);
            setPickedUpPwrtime(System.currentTimeMillis());
            return null;
        }
        //had to return something, but it shouldn't get this far
        return powerups.getFirst();
    }

    /**
     * If function is activated, it will change the value of speedBoostOn to true
     * for a specified time period (1 second)
     */
    public void movementPowerup(String powerUp){
        switch(powerUp){
            case "carSpin":
                carSpinOn = true;
            case "speedBoost":
                speedBoostOn = true;
            case "carSlide":
                carSlideOn = true;
        }
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        switch(powerUp){
                            case "carSpin":
                                carSpinOn = false;
                            case "speedBoost":
                                speedBoostOn = false;
                            case "carSlide":
                                carSlideOn = false;
                        }
                    }
                },
                1000
        );
    }


    public boolean isActivatedPowerup() {
        return powerup;
    }

    public void setActivatePowerup(boolean powerup) {
        this.powerup = powerup;
    }

    public long getPickedUpPwrtime()
    {
        return pickedUpPwrtime;
    }

    public void setPickedUpPwrtime(long pickedUpPwrtime)
    {
        this.pickedUpPwrtime = pickedUpPwrtime;
    }

}
