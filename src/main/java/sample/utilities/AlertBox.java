package sample.utilities;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.audio.SoundManager;

/**
 * This class represents a general UI alert box. E.g., a 'quit game' prompt.
 */
public class AlertBox {

    Label alertBoxDescription;
    Button acceptButton;
    Button declineButton;
    Stage alertWindow;
    Stage currentStage;
    String alertBoxDescriptionText;
    String acceptButtonText;
    String declineButtonText;
    String alertBoxTitle;

    final int alertBoxMaxWidth = 320;
    final int alertBoxMaxHeight = 180;

    public AlertBox(Stage currentStage, String alertBoxDescriptionText, String acceptButtonText, String declineButtonText,String alertBoxTitle) {
        this.currentStage = currentStage;
        this.alertBoxDescriptionText = alertBoxDescriptionText;
        this.acceptButtonText = acceptButtonText;
        this.declineButtonText = declineButtonText;
        this.alertBoxTitle = alertBoxTitle;
    }

    /**
     * Displays the prompt.
     *
     *
     */
    public void displayPrompt(){

        alertWindow = new Stage();

        // this is to make it look like a prompt/popup
        alertWindow.initModality(Modality.APPLICATION_MODAL);

        alertBoxDescription = new Label(alertBoxDescriptionText);
        acceptButton = new Button(acceptButtonText);
        declineButton = new Button(declineButtonText);

        VBox layout = new VBox();
        layout.getChildren().addAll(alertBoxDescription, acceptButton,declineButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(20);

        Scene scene = new Scene(layout, alertBoxMaxWidth, alertBoxMaxHeight);

        scene.getStylesheets().add("stylesheets/alertBox.css");

        declineButton.setOnMouseClicked(e ->
                alertWindow.close()
        );

        acceptButton.setOnAction(e -> {
            alertWindow.close();
            currentStage.close();

            // stop all sounds only if quiting application
            SoundManager.stopAll();
        });

        initializeAlertWindow(scene, alertBoxTitle);
    }

    private void initializeAlertWindow(Scene scene, String title){
        alertWindow.setScene(scene);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(alertBoxMaxWidth);
        alertWindow.setMinHeight(alertBoxMaxHeight);
        alertWindow.setResizable(false);
        alertWindow.showAndWait();
    }
}
