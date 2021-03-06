package com.turboracer.controllers.ui;

import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import com.turboracer.Main;

public class SettingsScreen {

    public void soundClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/soundScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);

            // *** NEW - CHANGED ***
            // get the actual class from the FXMLLoader
            // and configure the ui-elements to represent corrected volume values
            // every time the SoundScreen page is being loaded.
            SoundScreen soundScreen_controller = loader.getController();
            soundScreen_controller.setUp();
            //


        } catch (Exception ex) {
            System.out.println("Error in sound clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }



    public void gameClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/gameSettingsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in game clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        Main.sceneManager.setPrevScene();
    }
}
