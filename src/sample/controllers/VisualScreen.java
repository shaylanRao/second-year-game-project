package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class VisualScreen {
    @FXML
    private Slider brightSlider;
    @FXML
    private ToggleGroup colorBlind;

    public void brightSelected(MouseEvent mouseEvent) {
    }

    public void backClicked(ActionEvent actionEvent) { Main.sceneController.setPrevScene(); }
}
