//Handles key clicks for UI

package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.Main;
import sample.audio.SoundObject;

public class ControlsScreen {

    /*
        The new control page is just to show a table of 2 players' control.

    */

    @FXML

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundObject button = new SoundObject("src\\sample\\resources\\audio\\button.wav");
        button.play();
        
        System.out.println("back button pressed");
        Main.sceneManager.setPrevScene();
    }


}
