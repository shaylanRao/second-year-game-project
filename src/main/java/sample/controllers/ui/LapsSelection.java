package sample.controllers.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.models.audio.SoundManager;

public class LapsSelection {

    @FXML
    private Slider lapsSlider;

    @FXML
    private Button nextButton;

    @FXML
    private Label lapsLabel;

    public void lapsSelected(MouseEvent mouseEvent) {
        SoundManager.play("button");

        // getValue returns a float, so converting it to int
        int lapsSelected = (int) lapsSlider.getValue();

        if (lapsSelected == 1) {
            String lapsLabelTextSingular = "Lap:";
            lapsLabel.setText(lapsLabelTextSingular + " " + lapsSelected);
        } else {
            String lapsLabelTextPlural = "Laps:";
            lapsLabel.setText(lapsLabelTextPlural + " " + lapsSelected);
        }

        Main.sceneManager.activateNextButton(nextButton);

        Main.settings.setLaps(lapsSelected);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        Main.sceneManager.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        SoundManager.play("button");

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/views/vehicleSelection.fxml"));
            Parent root = loader.load();
            Main.sceneManager.setCurrentRoot(root);
        }catch (Exception ex ) {
            System.out.println("error in LapsSelection.java -> next button");
        }
    }


}
