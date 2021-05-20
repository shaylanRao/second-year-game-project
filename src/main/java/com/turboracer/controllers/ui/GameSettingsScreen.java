package com.turboracer.controllers.ui;

import com.turboracer.models.Settings;
import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import com.turboracer.Main;

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
