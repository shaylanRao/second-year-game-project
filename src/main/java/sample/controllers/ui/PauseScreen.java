package sample.controllers.ui;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;

public class PauseScreen {

    public void keyClicked(KeyEvent event) {
        KeyCode code = event.getCode();
        switch (code) {
            case P:
                if (Main.sceneManager.isPaused()) {
                    Main.sceneManager.setPaused(false);
                    Main.sceneManager.setPrevScene();
                }
        }
    }
}
