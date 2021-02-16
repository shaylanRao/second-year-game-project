package sample;

import javafx.stage.Stage;

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
}
