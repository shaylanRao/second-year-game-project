package com.turboracer.controllers.ui;

import com.turboracer.models.Settings;
import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import com.turboracer.Main;

public class PlayMode {

    @FXML
    private Button nextButton;

    public void gameModeOneClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        // code to handle AI VS HUMAN
        System.out.println("AI VS HUMAN");

        // this will activate the next button
        Main.sceneManager.activateNextButton(nextButton);

        // setting play modes
        Main.settings.setPlayMode(Settings.PlayMode.AI);
    }

    public void gameModeTwoClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        // code to handle HUMAN VS HUMAN
        System.out.println("HUMAN VS HUMAN");

        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setPlayMode(Settings.PlayMode.MULTIPLAYER);
    }

    public void gameModeThreeClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        // code to handle TIME TRIAL
        System.out.println("TIME TRIAL");

        // change this line -> repetition
        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setPlayMode(Settings.PlayMode.TIMETRIAL);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        if (!Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {
            // code to handle next screen
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/trackSelection.fxml"));
                Parent root = loader.load();
                Main.sceneManager.setCurrentRoot(root);
            } catch (Exception ex) {
                System.out.println("Error in PlayMode.next to TrackSelection");
            }
        } else {
            // code to handle next screen
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/totalLapsScreen.fxml"));
                Parent root = loader.load();
                Main.sceneManager.setCurrentRoot(root);
            } catch (Exception ex) {
                System.out.println("Error in PlayMode.next to TrackSelection");
            }
        }
    }
}
