package sample.controllers.game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.Main;
import sample.models.Game;
import sample.models.Point;
import sample.models.Raycaster;
import sample.models.TrackBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RandomTrackScreen extends AbstractGameController {
    public static Raycaster raycaster;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //generate track lines
        //TODO consider making BuildTrack() a static method
        Main.trackBuilder.BuildTrack();
        raycaster = new Raycaster(pane);
        //display track on screen
        pane.getChildren().addAll(Main.trackBuilder.getTrackLines());
/*        for (Point point : Main.trackBuilder.getPowerupSpawns()) {
            Line line = new Line(point.getXConverted(), point.getYConverted(), point.getXConverted(), point.getYConverted());
            line.setStrokeWidth(5);
            pane.getChildren().add(line);
        }*/
        try {
            game = new Game();
            game.initialiseGameObjects(pane);
            // starts the game
            game.gameLoop();

        } catch (Exception ex) {
            System.out.println("Error when initializing ");
            ex.printStackTrace();
        }
    }
}
