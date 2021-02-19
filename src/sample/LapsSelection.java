package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class LapsSelection {

    @FXML
    private Slider lapsSlider;

    @FXML
    private Button nextButton;

    @FXML
    private Label lapsLabel;

    private String lapsLabelTextPlural = "Laps:";
    private String lapsLabelTextSingular = "Lap:";

    public void lapsSelected(MouseEvent mouseEvent) {
        // getValue returns a float, so converting it to int
        int lapsSelected = (int) lapsSlider.getValue();

        if (lapsSelected == 1) {
            lapsLabel.setText(lapsLabelTextSingular + " " + lapsSelected);
        } else {
            lapsLabel.setText(lapsLabelTextPlural + " " + lapsSelected);
        }

        Main.sceneController.activateNextButton(nextButton);
    }

    public void backButtonClicked(ActionEvent actionEvent) {
        Main.sceneController.setPrevScene();
    }

    public void nextButtonClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/vehicleSelection.fxml"));
            Parent root = loader.load();

            Scene carSelectionScene = new Scene(root,Main.maxWidth,Main.maxHeight);

            Main.sceneController.setCurrentScene(carSelectionScene);
        }catch (Exception ex ) {
            System.out.println("error in LapsSelection.java -> next button");
        }
    }


}
