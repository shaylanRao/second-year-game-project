package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;
import sample.models.Car;

public class GameController {

    @FXML
    private ImageView carImage;

    public void keyClicked(KeyEvent event) {
        Main.game.setPlayerCar(new Car(carImage));
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                Main.game.getPlayerCar().setGoingForward(true);
                Main.game.getPlayerCar().setAccelerate(true);
                break;
            case DOWN:
                Main.game.getPlayerCar().setGoingBackward(true);
                break;
            case LEFT:
                Main.game.getPlayerCar().setTurnLeft(true);
                break;
            case RIGHT:
                Main.game.getPlayerCar().setTurnRight(true);
                break;
            case SHIFT:
                Main.game.getPlayerCar().setRacing(true);
        }
    }

    public void keyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                Main.game.getPlayerCar().setAccelerate(false);
                if (Main.game.getPlayerCar().getAccelerationFactor() <= 0.2) {
                    Main.game.getPlayerCar().setGoingForward(false);
                }
                break;
            case DOWN:
                Main.game.getPlayerCar().setGoingBackward(false);
                break;
            case LEFT:
                Main.game.getPlayerCar().setTurnLeft(false);
                break;
            case RIGHT:
                Main.game.getPlayerCar().setTurnRight(false);
                break;
            case SHIFT:
                Main.game.getPlayerCar().setRacing(false);
        }
    }

}
