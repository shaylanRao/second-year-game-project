package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class FinishLine extends Powerup {

    public FinishLine(Pane gameBackground) {
        super(gameBackground, generateFinishImageView());
    }

    private static ImageView generateFinishImageView() {
        try {
            FileInputStream carImageFile = new FileInputStream("src/sample/resources/images/finishLine.png");

            Image carImage = new Image(carImageFile);
            return new ImageView(carImage);
        } catch (Exception ex) {
            System.out.println("Error when loading finish line");
        }
        return null;
    }
}
