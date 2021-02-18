package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TrackSelection {
    public void trackOneSelected(ActionEvent actionEvent) {
    }

    public void trackTwoSelected(ActionEvent actionEvent) {
    }

    public void trackThreeSelected(ActionEvent actionEvent) {
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
