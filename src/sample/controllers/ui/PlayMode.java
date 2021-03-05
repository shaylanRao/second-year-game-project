package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;
import sample.audio.SoundObject;
import sample.models.Settings;

public class PlayMode {

    @FXML
    private Button nextButton;

    public void gameModeOneClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        // code to handle AI VS HUMAN
        System.out.println("AI VS HUMAN");

        // this will activate the next button
        Main.sceneManager.activateNextButton(nextButton);

        // setting play modes
        Main.settings.setPlayMode(Settings.PlayMode.AI);
    }

    public void gameModeTwoClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        // code to handle HUMAN VS HUMAN
        System.out.println("HUMAN VS HUMAN");

        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setPlayMode(Settings.PlayMode.STANDARD);
    }

    public void gameModeThreeClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        // code to handle TIME TRIAL
        System.out.println("TIME TRIAL");

        // change this line -> repetition
        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setPlayMode(Settings.PlayMode.TIMETRIAL);
    }

    public void backButtonClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        // code to handle next screen
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/trackSelection.fxml"));
            Parent root = loader.load();

            Scene trackSelectionScene = new Scene(root, Main.maxWidth, Main.maxHeight);

            Main.sceneManager.setCurrentScene(trackSelectionScene);
        } catch (Exception ex) {
            System.out.println("Error in PlayMode.next to TrackSelection");
        }
    }
}
