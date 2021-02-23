package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;
import sample.models.Game;
import sample.models.Settings;


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
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("views/gameScreen.fxml"));
            Parent root = loader.load();

            Scene gameViewScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            // solves user key click issue
            gameViewScene.getRoot().requestFocus();

            // to see all the selected options - settings
            // for debugging
            System.out.println(Main.settings.toString());

            Main.sceneManager.setCurrentScene(gameViewScene);

            // game is initialised over here

            Main.game = new Game();
            Main.game.gameLoop();
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
            ex.printStackTrace();
        }
    }


}
