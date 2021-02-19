package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class PlayMode {

    @FXML
    private Button nextButton;

    public void gameModeOneClicked(ActionEvent actionEvent) {
        // code to handle AI VS HUMAN
        System.out.println("AI VS HUMAN");

        // this will activate the next button
        Main.sceneController.activateNextButton(nextButton);
    }

    public void gameModeTwoClicked(ActionEvent actionEvent) {
        // code to handle HUMAN VS HUMAN
        System.out.println("HUMAN VS HUMAN");
        Main.sceneController.activateNextButton(nextButton);
    }

    public void gameModeThreeClicked(ActionEvent actionEvent) {
        // code to handle TIME TRIAL
        System.out.println("TIME TRIAL");

        // change this line -> repetition
        Main.sceneController.activateNextButton(nextButton);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        // code to handle next screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/trackSelection.fxml"));
            Parent root = loader.load();

            Scene trackSelectionScene = new Scene(root, Main.maxWidth, Main.maxHeight);

            Main.sceneController.setCurrentScene(trackSelectionScene);
        } catch (Exception ex) {
            System.out.println("Error in PlayMode.next to TrackSelection");
        }
    }
}
