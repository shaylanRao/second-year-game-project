package sample.controllers.game;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MultiplayerController extends RandomTrackScreen {
    @Override
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
                this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
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
                this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                try {
                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                }
                catch(Exception e) {
                    System.out.println("KEY PRESSED ERROR");
//                    e.notify();
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
                this.getGame().getPlayerCar2().setPickedUpPwrtime(System.currentTimeMillis());
//                try {
//                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
//                }
//                catch(Exception e) {
//                    System.out.println("KEY RELEASED ERROR");
//                    e.getCause();
//                }
                break;
        }
    }
}
