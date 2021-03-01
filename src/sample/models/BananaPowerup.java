package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.util.ArrayList;

public class BananaPowerup extends Powerup {

    public BananaPowerup(BorderPane gameBackground) {
        super(gameBackground, generateCarImageView());
    }

    protected ArrayList<BananaPowerup> generatePowerups(int total) {
        ArrayList<BananaPowerup> powerups = new ArrayList<>();
        for (int i = 0 ; i < total ; i++) {
            powerups.add(new BananaPowerup(super.getGameBackground()));
        }
        return powerups;
    }

    private static ImageView generateCarImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/powerupBanana.png");
            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading car image");
        }
        return null;
    }
}
