package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.ExtraBits.AlertBox;

public class MainScreen {

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

    public void playClicked(ActionEvent actionEvent) {
        System.out.println("Play clicked");
    }

    public void settingsClicked(ActionEvent actionEvent) {
        System.out.println("Settings clicked");
    }

    public void controlsClicked(ActionEvent actionEvent) {
        System.out.println("Controls clicked");
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
