package com.turboracer.controllers.game;

import com.turboracer.models.Game;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import com.turboracer.Main;
import com.turboracer.models.audio.SoundManager;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RandomTrackScreen extends AbstractGameController {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //generate track lines

        //display track on screen
        ArrayList<PathElement> outerPathElems = Main.track.getOuterPathElems();
        ArrayList<PathElement> innerPathElems = Main.track.getInnerPathElems();

        Path outerPath = new Path(outerPathElems);
        Path innerPath = new Path(innerPathElems);
        Line[] gates = Main.track.getGates();

        outerPath.setFill(Color.web("#444444"));
        innerPath.setFill(Color.web("#106111"));
        pane.getChildren().add(outerPath);
        pane.getChildren().add(innerPath);
        pane.getChildren().addAll(Main.track.getFinWhite());
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

    public void backClicked() {
        SoundManager.play("button");
        SoundManager.stop("playBgm");
        SoundManager.play("bgm");
        System.out.println("back button pressed");
        Main.sceneManager.setPrevScene();
    }
}
