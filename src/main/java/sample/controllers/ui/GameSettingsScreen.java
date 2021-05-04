package sample.controllers.ui;

import javafx.event.ActionEvent;
import sample.Main;
import sample.controllers.audio.SoundManager;
import sample.models.Settings;

public class GameSettingsScreen {

    public void backClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        Main.sceneManager.setPrevScene();
    }

    public void hardClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Hard mode clicked");

        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void mediumClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("Medium mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void easyClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        System.out.println("easy mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }
}
