package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stageName;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stageName = primaryStage;

        final int maxWidth = 1280;
        final int maxHeight = 720;

        Parent root = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));

        Controller controller = new Controller();

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(new Scene(root, maxWidth, maxHeight));
        primaryStage.setResizable(true);
        primaryStage.show();

        controller.setCurrentStage(stageName);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
