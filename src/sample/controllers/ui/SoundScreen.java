package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import sample.Main;

import java.util.concurrent.atomic.AtomicInteger;


public class SoundScreen {
//    @FXML
//    private Label sfxLabel;
    @FXML
    private Label masterLabel;
    @FXML
    private Slider masterSlider;
    @FXML
    private ToggleGroup sfxSelect;
//    private Slider sfxSlider;

    public void backClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public ToggleGroup getSfxSelect() {
        return sfxSelect;
    }

    public void setSfxSelect(ToggleGroup sfxSelect) {
        AtomicInteger click_num = new AtomicInteger();
        click_num.set(click_num.get() +1);
        if ((click_num.get() %2 ==1)){
            Main.soundManager.stop("button");
        }else {
            Main.soundManager.play("button");
        }
        this.sfxSelect = sfxSelect;
    }


/*    public void sfxSelected(MouseEvent mouseEvent) {
        Main.soundManager.play("button");
        float sfxVol = (float) sfxSlider.getValue();
        sfxLabel.setText("sfx volume: " + sfxVol);
        System.out.println(sfxSlider.getValue());

        Main.settings.setSfxVol(sfxVol);
    }
    */

    public void masterSelected(MouseEvent mouseEvent) {
        Main.soundManager.play("button");
        float masterVol = (float) masterSlider.getValue();
        masterLabel.setText("master volume: " + masterVol);
        System.out.println(masterSlider.getValue());

        Main.settings.setMasterVol(masterVol);
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        Main.soundManager.play("button");
    }

}
