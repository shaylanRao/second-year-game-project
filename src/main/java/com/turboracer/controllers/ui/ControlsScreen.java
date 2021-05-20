//Handles key clicks for UI

package com.turboracer.controllers.ui;

import com.turboracer.models.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.turboracer.Main;


public class ControlsScreen {

    /*
        The new control page is just to show a table of 2 players' control.

    */

    @FXML

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        System.out.println("back button pressed");
        Main.sceneManager.setPrevScene();
    }


}
