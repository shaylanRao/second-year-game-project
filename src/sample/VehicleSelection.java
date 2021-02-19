package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;

public class VehicleSelection {
    @FXML
    private Button playButton;

    public void carOneSelected(ActionEvent actionEvent) {
        System.out.println("Car one selected");
        Main.sceneController.activateNextButton(playButton);
    }

    public void carTwoSelected(ActionEvent actionEvent) {
        System.out.println("Car two selected");
        Main.sceneController.activateNextButton(playButton);
    }

    public void carThreeSelected(ActionEvent actionEvent) {
        System.out.println("Car three selected");
        Main.sceneController.activateNextButton(playButton);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
    }


}
