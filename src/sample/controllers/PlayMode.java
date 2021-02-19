package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;

public class PlayMode {

    @FXML
    private Button nextButton;

    private boolean isGameModeChosen = false;

    public void gameModeOneClicked(ActionEvent actionEvent) {
        // code to handle AI VS HUMAN
        System.out.println("AI VS HUMAN");
        isGameModeChosen = true;
        nextButton.setVisible(true);
    }

    public void gameModeTwoClicked(ActionEvent actionEvent) {
        // code to handle HUMAN VS HUMAN
        System.out.println("HUMAN VS HUMAN");
        isGameModeChosen = true;
        nextButton.setVisible(true);
    }

    public void gameModeThreeClicked(ActionEvent actionEvent) {
        // code to handle TIME TRIAL
        System.out.println("TIME TRIAL");

        // change this line -> repetition
        isGameModeChosen = true;
        nextButton.setVisible(true);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        // code to handle next screen
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/trackSelection.fxml"));
            Parent root = loader.load();

            Scene nextScene = new Scene(root, Main.maxWidth, Main.maxHeight);

            Main.sceneController.setCurrentScene(nextScene);
        } catch (Exception ex) {
            System.out.println("Error in PlayMode.next to TrackSelection");
        }
    }
}
