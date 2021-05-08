package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.Game;
import sample.models.PlayerCar;

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
            case L:
                this.getGame().getPlayerCar().setActivatePowerup(true);
                //this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            try {
	            this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON PRESSED");
	          }
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    Main.sceneManager.setPaused(true);
                    Main.sceneManager.pause();
                    this.game.getTimer().stop();
                }
                break;
        }
    }

    public void keyReleased(KeyEvent event) throws InterruptedException {
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
            case L:
                this.getGame().getPlayerCar().setActivatePowerup(false);
                //this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            try {
	            this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON RELEASED");
	          }
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    this.game.getTimer().start();
                }
                break;
        }
    }
}