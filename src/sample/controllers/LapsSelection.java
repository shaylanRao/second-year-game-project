package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class LapsSelection {

    @FXML
    private Slider lapsSlider;

    public void lapsSelected(MouseEvent mouseEvent) {
        System.out.println(lapsSlider.getValue());
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
    }
}
