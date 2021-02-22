package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;
import sample.Settings;
import sample.controllers.game.GameController;


public class VehicleSelection {
    @FXML
    private Button playButton;

    public void carOneSelected(ActionEvent actionEvent) {
        System.out.println("Car one selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE1);
    }

    public void carTwoSelected(ActionEvent actionEvent) {
        System.out.println("Car two selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE2);
    }

    public void carThreeSelected(ActionEvent actionEvent) {
        System.out.println("Car three selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/gameView.fxml"));
            Parent root = loader.load();

            Scene gameViewScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            // to see all the selected options - settings
            System.out.println(Main.settings.toString());

            Main.sceneManager.setCurrentScene(gameViewScene);

            // TODO: Figure out how to put this in the handler
            gameViewScene.setOnKeyPressed(GameController::userKeyClicked);
            gameViewScene.setOnKeyReleased(GameController::userKeyReleased);
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
            ex.printStackTrace();
        }
    }


}
