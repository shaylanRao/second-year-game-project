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
        this.powerups.add(powerup);
    }

    public Powerup activatePowerup() {
        if (!(this.powerups.isEmpty())) {
            return powerups.pop();
        }
        return null;
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
