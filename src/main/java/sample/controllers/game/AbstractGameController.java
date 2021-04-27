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

public abstract class AbstractGameController implements Initializable {
    @FXML
    protected Pane pane;
    protected Game game;
    protected PlayerCar playerCar;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void keyClicked(KeyEvent event) {
        KeyCode code = event.getCode();
        if (Main.settings.getPlayMode() == Settings.PlayMode.MULTIPLAYER) {
            p1KeyClicked(code);
            p2KeyClicked(code);
        } else {
            p1KeyClicked(code);
        }
    }

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
                try {
                    this.playerCar.setPickedUpPwrtime(System.currentTimeMillis());
                } catch (Exception e) {
                    System.out.println("Controller ERROR");
                }
                break;
        }
    }

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
                break;
        }
    }

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
                break;
        }
    }


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
