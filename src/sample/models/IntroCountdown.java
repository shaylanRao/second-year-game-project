package sample.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class IntroCountdown extends Sprite{


    public IntroCountdown(Pane gameBackground, int frame) {
        super(gameBackground, generateImage(frame));
    }

    public void activate() {
        this.getImage().setVisible(true);
    }
    public void deactivate() {
        this.getImage().setVisible(false);
    }


    private static ImageView generateImage(int frame) {
        try {

            String filePath = "";
            if(frame == 0)
                filePath = "src/sample/resources/images/Picture3.png";
            else if(frame == 1)
                filePath = "src/sample/resources/images/Picture2.png";
            else if(frame == 2)
                filePath = "src/sample/resources/images/Picture1.png";
            else if(frame == 3)
                filePath = "src/sample/resources/images/Picture4.png";

            FileInputStream file = new FileInputStream(filePath);
            Image img = new Image(file);
            return new ImageView(img);
        } catch (Exception ex) {
            System.out.println("Error when loading intro image : "+ frame);
        }
        return null;
    }
}
