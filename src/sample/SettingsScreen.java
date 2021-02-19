package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Main;

public class SettingsScreen {
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void soundClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/soundScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneController.setCurrentScene(screen);
        } catch (Exception ex) {
            System.out.println("Error in sound clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void visualClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/visualScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneController.setCurrentScene(screen);
        } catch (Exception ex) {
            System.out.println("Error in visual clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void gameClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/gameSettingsScreen.fxml"));
            Parent root = loader.load();
            Scene screen = new Scene(root, Main.maxWidth,Main.maxHeight);
            Main.sceneController.setCurrentScene(screen);
        } catch (Exception ex) {
            System.out.println("Error in game clicked - SettingsScreen.java");
            ex.printStackTrace();
        }
    }

    public void backClicked(ActionEvent actionEvent) { Main.sceneController.setPrevScene(); }
}
