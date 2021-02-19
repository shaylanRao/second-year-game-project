package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

public class SettingsScreen {
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void soundClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/soundScreen.fxml"));
            Parent root = loader.load();
            Scene soundScreen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneController.setCurrentScene(soundScreen);
        } catch (Exception ex) {
            System.out.println("Error in sound clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void visualClicked(ActionEvent actionEvent) {
    }

    public void gameClicked(ActionEvent actionEvent) {
    }

    public void backClicked(ActionEvent actionEvent) { Main.sceneController.setPrevScene(); }
}
