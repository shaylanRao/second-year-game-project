package com.turboracer.controllers.game;

import com.turboracer.models.Game;
import com.turboracer.models.PlayerCar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import com.turboracer.Main;

public abstract class AbstractGameController implements Initializable {
    @FXML
    protected Pane pane;
    protected Game game;
    protected PlayerCar playerCar;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        Main.sceneManager.setGame(game);
        this.game = game;
    }

    public void keyClicked(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                System.out.println("up pressed");
                getGame().getPlayerCar().setGoingForward(true);
                getGame().getPlayerCar().setAccelerate(true);
                break;
            case DOWN:
                getGame().getPlayerCar().setGoingBackward(true);
                break;
            case LEFT:
                getGame().getPlayerCar().setTurnLeft(true);
                break;
            case RIGHT:
                getGame().getPlayerCar().setTurnRight(true);
                break;
            case L:
                getGame().getPlayerCar().setActivatePowerup(true);
                //this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            try {
                    playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON PRESSED");
	          }
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    Main.sceneManager.setPaused(true);
                    Main.sceneManager.pause();
                    game.getTimer().stop();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent event) throws InterruptedException {
        // need to add 2nd player listeners
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                getGame().getPlayerCar().setAccelerate(false);
                break;
            case DOWN:
                getGame().getPlayerCar().setGoingBackward(false);
                break;
            case LEFT:
                getGame().getPlayerCar().setTurnLeft(false);
                break;
            case RIGHT:
                getGame().getPlayerCar().setTurnRight(false);
                break;
            case L:
                getGame().getPlayerCar().setActivatePowerup(false);
                //this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            try {
                    playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON RELEASED");
	          }
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    game.getTimer().start();
                }
                break;
        }
    }
}
