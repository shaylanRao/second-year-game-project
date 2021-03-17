/* Handle game input*/

package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.Main;
import sample.models.Game;
import sample.models.Raycaster;
import sample.models.Settings;
import sample.models.TrackBuilder;

import java.net.URL;
import java.util.ArrayList;
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
