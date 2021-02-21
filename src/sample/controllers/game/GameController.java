package sample.controllers.game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class GameController {

    public static EventHandler<KeyEvent> userKeyClicked(KeyEvent event) {
        System.out.println(event.getCode().toString());
        return null;
    }

    public static EventHandler<KeyEvent> userKeyReleased(KeyEvent event) {
        System.out.println(event.getCode().toString());
        return null;
    }
}
