package sample.controllers.ui;

import javafx.event.ActionEvent;
import sample.Main;
import sample.Settings;

public class GameSettingsScreen {
    public void backClicked(ActionEvent actionEvent) { Main.sceneManager.setPrevScene(); }

    public void hardClicked(ActionEvent actionEvent) {
        System.out.println("Hard mode clicked");

        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void mediumClicked(ActionEvent actionEvent) {
        System.out.println("Medium mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void easyClicked(ActionEvent actionEvent) {
        System.out.println("easy mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }
}
