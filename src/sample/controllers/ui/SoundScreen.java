package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;

public class SoundScreen {
    @FXML
    private Label sfxLabel;
    @FXML
    private Label masterLabel;
    @FXML
    private Slider masterSlider;
    @FXML
    private Slider sfxSlider;

    public void backClicked(ActionEvent actionEvent) { Main.sceneManager.setPrevScene();}

    public void sfxSelected(MouseEvent mouseEvent) {
        int sfxVol = (int) sfxSlider.getValue();
        sfxLabel.setText("sfx volume: " + sfxVol);
        System.out.println(sfxSlider.getValue());
    }

    public void masterSelected(MouseEvent mouseEvent) {
        int masterVol = (int) masterSlider.getValue();
        masterLabel.setText("master volume: " + masterVol);
        System.out.println(masterSlider.getValue());
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
    }
}
