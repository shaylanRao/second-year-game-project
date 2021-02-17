package sample;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlsScreen {
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public void player2Hover(MouseEvent mouseEvent) {
        // supposed to change the keyboard_layout image when this is called
        System.out.println("mouse hover detected");
    }

    public void controlsToMainScreen(ActionEvent actionEvent) {
        System.out.println("back button pressed");
        Main.sceneController.setPrevScene();
    }
}
