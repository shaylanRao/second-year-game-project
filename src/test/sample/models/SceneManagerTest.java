package sample.models;

import javafx.scene.Scene;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SceneManagerTest {
    private SceneManager sceneManager;
    @Before
    void init() {
        Scene scene = new Scene(null, 100, 100);
        sceneManager = new SceneManager(scene);
    }

    @Test
    void setPrevScene() {

    }

    @Test
    void setCurrentRoot() {
    }

    @Test
    void activateNextButton() {
    }
}