package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.Game;
import sample.models.PlayerCar;
import sample.models.Settings;

/**
 * Abstract class containing key input handling logic
 */
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

    /** Handles game key click.
     * @param event The key event
     */
    public void keyClicked(KeyEvent event) {
        if (!Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
            KeyCode code = event.getCode();
            if (Main.settings.getPlayMode() == Settings.PlayMode.MULTIPLAYER) {
                p1KeyClicked(code);
                p2KeyClicked(code);
            } else {
                p1KeyClicked(code);
            }
        } else {
            System.out.println("currently in train mode");
        }
    }

    /** Handles player one key click.
     * @param code The key code
     */
    private void p1KeyClicked(KeyCode code) {
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
                try {
                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                } catch (Exception e) {
                    System.out.println("Controller ERROR");
                }
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    Main.sceneManager.setPaused(true);
                    Main.sceneManager.pause();
                    this.game.getTimer().stop();
                }
//                try {
//                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
//                }
//                catch(NullPointerException e) {
//                    System.out.println("KEY PRESSED ERROR");
//                    e.getCause();
//                    e.getLocalizedMessage();
//                    e.printStackTrace();
//                }
                break;
        }
    }

    /** Handles player two key click.
     * @param code The key code
     */
    private void p2KeyClicked(KeyCode code) {
        switch (code) {
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
                break;
        }
    }

    /** Handles player one key release.
     * @param code The key code
     */
    private void p1KeyReleased(KeyCode code) {
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
                this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                break;
            case P:
                if (!Main.sceneManager.isPaused()) {
                    this.game.getTimer().start();
                }
//                try {
//                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
//                }
//                catch(Exception e) {
//                	System.out.println("KEY RELEASED ERROR");
//                }
                break;
        }
    }

    /** Handles player two key click.
     * @param code The key code
     */
    private void p2KeyReleased(KeyCode code) {
        switch (code) {
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
                this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                break;
        }
    }

    /** Handles game key release.
     * @param event The key event
     */
    public void keyReleased(KeyEvent event) {
        // need to add 2nd player listeners
        KeyCode code = event.getCode();
        if (Main.settings.getPlayMode() == Settings.PlayMode.MULTIPLAYER) {
            p1KeyReleased(code);
            p2KeyReleased(code);
        } else {
            p1KeyReleased(code);
        }
    }
}
