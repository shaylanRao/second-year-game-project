package sample;

import javafx.event.ActionEvent;

public class Controller {

    public void playClicked(ActionEvent actionEvent) {
        System.out.println("Play clicked");
    }

    public void settingsClicked(ActionEvent actionEvent) {
        System.out.println("Settings clicked");
    }

    public void controlsClicked(ActionEvent actionEvent) {
        System.out.println("Controls clicked");
    }

    public void quitClicked(ActionEvent actionEvent) {
        System.out.println("Quit clicked");
    }
}
