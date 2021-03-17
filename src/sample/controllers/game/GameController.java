/* Handle game input*/

package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import sample.models.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private BorderPane gameBackground;

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
            case F:
                this.getGame().getPlayerCar().setActivatePowerup(true);
                break;
        }
    }

    public void keyReleased(KeyEvent event) {
        // need to add 2nd player listeners
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                this.getGame().getPlayerCar().setAccelerate(false);
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
            case F:
                this.getGame().getPlayerCar().setActivatePowerup(false);
                break;
        }
    }


    /* Actually initializes gameplay*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.setGame(new Game());
            this.game.initialiseGameObjects(gameBackground);
            //this.game.initialisePowerups(gameBackground);
            // starts the game
            this.getGame().gameLoop();

        } catch (Exception ex) {
            System.out.println("Error when initializing ");
        }
    }
}
