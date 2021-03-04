package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.util.Stack;

public class PlayerCar extends Car {

    private final Stack<Powerup> powerups;

    public PlayerCar(BorderPane gameBackground) {
        super(gameBackground, generateCarImageView());
        this.powerups = new Stack<>();
    }

    public void addPowerup(Powerup powerup) {
        // TODO set max no of powerups - 3
        if (powerups.size() < 3) {
            this.powerups.add(powerup);
        }
    }
    /**
     * Should handle powerup activate here, like changing speed of the car or something
     * */
    public void activatePowerup() {
        if (!(this.powerups.isEmpty())) {
            Powerup powerup = powerups.pop();
            if (powerup instanceof BananaPowerup) {
                System.out.println("detected banana powerup");
            } else if (powerup instanceof SpeedboosterPowerup) {
                System.out.println("detected speed boosted powerup");
            } else if (powerup instanceof OilGhostPowerup) {
                System.out.println("detected oil ghost powerup");
            }
        }
    }

    private static ImageView generateCarImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/car.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
