package sample.controllers.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import sample.Main;
import sample.ai.TrainCar;
import sample.models.audio.SoundManager;
import sample.models.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController extends AbstractGameController {
    public static Raycaster raycaster;

    @FXML
    private Button trainButton;

    //TODO maybe merge AbstractGameController and GameController into one file
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //generate track lines
        raycaster = new Raycaster(pane, playerCar);
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
            game = new Game(pane);
            // starts the game
            if (!Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
                game.gameLoop();
                trainButton.setVisible(false);
            }

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

    public void trainClicked(ActionEvent actionEvent) {
        TrainCar.setGame(game);
        TrainCar.train(500);
    }
}
