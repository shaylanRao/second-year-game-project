package sample.controllers.game;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import sample.Main;
import sample.models.audio.SoundManager;
import sample.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController extends AbstractGameController {
    public static Raycaster raycaster;
    //TODO maybe merge AbstractGameController and GameController into one file
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //generate track lines
        raycaster = new Raycaster(pane);
        //display track on screen
        ArrayList<PathElement> outerPathElems = Main.track.getOuterPathElems();
        ArrayList<PathElement> innerPathElems = Main.track.getInnerPathElems();

        Path outerPath = new Path(outerPathElems);
        Path innerPath = new Path(innerPathElems);
        Line[] gates = Main.track.getGates();
        /*for (Point point : Main.track.getPowerupSpawns()) {
            Line line = new Line(point.getXConverted(), point.getYConverted(), point.getXConverted(), point.getYConverted());
            line.setStrokeWidth(5);
            pane.getChildren().add(line);
        }*/

        outerPath.setFill(Color.web("#444444"));
        innerPath.setFill(Color.web("#013220"));
        pane.getChildren().add(outerPath);
        pane.getChildren().add(innerPath);
        pane.getChildren().addAll(gates);
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

    public void backClicked(ActionEvent actionEvent) {
        SoundManager.play("button");
        SoundManager.stop("playBgm");
        SoundManager.play("bgm");
        System.out.println("back button pressed");
        Main.sceneManager.setPrevScene();
    }
}
