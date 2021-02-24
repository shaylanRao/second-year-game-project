package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import sample.Main;
import sample.models.Settings;

public class TrackSelection {
    @FXML
    private Button nextButton;

    public void trackOneSelected(ActionEvent actionEvent) {
        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setTrack(Settings.Track.TRACK1);
    }

    public void trackTwoSelected(ActionEvent actionEvent) {
        Main.sceneManager.activateNextButton(nextButton);
        Main.settings.setTrack(Settings.Track.TRACK2);
    }

    public void trackThreeSelected(ActionEvent actionEvent) {
        Main.sceneManager.activateNextButton(nextButton);
        Main.settings.setTrack(Settings.Track.TRACK3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/totalLapsScreen.fxml"));
            Parent root = loader.load();

            Scene totalLapsScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            Main.sceneManager.setCurrentScene(totalLapsScene);
        } catch (Exception ex) {
            System.out.println("Error in TrackSelection.java -> next button clicked");
        }
    }
}