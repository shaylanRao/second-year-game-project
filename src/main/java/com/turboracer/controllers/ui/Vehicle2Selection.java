package com.turboracer.controllers.ui;

import com.turboracer.models.Settings;
import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import com.turboracer.Main;


public class Vehicle2Selection {
    @FXML
    private Button playButton;

    public void carOneSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car one selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicle2Type(Settings.VehicleType.VEHICLE1);
    }

    public void carTwoSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car two selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicle2Type(Settings.VehicleType.VEHICLE2);
    }

    public void carThreeSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car three selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicle2Type(Settings.VehicleType.VEHICLE3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        SoundManager.stop("bgm");

        SoundManager.loop("playBgm");
        try {
            Parent root;
            FXMLLoader loader;
            //if track 3 was selected then run the raycasting demo, else run the standard game
            if (Main.settings.getPlayMode().equals(Settings.PlayMode.MULTIPLAYER)) {
                loader = new FXMLLoader(Main.class.getResource("/views/multiplayer.fxml"));
            }
            else{
                loader = new FXMLLoader(Main.class.getResource("/views/randomTrackScreen.fxml"));
            }
            root = loader.load();
            // solves user key click issue
            root.requestFocus();

            // to see all the selected options - settings
            // for debugging
            System.out.println(Main.settings.toString());

            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
            ex.printStackTrace();
        }
    }


}
