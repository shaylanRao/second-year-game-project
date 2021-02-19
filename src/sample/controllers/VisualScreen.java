package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class VisualScreen {
    @FXML
    private Label brightLabel;
    @FXML
    private Slider brightSlider;
    @FXML
    private ToggleGroup colorBlind;

    public void brightSelected(MouseEvent mouseEvent) {
        int bright = (int) brightSlider.getValue();
        brightLabel.setText("brightness: " + bright);
        System.out.println(brightSlider.getValue());
    }

    public void backClicked(ActionEvent actionEvent) { Main.sceneController.setPrevScene(); }
}
