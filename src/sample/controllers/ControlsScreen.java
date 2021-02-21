package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;

public class ControlsScreen {

    /*
        The idea here was to change the image when the buttons are clicked
        By default the player 1 layout is displayed
        When the player 2 button is clicked it's changed to player 2 layout

        This method is not very clear, the user wouldn't know which button is active ergo problem!

    */

    @FXML
    private ImageView keyboardLayout;

    public void backButtonClicked(ActionEvent actionEvent) {
        System.out.println("back button pressed");
        Main.sceneController.setPrevScene();
    }

    public void player2ButtonClicked(ActionEvent actionEvent) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/sample/resources/images/Player_2_Keyboard_Layout.png"));
            keyboardLayout.setImage(image);
        } catch (Exception ex) {
            System.out.println("Error in player2ButtonClicked");
        }
    }

    public void player1ButtonClicked(ActionEvent actionEvent) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/sample/resources/images/Player_1_Keyboard_Layout.png"));
            keyboardLayout.setImage(image);
        } catch (Exception ex) {
            System.out.println("Error in player1ButtonClicked");
        }
    }

}
