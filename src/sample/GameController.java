package sample;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class GameController {
    private Car car; //car controller has an instance of Car
    boolean running, goNorth, goSouth, goEast, goWest;

    Scene scene = Main.sceneController.getCurrentScene();

    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:    //car.accelerate(); break;
            case DOWN:  //car.decelerate() break;
            case LEFT:  //car.turnLeft();  = true; break;
            case RIGHT: //car.turnRight();  = true; break;
            //case SHIFT:
            }

    }

    public void onKeyReleased(KeyEvent keyEvent) {
        //TODO maybe fill in
    }

}
