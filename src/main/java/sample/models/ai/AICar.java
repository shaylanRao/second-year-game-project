package sample.models.ai;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Main;
import sample.models.Car;
import sample.models.Point;
import sample.models.Settings;

import java.util.Arrays;

public class AICar extends Car {
    /**
     * {@inheritDoc}
     */
    public AICar(Pane gameBackground, Settings.VehicleType vehicleType) {
        super(gameBackground, generateCarImageView(vehicleType), vehicleType);
    }

    public void setDistances(double[] distances) {
        this.distances = distances;
    }

    double[] distances;

    public void chooseAction() {

        System.out.println(Arrays.toString(distances));
        //array of distances of the 3 front-facing rays
        double frontDistances[] = new double[3];
        for (int i=5; i<8; i++) {
            frontDistances[i-5] = distances[i];
        }
        int maxIndex = argmax(frontDistances);
        switch (maxIndex) {
            case 0:
                //left
                System.out.println("turn left");
                setTurnRight(false);
                setTurnLeft(true);
                break;
            case 1:
                //right
                System.out.println("turn right");
                setTurnLeft(false);
                setTurnRight(true);
                break;
            case 2:
                //front
                //do nothing as car is always set to be going forwards
                System.out.println("do nothing");
                setTurnRight(false);
                setTurnLeft(false);
                break;
            default:
                System.out.println("Error - incorrect index");
        }
    }

    private int argmax(double[] arr) {
        double max = Double.MIN_VALUE;
        int arg = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                arg = i;
            }
        }
        return arg;
    }
}
