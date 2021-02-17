package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ExtraBits.AlertBox;

public class MainScreen {

    private Stage currentStage;

    private Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        try {
            this.currentStage = currentStage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playClicked(ActionEvent actionEvent) {
        System.out.println("Play clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/playModeScreen.fxml"));
            Parent root = loader.load();

            Scene playModeScene = new Scene(root,Main.maxWidth,Main.maxWidth);

            Main.sceneController.setCurrentScene(playModeScene);

        } catch (Exception ex) {
            System.out.println("Error in play clicked - MainScreen.java");
        }

    }

    public void settingsClicked(ActionEvent actionEvent) {
        System.out.println("Settings clicked");
    }

    public void controlsClicked(ActionEvent actionEvent) {
        System.out.println("Controls clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/controlsScreen.fxml"));
            Parent root = loader.load();
            Scene controlScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            Main.sceneController.setCurrentScene(controlScene);

        } catch (Exception e) {
            System.out.println("Error in controls clicked - MainScreen.java");
            e.printStackTrace();
        }
    }

    public void quitClicked(ActionEvent actionEvent) throws Exception {
        try {
            System.out.println("Quit Clicked");
            AlertBox quitPrompt = new AlertBox(this.getCurrentStage(),
                    "Are you sure you want to exit?",
                    "QUIT",
                    "NO",
                    "QUIT");
            quitPrompt.displayPrompt();
        } catch (Exception e) {
            System.out.println("here");
        }
    }
}
