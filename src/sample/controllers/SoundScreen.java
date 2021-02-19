package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class SoundScreen {
    @FXML
    private Slider masterSlider;
    @FXML
    private Slider sfxSlider;

    public void backClicked(ActionEvent actionEvent) { Main.sceneController.setPrevScene();}

    public void sfxSelected(MouseEvent mouseEvent) {
        //TODO logic for changing sfx volume
        System.out.println(sfxSlider.getValue());
    }

    public void masterSelected(MouseEvent mouseEvent) {
        //TODO logic for changing master volume
        System.out.println(masterSlider.getValue());
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
    }
}
