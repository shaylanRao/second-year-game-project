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
            case W:
                getGame().getPlayerCar2().setGoingForward(true);
                getGame().getPlayerCar2().setAccelerate(true);
                break;
            case S:
                getGame().getPlayerCar2().setGoingBackward(true);
                break;
            case A:
                getGame().getPlayerCar2().setTurnLeft(true);
                break;
            case D:
                getGame().getPlayerCar2().setTurnRight(true);
                break;
            case F:
                getGame().getPlayerCar2().setActivatePowerup(true);
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

    @Override
    public void keyReleased(KeyEvent event) {
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
	            try {
                    playerCar.setPickedUpPwrtime(System.currentTimeMillis());
	            }
	            catch(NullPointerException e) {
	              System.out.println("POWER-UP BUTTON RELEASED");
	          }
                break;
            case W:
                getGame().getPlayerCar2().setAccelerate(false);
                break;
            case S:
                getGame().getPlayerCar2().setGoingBackward(false);
                break;
            case A:
                getGame().getPlayerCar2().setTurnLeft(false);
                break;
            case D:
                getGame().getPlayerCar2().setTurnRight(false);
                break;
            case F:
                getGame().getPlayerCar2().setActivatePowerup(false);
                //this.getGame().getPlayerCar2().setPickedUpPwrtime(System.currentTimeMillis());
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
