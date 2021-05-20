package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.models.audio.SoundManager;
import sample.controllers.ui.MainScreen;
import sample.models.SceneManager;
import sample.models.Settings;
import sample.models.Track;

public class Main extends Application {

    public static int maxWidth;
    public static int maxHeight;

    public static SceneManager sceneManager;
    public static Settings settings;
    public static FXMLLoader loader;

    public static Track track;

    /**
     * @param primaryStage - main stage onto which scenes are added
     * */

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load font here instead of CSS to avoid weird path errors
        Font.loadFont(getClass().getResourceAsStream("/fonts/VT323-Regular.ttf"), 16);


        loader = new FXMLLoader(getClass().getResource("/views/mainScreen.fxml"));
        Parent root = loader.load();
        MainScreen mainScreen = loader.getController();
        maxWidth = (int) Screen.getPrimary().getBounds().getWidth();
        maxHeight = (int) Screen.getPrimary().getBounds().getHeight();
        Scene currentScene = new Scene(root, maxWidth, maxHeight);

        //primaryStage.setFullScreen(true);
        primaryStage.setTitle("RACING GAME 2D");
        primaryStage.setScene(currentScene);
        primaryStage.setResizable(true);
        primaryStage.show();

        try {


            SoundManager.Init();               // Initialize Sound
            SoundManager.configureSounds();

            mainScreen.setCurrentStage(primaryStage);

            sceneManager = new SceneManager(currentScene);
            settings = new Settings();


            sceneManager.setCurrentRoot(root);
            track = new Track();
        } catch (Exception e) {
            System.out.println("error in main.java");
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop() {
        //System.exit(0);
    }

}
