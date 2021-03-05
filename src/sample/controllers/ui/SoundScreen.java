package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.audio.SoundObject;

public class SoundScreen {
    @FXML
    private Label sfxLabel;
    @FXML
    private Label masterLabel;
    @FXML
    private Slider masterSlider;
    @FXML
    private Slider sfxSlider;

    public void backClicked(ActionEvent actionEvent) {

        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();

        Main.sceneManager.setPrevScene();
    }

    public void sfxSelected(MouseEvent mouseEvent) {
        float sfxVol = (float) sfxSlider.getValue();
        sfxLabel.setText("sfx volume: " + sfxVol);
        System.out.println(sfxSlider.getValue());

        Main.settings.setSfxVol(sfxVol);
    }

    public void masterSelected(MouseEvent mouseEvent) {
        float masterVol = (float) masterSlider.getValue();
        masterLabel.setText("master volume: " + masterVol);
        System.out.println(masterSlider.getValue());

        Main.settings.setMasterVol(masterVol);
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();
    }
}
