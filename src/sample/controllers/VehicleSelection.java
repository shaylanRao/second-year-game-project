package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import sample.Game;
import sample.Main;

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
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/gameView.fxml"));
            Parent root = loader.load();

            Scene gameViewScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            Main.sceneController.setCurrentScene(gameViewScene);

            gameViewScene.setOnKeyPressed(GameController::userKeyClicked);

            gameViewScene.setOnKeyReleased(GameController::userKeyReleased);
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
        }
    }


}
