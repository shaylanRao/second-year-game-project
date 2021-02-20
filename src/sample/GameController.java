package sample;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import static javafx.scene.input.KeyCode.UP;

public class GameController{
    private Car car; //car controller has an instance of Car
    boolean running, goNorth, goSouth, goEast, goWest;




    @FXML
    private Image carImage;

    Scene scene = Main.sceneController.getCurrentScene();

    public void onKeyPress(KeyEvent keyEvent) {
//        switch (keyEvent.getCode()) {
//            case UP:    //car.accelerate(); break;
//            case DOWN:  //car.decelerate() break;
//            case LEFT:  //car.turnLeft();  = true; break;
//            case RIGHT: //car.turnRight();  = true; break;
//            //case SHIFT:
//            }
//        public void handle(KeyEvent keyEvent) {
//            switch (keyEvent.getCode()) {
//                case UP:
//                    goNorth = false;
//                    break;
//                case DOWN:
//                    goSouth = false;
//                    break;
//                case LEFT:
//                    goWest  = false;
//                    break;
//                case RIGHT:
//                    goEast  = false;
//                    break;
//                case SHIFT:
//                    running = false;
//                    break;
//            }
//        }

        System.out.println("ehrhehehrhehrdfniohdiofb");
    }

    @FXML
    private void onKeyReleased(KeyEvent keyEvent) {
        //TODO maybe fill in
        System.out.println(keyEvent.toString());
    }
}
