package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.ExtraBits.AlertBox;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final int maxWidth = 1280;
        final int maxHeight = 720;

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(new Scene(root, maxWidth, maxHeight));
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
