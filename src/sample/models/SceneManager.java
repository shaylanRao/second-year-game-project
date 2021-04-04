package sample.models;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.Stack;

public class SceneManager {
    private final Stack<Parent> rootStack;
    //TODO change currentStage to currentScene as we are only ever using stage to access the scene
    private final Scene currentScene;

    public SceneManager(Scene currentScene) {
        this.rootStack = new Stack<>();
        this.currentScene = currentScene;
    }

    public void setPrevScene() {
        // check for stack underflow
        if (!(this.rootStack.isEmpty())) {
            this.rootStack.pop();
            Parent root = this.rootStack.peek();
            this.currentScene.setRoot(root);
        }
    }

    public void setCurrentRoot(Parent currentRoot) {
        this.rootStack.push(currentRoot);
        this.currentScene.setRoot(currentRoot);
    }

    /**
     * @param nextButton
     * This function will activate nextButton i.e. set it's visibility to true
     */
    public void activateNextButton(Button nextButton) {
        nextButton.setVisible(true);
    }
}
