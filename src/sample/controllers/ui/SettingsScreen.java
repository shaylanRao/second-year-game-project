package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import sample.Main;

public class SettingsScreen {

    public void soundClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/soundScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth, Main.maxHeight);
            Main.sceneManager.setCurrentScene(screen);

            // *** NEW - CHANGED ***
            // get the actual class from the FXMLLoader
            // and configure the ui-elements to represent corrected volume values
            // every time the SoundScreen page is being loaded.
            SoundScreen soundScreen_controller = (SoundScreen) loader.getController();
            soundScreen_controller.setUp();
            //


        } catch (Exception ex) {
            System.out.println("Error in sound clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void visualClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/visualScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(screen);
        } catch (Exception ex) {
            System.out.println("Error in visual clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void gameClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/gameSettingsScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(screen);
        } catch (Exception ex) {
            System.out.println("Error in game clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void backClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        Main.sceneManager.setPrevScene();
    }
}
