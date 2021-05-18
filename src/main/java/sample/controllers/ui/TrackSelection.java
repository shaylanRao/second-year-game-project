package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.models.audio.SoundManager;
import sample.models.Settings;

public class TrackSelection {
    @FXML
    private Slider wiggleSlider;

    @FXML
    private Label wiggleLabel;

    @FXML
    private Slider trackWidthSlider;

    @FXML
    private Label trackWidthLabel;

    @FXML
    private Button nextButton;
    public void wiggleSelect(MouseEvent mouseEvent) {
        SoundManager.play("button");

        // getValue returns a float, so converting it to int
        int wiggleFactor = (int) wiggleSlider.getValue();
        String wiggleTextLabel = "Wiggle Factor:";
        wiggleLabel.setText(wiggleTextLabel + " " + wiggleFactor);

        Main.settings.setWiggleFactor(wiggleFactor);
    }

    public void trackWidthSelect(MouseEvent mouseEvent) {
        SoundManager.play("button");

        // getValue returns a float, so converting it to int
        int wiggleFactor = (int) trackWidthSlider.getValue();
        String trackWidthLabel = "Wiggle Factor:";
        wiggleLabel.setText(trackWidthLabel + " " + wiggleFactor);

        Main.settings.setWiggleFactor(wiggleFactor);
    }

//    public void trackOneSelected(ActionEvent actionEvent) {
//        SoundManager.play("button");
//
//        Main.sceneManager.activateNextButton(nextButton);
//
//        Main.settings.setTrack(Settings.Track.TRACK1);
//    }

//    public void trackTwoSelected(ActionEvent actionEvent) {
//        SoundManager.play("button");
//
//        Main.sceneManager.activateNextButton(nextButton);
//        Main.settings.setTrack(Settings.Track.TRACK2);
//    }
//
//    public void trackThreeSelected(ActionEvent actionEvent) {
//        SoundManager.play("button");
//
//        Main.sceneManager.activateNextButton(nextButton);
//        Main.settings.setTrack(Settings.Track.TRACK3);
//    }

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
