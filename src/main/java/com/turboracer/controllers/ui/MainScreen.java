package com.turboracer.controllers.ui;

import com.turboracer.models.audio.SoundManager;
import com.turboracer.utilities.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.turboracer.Main;


public class MainScreen {


    private Stage currentStage;

    private Stage getCurrentStage() {
        return currentStage;
    }
    public void setCurrentStage(Stage currentStage) {
        SoundManager.loop("bgm");
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            System.out.println("Error in setting current stage - MainScreen.java");
        }
    }
    public void playClicked(ActionEvent actionEvent) {

        SoundManager.play("button");

        System.out.println("Play clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/playModeScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in play clicked - MainScreen.java");
            ex.printStackTrace();
        }
    }
    public void settingsClicked(ActionEvent actionEvent) {

        SoundManager.play("button");

        System.out.println("Settings clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/settingsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in settings clicked - MainScreen.java");
            ex.printStackTrace();
        }
    }
    public void controlsClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        System.out.println("Controls clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/controlsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception e) {
            System.out.println("Error in controls clicked - MainScreen.java");
            e.printStackTrace();
        }
    }
    public void quitClicked(ActionEvent actionEvent) throws Exception {
        // Main.soundManager.play("button");
        try {
            System.out.println("Quit Clicked");
            AlertBox quitPrompt = new AlertBox(getCurrentStage(), "Are you sure you want to exit?", "YES", "NO", "QUIT");
            quitPrompt.displayPrompt();

        } catch (Exception e) {
        }
    }
}