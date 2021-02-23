package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.controllers.ui.MainScreen;
import sample.models.SceneManager;
import sample.models.Settings;

public class Main extends Application {

    public static final int maxWidth = 1280;
    public static final int maxHeight = 720;

    // TODO: is this the right way to do this
    public static SceneManager sceneManager;
    public static Settings settings;

    @Override
    public void start(Stage primaryStage) throws Exception{
        // Load font here instead of CSS to avoid weird path errors
        Font.loadFont(getClass().getResourceAsStream("/sample/resources/fonts/VT323/VT323-Regular.ttf"), 16);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("views/mainScreen.fxml"));
        Parent root = loader.load();
        Scene currentScene = new Scene(root, maxWidth, maxHeight);

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(currentScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        try {
            MainScreen mainScreen =  new MainScreen();
            mainScreen.setCurrentStage(primaryStage);

            sceneManager = new SceneManager(primaryStage);
            settings = new Settings();

            sceneManager.setCurrentScene(currentScene);
        } catch (Exception e) {
            System.out.println("Inside main");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
