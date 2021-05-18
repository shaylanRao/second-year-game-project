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
import sample.models.Track;
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
        wiggleLabel.setText(wiggleTextLabel + " " + wiggleFactor + "%");


        Main.settings.setWiggleFactor((int) wiggleFactor/2);
    }

    public void trackWidthSelect(MouseEvent mouseEvent) {
        SoundManager.play("button");

        // getValue returns a float, so converting it to int
        int trackWidthFactor = (int) trackWidthSlider.getValue();
        String trackWidthTextLabel = "Track Width:";
        trackWidthLabel.setText(trackWidthTextLabel + " " + trackWidthFactor + "%");

        int trackValue = (int) ((trackWidthFactor * 2) + 100);

        Main.settings.setTrackWidth(trackValue);
    }


    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        Main.track = new Track();
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/totalLapsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in TrackSelection.java -> next button clicked");
        }
    }
}
