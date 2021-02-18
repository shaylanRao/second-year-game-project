package sample;

import javafx.event.ActionEvent;


public class PlayMode {

    public void gameModeOneClicked(ActionEvent actionEvent) {
        // code to handle AI VS HUMAN
        System.out.println("AI VS HUMAN");
    }

    public void gameModeTwoClicked(ActionEvent actionEvent) {
        // code to handle HUMAN VS HUMAN
        System.out.println("HUMAN VS HUMAN");
    }

    public void gameModeThreeClicked(ActionEvent actionEvent) {
        // code to handle TIME TRIAL
        System.out.println("TIME TRIAL");
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        // code to handle next screen
    }
}
