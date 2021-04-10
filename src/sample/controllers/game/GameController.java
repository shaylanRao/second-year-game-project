/* Handle game input*/

package sample.controllers.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.controllers.audio.SoundManager;
import sample.models.Game;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends AbstractGameController {
    @FXML
    private Pane gameBackground;
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
