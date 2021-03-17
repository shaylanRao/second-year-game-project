package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

public class SettingsScreen {

    public void soundClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/soundScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneManager.setCurrentScene(screen);
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
