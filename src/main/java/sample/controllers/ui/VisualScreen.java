package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.models.audio.SoundManager;

public class VisualScreen {
    @FXML
    private Label brightLabel;
    @FXML
    private Slider brightSlider;
    @FXML
    private ToggleGroup colorBlind;

    public void brightSelected(MouseEvent mouseEvent) {
        SoundManager.play("button");

        float bright = (float) brightSlider.getValue();
        brightLabel.setText("brightness: " + bright);
        System.out.println(brightSlider.getValue());

        Main.settings.setBright(bright);
    }

    public void backClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }
}
