package sample.models;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Stack;

public class SceneManager {
    private Stack<Scene> sceneStack;
    private Stage currentStage;

    public SceneManager(Stage currentStage) {
        this.sceneStack = new Stack<>();
        this.currentStage = currentStage;
    }

    private Stack<Scene> getSceneStack() {
        return sceneStack;
    }

    private Stage getCurrentStage() {
        return currentStage;
    }

    public void setPrevScene() {
        // check for stack underflow
        if (!(this.getSceneStack().isEmpty())) {
            this.sceneStack.pop();
            Scene prevScene = this.getSceneStack().peek();
            this.getCurrentStage().setScene(prevScene);
        }
    }

    public void setCurrentScene(Scene currentScene) {
        this.getSceneStack().push(currentScene);
        this.getCurrentStage().setScene(currentScene);
    }


    public void activateNextButton(Button nextButton) {
        nextButton.setVisible(true);
    }
}
