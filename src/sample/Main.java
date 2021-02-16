package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage stageName;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load font here instead of CSS to avoid weird path errors
        Font.loadFont(getClass().getResourceAsStream("/sample/resources/fonts/VT323/VT323-Regular.ttf"), 16);

        final int maxWidth = 1280;
        final int maxHeight = 720;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
        Parent root = (Parent) loader.load();

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(new Scene(root, maxWidth, maxHeight));
        primaryStage.setResizable(true);
        primaryStage.show();



        try {
            MainScreen mainScreen =  (MainScreen) loader.getController();
            mainScreen.setCurrentStage(primaryStage);
        } catch (Exception e) {
            System.out.println("Inside main");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
