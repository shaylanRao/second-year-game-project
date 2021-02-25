package sample.models;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * Handles scene changes, and initialised with the primary stage.
 * */

public class SceneManager {
    private final Stack<Scene> sceneStack;
    private final Stage currentStage;

    public SceneManager(Stage currentStage) {
        this.sceneStack = new Stack<>();
        this.currentStage = currentStage;
    }

    private Stack<Scene> getSceneStack() {
        return sceneStack;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    /**
     * This function will set the current scene of the primary stage to the previous scene
     * This has to be called when the previous scene needs to be set as the current scene
     * */
    public void setPrevScene() {
        // check for stack underflow
        if (!(this.getSceneStack().isEmpty())) {
            this.sceneStack.pop();
            Scene prevScene = this.getSceneStack().peek();
            this.getCurrentStage().setScene(prevScene);
        }
    }


    /**
     * @param currentScene
     * This function will set the current scene of the primary stage to the new scene which is passed as a parameter
     * This has to be called when the new scene needs to be set as the current scene
     * */
    public void setCurrentScene(Scene currentScene) {
        this.getSceneStack().push(currentScene);
        this.getCurrentStage().setScene(currentScene);
    }


    /**
     * @param nextButton
     * This function will activate nextButton i.e. set it's visibility to true
     */
    public void activateNextButton(Button nextButton) {
        nextButton.setVisible(true);
    }
}
