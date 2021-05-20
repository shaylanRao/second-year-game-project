package com.turboracer.controllers.ui;

import com.turboracer.models.Settings;
import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import com.turboracer.Main;


public class VehicleSelection {
    @FXML
    private Button playButton;

    public void carOneSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car one selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE1);
    }

    public void carTwoSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car two selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE2);
    }

    public void carThreeSelected(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Car three selected");
        Main.sceneManager.activateNextButton(playButton);

        Main.settings.setVehicleType(Settings.VehicleType.VEHICLE3);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/vehicle2Selection.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        }catch (Exception ex ) {
            System.out.println("error in VehicleSelection.java -> Vehicle2Selection.java");
        }
    }


}
