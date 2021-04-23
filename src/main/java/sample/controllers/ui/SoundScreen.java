package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.controllers.audio.SoundManager;
import sample.controllers.audio.SoundObject;

public class SoundScreen {



    @FXML
    private Label sfxLabel;
    @FXML
    private Slider sfxSlider;

    @FXML
    private Label game_sfxLabel;
    @FXML
    private Slider game_sfxSlider;

    @FXML
    private Label bgm_Label;
    @FXML
    private Slider bgm_Slider;


    @FXML
    private Label music_Label;
    @FXML
    private Slider music_Slider;

    @FXML
    private Label masterLabel;
    @FXML
    private Slider masterSlider;


    // *** NEW - CHANGED ***
    // call this method, after creating this scene
    // in order to update the values of the ui-elements
    // to represent the relative values of the SoundManager
    public void setUp()
    {


        // get the actual master-volume from SoundManager and update the 'masterSlider' value
        masterSlider.setValue(SoundManager.getMasterVolume() * 100.f);

        // get the actual UI_SFX volume from SoundManager and update the 'sfxSlider' value
        sfxSlider.setValue(SoundManager.getVolume(SoundObject.UI_SFX) * 100.f);

        // get the actual GAME_SFX volume from SoundManager and update the 'game_sfxSlider' value
        game_sfxSlider.setValue(SoundManager.getVolume(SoundObject.GAME_SFX) * 100.f);


        // get the actual BG volume from SoundManager and update the 'bgm_Slider' value
        bgm_Slider.setValue(SoundManager.getVolume(SoundObject.BG_MUSIC) * 100.f);


        // get the actual MUSIC volume from SoundManager and update the 'music_Slider' value
        music_Slider.setValue(SoundManager.getVolume(SoundObject.MUSIC) * 100.f);


    }


    public void backClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        Main.sceneManager.setPrevScene();
    }

    public void sfxSelected(MouseEvent mouseEvent) {
        float sfxVol = (float) sfxSlider.getValue();
        sfxLabel.setText("sfx volume: " + sfxVol);
        System.out.println(sfxSlider.getValue());

        Main.settings.setSfxVol(sfxVol);
        SoundManager.setVolume(sfxVol / 100.0f, SoundObject.UI_SFX);
    }

    public void masterSelected(MouseEvent mouseEvent) {

        SoundManager.play("button");
        float masterVol = (float) masterSlider.getValue();
        masterLabel.setText("master volume: " + masterVol);
        System.out.println(masterSlider.getValue());

        Main.settings.setMasterVol(masterVol);
        SoundManager.setMasterVolume(masterVol / 100.0f);
    }


    public void game_sfxSelected(MouseEvent e)
    {
        float game_sfxVol = (float) game_sfxSlider.getValue();
        game_sfxLabel.setText("game_sfx volume: " + game_sfxVol);
        SoundManager.setVolume(game_sfxVol / 100.0f, SoundObject.GAME_SFX);
    }

    public void bgm_Selected(MouseEvent e)
    {
        float vol = (float) bgm_Slider.getValue();
        bgm_Label.setText("Background volume: " + vol);
        SoundManager.setVolume(vol / 100.0f, SoundObject.BG_MUSIC);
    }

    public void music_Selected(MouseEvent e)
    {
        float vol = (float) music_Slider.getValue();
        music_Label.setText("Music volume: " + vol);
        SoundManager.setVolume(vol / 100.0f, SoundObject.MUSIC);
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
    }
}
