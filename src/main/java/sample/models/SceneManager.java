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
    private final Scene currentScene;

    public SceneManager(Scene currentScene) {
        rootStack = new Stack<>();
        this.currentScene = currentScene;
    }

    public void setPrevScene() {
        // check for stack underflow
        if (!(rootStack.isEmpty())) {
            rootStack.pop();
            Parent root = rootStack.peek();
            currentScene.setRoot(root);
        }
    }

    public void setCurrentRoot(Parent currentRoot) {
        rootStack.push(currentRoot);
        currentScene.setRoot(currentRoot);
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
