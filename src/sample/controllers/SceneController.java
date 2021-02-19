package sample.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class SceneController {
    private Stack<Scene> sceneStack;
    private Stage currentStage;

    public SceneController(Stage currentStage) {
        this.sceneStack = new Stack<>();
        this.currentStage = currentStage;
    }

    private Stack<Scene> getSceneStack() {
        return sceneStack;
    }

    private Stage getCurrentStage() {
        return currentStage;
    }

    /**
     * Functions
     * 1. setPrevScene
     * --- used when previous scene has to be set again, call this function, it will pop the last element from the stack and set it as the currentScene
     * --- call it to go back to previous scene
     * 2. setCurrentScene - to set a new scene, call this function and pass the new scene as parameter
     * --- call it to set a new window
     */

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

    /*
    • prerequisites for createScene function
    • need a hash set of all the scenes in the project
       • how to do that - need a list of all the views - add
    */
}
