package sample.ExtraBits;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;

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

    public AlertBox(Stage currentStage, String alertBoxDescriptionText, String acceptButtonText, String declineButtonText,String alertBoxTitle) {
        this.currentStage = currentStage;
        this.alertBoxDescriptionText = alertBoxDescriptionText;
        this.acceptButtonText = acceptButtonText;
        this.declineButtonText = declineButtonText;
        this.alertBoxTitle = alertBoxTitle;
    }

    public void displayPrompt(){
        int alertBoxMaxWidth = 320;
        int alertBoxMaxHeight = 180;

        alertWindow = new Stage();

        alertWindow.initModality(Modality.APPLICATION_MODAL);

        alertBoxDescription = new Label(alertBoxDescriptionText);
        acceptButton = new Button(acceptButtonText);
        declineButton = new Button(declineButtonText);

        VBox layout = new VBox();
        layout.getChildren().addAll(alertBoxDescription, acceptButton,declineButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, alertBoxMaxWidth, alertBoxMaxHeight);

        scene.getStylesheets().add("sample/ExtraBits/stylesheet.css");

        declineButton.setOnMouseClicked(e ->
                alertWindow.close()
        );

        acceptButton.setOnAction(e -> {
                alertWindow.close();
                currentStage.close();
        });

        initializeAlertWindow(scene,alertBoxMaxWidth,alertBoxMaxHeight, alertBoxTitle);
    }

    private void initializeAlertWindow(Scene scene, int alertBoxMaxWidth, int alertBoxMaxHeight, String title){
        alertWindow.setScene(scene);
        alertWindow.setTitle(title);
        alertWindow.setMinWidth(alertBoxMaxWidth);
        alertWindow.setMinHeight(alertBoxMaxHeight);
        alertWindow.setResizable(false);
        alertWindow.showAndWait();
    }
}
