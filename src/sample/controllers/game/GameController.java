/* Handle game input*/

package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.models.Game;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private ImageView carImage;

    @FXML
    private ImageView powerupOne;

    @FXML
    private ImageView powerupTwo;

    @FXML
    private ImageView powerupThree;

    @FXML
    private ImageView powerupFour;

    @FXML
    private ImageView powerupFive;

    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void keyClicked(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                this.getGame().getPlayerCar().setGoingForward(true);
                this.getGame().getPlayerCar().setAccelerate(true);
                break;
            case DOWN:
                this.getGame().getPlayerCar().setGoingBackward(true);
                break;
            case LEFT:
                this.getGame().getPlayerCar().setTurnLeft(true);
                break;
            case RIGHT:
                this.getGame().getPlayerCar().setTurnRight(true);
                break;
        }
    }

    public void keyReleased(KeyEvent event) {
        // need to add 2nd player listeners
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                this.getGame().getPlayerCar().setAccelerate(false);
                if (this.getGame().getPlayerCar().getAccelerationFactor() < 0.2) {
                    this.getGame().getPlayerCar().setGoingForward(false);
                }
                break;
            case DOWN:
                this.getGame().getPlayerCar().setGoingBackward(false);
                break;
            case LEFT:
                this.getGame().getPlayerCar().setTurnLeft(false);
                break;
            case RIGHT:
                this.getGame().getPlayerCar().setTurnRight(false);
                break;
        }
    }


    /* Actually initializes gameplay*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.setGame(new Game());

            // temp
            // TODO - find a better way to do this

            ArrayList<ImageView> temp = new ArrayList<>();

            temp.add(powerupOne);
            temp.add(powerupTwo);
            temp.add(powerupThree);
            temp.add(powerupFour);
            temp.add(powerupFive);

            // use this method to pass things to the model
            this.game.initialiseGameObjects(carImage, temp);


            // starts the game
            this.getGame().gameLoop();

        } catch (Exception ex) {
            System.out.println("Error when initializing ");
        }
    }
}
