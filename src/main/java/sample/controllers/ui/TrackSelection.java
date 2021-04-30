package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import sample.Main;
import sample.models.audio.SoundManager;
import sample.models.Settings;

public class TrackSelection {
    @FXML
    private Button nextButton;

    public void trackOneSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setTrack(Settings.Track.TRACK1);
    }

    public void trackTwoSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.activateNextButton(nextButton);
        Main.settings.setTrack(Settings.Track.TRACK2);
    }

    public void trackThreeSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.activateNextButton(nextButton);
        Main.settings.setTrack(Settings.Track.TRACK3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/totalLapsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in TrackSelection.java -> next button clicked");
        }
    }
}
