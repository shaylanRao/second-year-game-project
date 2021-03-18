package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.controllers.audio.SoundManager;
import sample.controllers.ui.MainScreen;
import sample.models.SceneManager;
import sample.models.Settings;
import sample.models.TrackBuilder;

public class Main extends Application {

    public static final int maxWidth = 1280;
    public static final int maxHeight = 720;

    // TODO: is this the right way to do this
    public static SceneManager sceneManager;
    public static SoundManager soundManager;
    public static Settings settings;
    public static FXMLLoader loader;

    public static TrackBuilder trackBuilder;

    /**
     * @param primaryStage - main stage onto which scenes are added
     * */

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load font here instead of CSS to avoid weird path errors
        Font.loadFont(getClass().getResourceAsStream("/sample/resources/fonts/VT323/VT323-Regular.ttf"), 16);


        loader = new FXMLLoader(getClass().getResource("views/mainScreen.fxml"));
        Parent root = loader.load();
        MainScreen mainScreen = (MainScreen) loader.getController();
        Scene currentScene = new Scene(root, maxWidth, maxHeight);

        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(currentScene);
        primaryStage.setResizable(false);
        primaryStage.show();

        try {


            soundManager = new SoundManager(); // construct SoundManager
            soundManager.Init();               // Initialize Sound
            soundManager.configureSounds();

            mainScreen.setCurrentStage(primaryStage);

            sceneManager = new SceneManager(primaryStage);
            settings = new Settings();


            sceneManager.setCurrentScene(currentScene);

            trackBuilder = new TrackBuilder();
        } catch (Exception e) {
            System.out.println("Inside main");
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
