package sample.controllers.ui;

import javafx.event.ActionEvent;
import sample.Main;
import sample.audio.SoundObject;
import sample.models.Settings;

public class GameSettingsScreen {
    public void backClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        Main.sceneManager.setPrevScene();
    }

    public void hardClicked(ActionEvent actionEvent) {
        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        System.out.println("Hard mode clicked");

        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void mediumClicked(ActionEvent actionEvent) {
        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        System.out.println("Medium mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }

    public void easyClicked(ActionEvent actionEvent) {
        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        System.out.println("easy mode clicked");
        Main.settings.setDifficulty(Settings.Difficulty.EASY);
    }
}
