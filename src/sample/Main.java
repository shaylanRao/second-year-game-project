package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    static final int maxWidth = 1280;
    static final int maxHeight = 720;
    static SceneController sceneController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load font here instead of CSS to avoid weird path errors
        Font.loadFont(getClass().getResourceAsStream("/sample/resources/fonts/VT323/VT323-Regular.ttf"), 16);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/mainScreen.fxml"));
        Parent root = loader.load();

        Scene currentScene = new Scene(root, maxWidth, maxHeight);

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(currentScene);
        primaryStage.setResizable(true);
        primaryStage.show();



        try {
            MainScreen mainScreen =  loader.getController();
            mainScreen.setCurrentStage(primaryStage);

            sceneController = new SceneController(primaryStage);
            sceneController.setCurrentScene(currentScene);
        } catch (Exception e) {
            System.out.println("Inside main");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
