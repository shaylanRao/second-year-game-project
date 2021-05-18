package sample.models;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;

import java.util.Stack;

public class SceneManager {
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    private boolean paused = false;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private Game game;

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

    public void pause() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/pauseScreen.fxml"));
            Parent root = loader.load();
            setCurrentRoot(root);
            root.requestFocus();
        } catch (Exception e) {
            System.out.println("error in pausing");
            e.printStackTrace();
        }
    }

    /**
     * @param nextButton
     * This function will activate nextButton i.e. set it's visibility to true
     */
    public void activateNextButton(Button nextButton) {
        nextButton.setVisible(true);
    }
}
