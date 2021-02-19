package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class TrackSelection {
    @FXML
    private Button nextButton;

    public void trackOneSelected(ActionEvent actionEvent) {
        Main.sceneController.activateNextButton(nextButton);
    }

    public void trackTwoSelected(ActionEvent actionEvent) {
        Main.sceneController.activateNextButton(nextButton);
    }

    public void trackThreeSelected(ActionEvent actionEvent) {
        Main.sceneController.activateNextButton(nextButton);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/totalLapsScreen.fxml"));
            Parent root = loader.load();

            Scene totalLapsScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            Main.sceneController.setCurrentScene(totalLapsScene);
        } catch (Exception ex) {
            System.out.println("Error in TrackSelection.java -> next button clicked");
        }
    }

}
