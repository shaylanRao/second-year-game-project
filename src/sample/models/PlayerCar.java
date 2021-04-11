package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerCar extends Car {

    private final LinkedList<Powerup> powerups;
    public ArrayList<Powerup> powerupsDischarge;
    public PowerUpBar powerUpBar;

    public PlayerCar(Pane gameBackground) {
        super(gameBackground, generateCarImageView());
        this.powerups = new LinkedList<>();
        this.powerUpBar = new PowerUpBar(gameBackground);
        this.powerupsDischarge = new ArrayList<>();
    }

    public void addPowerup(Powerup powerup) {
        if (getPowerups().size() < 3) {
            this.getPowerups().add(powerup);
			this.powerupsDischarge.add(powerup);
            this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup);
        }
        else {
        	this.powerupsDischarge.remove(this.powerups.pop());
            this.powerUpBar.addPowerUpToBar(getPowerups().size(), powerup);
        	this.powerUpBar.removeFirstPowerup();
            
            this.getPowerups().add(powerup);
			this.powerupsDischarge.add(powerup);
        }
    }
    /**
     * Should handle powerup activate here, like changing speed of the car or something
     * */
    public boolean activatePowerup() {
        if (!(this.getPowerups().isEmpty())) {
            Powerup powerup = getPowerups().pop();
            if (powerup instanceof BananaPowerup) {
                System.out.println("detected banana powerup");
            } else if (powerup instanceof SpeedboosterPowerup) {
                System.out.println("detected speed boosted powerup");
            } else if (powerup instanceof OilGhostPowerup) {
                System.out.println("detected oil ghost powerup");
            }
            return true;
        }
        return false;
    }

    private static ImageView generateCarImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/original_car.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }

	public LinkedList<Powerup> getPowerups()
	{
		return powerups;
	}
}
