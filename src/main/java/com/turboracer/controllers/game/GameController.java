/* Handle game input*/

package com.turboracer.controllers.game;

import com.turboracer.models.Game;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends AbstractGameController {
    @FXML
    private Pane gameBackground;
    /* Actually initializes gameplay*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {



        try {
            setGame(new Game());
            game.initialiseGameObjects(gameBackground);
            //this.game.initialisePowerups(gameBackground);
            // starts the game
            getGame().gameLoop();

        } catch (Exception ex) {
            System.out.println("Error when initializing ");
        }
    }

}
