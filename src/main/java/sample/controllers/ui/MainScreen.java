package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Settings;
import sample.utilities.AlertBox;
import sample.controllers.audio.*;

public class MainScreen {


    private Stage currentStage;

    private Stage getCurrentStage() {
        return currentStage;
    }
    public void setCurrentStage(Stage currentStage) {
        SoundManager.loop("bgm");
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            System.out.println("Error in setting current stage - MainScreen.java");
        }
    }
    public void playClicked(ActionEvent actionEvent) {

        SoundManager.play("button");

        System.out.println("Play clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/playModeScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in play clicked - MainScreen.java");
            ex.printStackTrace();
        }
    }
    public void settingsClicked(ActionEvent actionEvent) {

        SoundManager.play("button");

        System.out.println("Settings clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/settingsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception ex) {
            System.out.println("Error in settings clicked - MainScreen.java");
            ex.printStackTrace();
        }
    }
    public void controlsClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        System.out.println("Controls clicked");
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/controlsScreen.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception e) {
            System.out.println("Error in controls clicked - MainScreen.java");
            e.printStackTrace();
        }
    }
    public void quitClicked(ActionEvent actionEvent) throws Exception {
        // Main.soundManager.play("button");
        try {
            System.out.println("Quit Clicked");
            AlertBox quitPrompt = new AlertBox(this.getCurrentStage(), "Are you sure you want to exit?", "YES", "NO", "QUIT");
            quitPrompt.displayPrompt();

        } catch (Exception e) {
        }
    }

    // just for debugging - only in dev
    public void startGame(ActionEvent actionEvent) {
        SoundManager.stop("bgm");
        SoundManager.loop("playBgm");

        try {
            Main.settings.setPlayMode(Settings.PlayMode.MULTIPLAYER);  /*multiplayer, just remove if not wanted*/
            Main.settings.setVehicleType(Settings.VehicleType.VEHICLE2);
            Main.settings.setVehicle2Type(Settings.VehicleType.VEHICLE3);
            Main.settings.setTrack(Settings.Track.TRACK3);
            Main.settings.setLaps(3);
//          FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/randomTrackScreen.fxml")); - /*single player*/
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/multiplayer.fxml"));

            Parent root = loader.load();
            root.requestFocus();
            Main.sceneManager.setCurrentRoot(root);
        } catch (Exception e) {
            System.out.println("Error in startGame clicked - MainScreen.java");
            e.printStackTrace();
        }
    }
}