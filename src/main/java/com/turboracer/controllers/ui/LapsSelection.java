package com.turboracer.controllers.ui;

import com.turboracer.models.Settings;
import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import com.turboracer.Main;

import java.io.IOException;

public class LapsSelection {
    @FXML
    private Slider lapsSlider;

    @FXML
    private Button nextButton;

    @FXML
    private Label lapsLabel;

    public void lapsSelected(MouseEvent mouseEvent) {
        SoundManager.play("button");

        // getValue returns a float, so converting it to int
        int lapsSelected = (int) lapsSlider.getValue();

        if (lapsSelected == 1) {
            String lapsLabelTextSingular = "Lap:";
            lapsLabel.setText(lapsLabelTextSingular + " " + lapsSelected);
        } else {
            String lapsLabelTextPlural = "Laps:";
            lapsLabel.setText(lapsLabelTextPlural + " " + lapsSelected);
        }

        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setLaps(lapsSelected);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) throws IOException {
        SoundManager.play("button");

        try {
            Parent root;
            if (Main.settings.getPlayMode().equals(Settings.PlayMode.AI)) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/aiModeCarSelection.fxml"));
                root = loader.load();
                root.requestFocus();
            }else if (Main.settings.getPlayMode().equals(Settings.PlayMode.TIMETRIAL)){
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/aiModeCarSelection.fxml"));
                root = loader.load();
                root.requestFocus();
            }
            else{
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/vehicleSelection.fxml"));
                root = loader.load();
                root.requestFocus();
            }

            System.out.println(Main.settings.toString());
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("error inside VehicleSelection.java - next button clicked");
            ex.printStackTrace();
        }

    }



}
