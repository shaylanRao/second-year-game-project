package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import sample.Main;
import sample.models.Settings;


public class VehicleSelection {
    @FXML
    private Button playButton;

    public void carOneSelected(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        System.out.println("Car one selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE1);
    }

    public void carTwoSelected(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        System.out.println("Car two selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE2);
    }

    public void carThreeSelected(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        System.out.println("Car three selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        Main.soundManager.stop("bgm");

        Main.soundManager.loop("playBgm");

        try {
            Parent root;
            FXMLLoader loader;
            //if track 3 was selected then run the raycasting demo, else run the standard game
            if (Main.settings.getTrack()==Settings.Track.TRACK3) {
                loader = new FXMLLoader(Main.class.getResource("views/randomTrackScreen.fxml"));
            } else {
                loader = new FXMLLoader(Main.class.getResource("views/gameScreen.fxml"));
            }
            root = loader.load();

            Scene gameViewScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            // solves user key click issue
            gameViewScene.getRoot().requestFocus();

            // to see all the selected options - settings
            // for debugging
            System.out.println(Main.settings.toString());

            Main.sceneManager.setCurrentScene(gameViewScene);
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
            ex.printStackTrace();
        }
    }


}
