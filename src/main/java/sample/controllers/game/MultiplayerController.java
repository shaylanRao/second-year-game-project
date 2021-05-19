package sample.controllers.game;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;

public class MultiplayerController extends RandomTrackScreen {

    @Override
    public void keyClicked(KeyEvent event) {
        System.out.println("inside key clicked");
        KeyCode code = event.getCode();
        switch (code) {
            case UP:
                System.out.println("up pressed");
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
            case W:
                this.getGame().getPlayerCar2().setGoingForward(true);
                this.getGame().getPlayerCar2().setAccelerate(true);
                break;
            case S:
                this.getGame().getPlayerCar2().setGoingBackward(true);
                break;
            case A:
                this.getGame().getPlayerCar2().setTurnLeft(true);
                break;
            case D:
                this.getGame().getPlayerCar2().setTurnRight(true);
                break;
            case F:
                this.getGame().getPlayerCar2().setActivatePowerup(true);
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

    @Override
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
            case L:
                this.getGame().getPlayerCar().setActivatePowerup(false);
	            try {
	            this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON RELEASED");
	          }
                break;
            case W:
                this.getGame().getPlayerCar2().setAccelerate(false);
                break;
            case S:
                this.getGame().getPlayerCar2().setGoingBackward(false);
                break;
            case A:
                this.getGame().getPlayerCar2().setTurnLeft(false);
                break;
            case D:
                this.getGame().getPlayerCar2().setTurnRight(false);
                break;
            case F:
                this.getGame().getPlayerCar2().setActivatePowerup(false);
                //this.getGame().getPlayerCar2().setPickedUpPwrtime(System.currentTimeMillis());
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
