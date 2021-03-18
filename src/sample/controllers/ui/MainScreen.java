package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Settings;
import sample.utilities.AlertBox;
import sample.controllers.audio.*;

public class MainScreen {


    private Stage currentStage;

    private Stage getCurrentStage() {
        return currentStage;
    }
    public void setCurrentStage(Stage currentStage) {
        Main.soundManager.loop("bgm");
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            System.out.println("Error in setting current stage - MainScreen.java");
        }
    }
    public void playClicked(ActionEvent actionEvent) {

        Main.soundManager.play("button");

        System.out.println("Play clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/playModeScreen.fxml"));
            Parent root = loader.load();
            Scene playModeScene = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(playModeScene);
        } catch (Exception ex) {
            System.out.println("Error in play clicked - MainScreen.java");
        }
    }
    public void settingsClicked(ActionEvent actionEvent) {

        Main.soundManager.play("button");

        System.out.println("Settings clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/settingsScreen.fxml"));
            Parent root = loader.load();
            Scene settingsScene = new Scene(root,Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(settingsScene);
        } catch (Exception ex) {
            System.out.println("Error in settings clicked - MainScreen.java");
            ex.printStackTrace();
        }
    }
    public void controlsClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");
        System.out.println("Controls clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/controlsScreen.fxml"));
            Parent root = loader.load();
            Scene controlScene = new Scene(root,Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(controlScene);
        } catch (Exception e) {
            System.out.println("Error in controls clicked - MainScreen.java");
            e.printStackTrace();
        }
    }
    public void quitClicked(ActionEvent actionEvent) throws Exception {
        // Main.soundManager.play("button");
        try {
            System.out.println("Quit Clicked");
            AlertBox quitPrompt = new AlertBox(this.getCurrentStage(), "Are you sure you want to exit?", "YES", "NO", "QUIT");
            quitPrompt.displayPrompt();

        } catch (Exception e) {
            System.out.println("here");
        }
    }

    // just for debugging - only in dev
    public void startGame(ActionEvent actionEvent) {
        Main.soundManager.stop("bgm");
        Main.soundManager.loop("playBgm");

        try {
            Main.settings.setTrack(Settings.Track.TRACK3);
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/randomTrackScreen.fxml"));
            Parent root = loader.load();
            Scene gamePlay = new Scene(root,Main.maxWidth,Main.maxHeight);
            gamePlay.getRoot().requestFocus();
            Main.sceneManager.setCurrentScene(gamePlay);
        } catch (Exception e) {
            System.out.println("Error in startGame clicked - MainScreen.java");
            e.printStackTrace();
        }
    }
}