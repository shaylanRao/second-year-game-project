package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class PowerUpBar extends Sprite
{
	
	public PowerUpBar(Pane gameBackground, String powerUpName)
	{
		 super(gameBackground, generateImageView(powerUpName));
	}
	
	private static ImageView generateImageView(String powerUpName) {
        try {
            FileInputStream ImageFile = new FileInputStream("src/sample/resources/images/" + powerUpName + ".png");
            Image Image = new Image(ImageFile);
            return new ImageView(Image);
        } catch (Exception ex) {
            System.out.println("Error when loading powerup bar image");
        }
        return null;
    }
	
}
