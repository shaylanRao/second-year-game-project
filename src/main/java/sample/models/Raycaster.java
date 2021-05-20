package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Raycaster {


    public Point pos;
    private final Car car;

    public double getRot() {
        return rot;
    }

    private double rot;
    private final Raycast[] rays = new Raycast[8];
    private final Pane pane;
    private final ArrayList<Line> rayLines = new ArrayList<>();

    private final ArrayList<Rectangle> rayRect = new ArrayList<>();

    public double carHeight;
    public double carWidth;

    //this is the list of angles in degrees that the rays should be casted at (from the car)
    private final double[] directions = {
            0,
            23,
            -23,
            90,
            -90,
            158,
            -158,
            180
    };


    public Raycaster(Pane pane, Car car) {
        pos = new Point(0, 0);
        this.pane = pane;
        this.car = car;
        carHeight = this.car.getCarHeightWidth()[0];
        carWidth = this.car.getCarHeightWidth()[1];

    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public ArrayList<Rectangle> getRayRect() {
        return rayRect;
    }

    private double rectRot;

    public void setRot(double rotation) {
        rectRot = rotation;
        rot = convertRot(rotation);
    }

    //converts from degrees to radians in the range -pi,pi
    private double convertRot(double rotation) {
        rotation = 180 - rotation;
        while (rotation > 180) {
            rotation-=360;
        }
        while (rotation < -180) {
            rotation +=360;
        }
        return Math.toRadians(rotation);
    }

    //    public double[] carColl(ArrayList<Rectangle> carRect, boolean showLines) {
//
//
//    }


    public double[] castRays(ArrayList<Line> boundaries, boolean showLines) {
        //create 8 rays
        for (int i=0; i<8; i++) {
            double cos = Math.cos(rot+convertRot(directions[i]));
            double sin = Math.sin(rot+convertRot(directions[i]));
            rays[i] = new Raycast(pos, new Point(cos,sin));
        }


        if (showLines) {
            pane.getChildren().removeAll(rayLines);
            pane.getChildren().removeAll(rayRect);
            rayRect.clear();
            rayLines.clear();
        }


        int counter = 0;
        double[] distances = new double[8];
        //actually makes lines??
        for  (int i=0; i < 8; i++){
            Point closest = null;
            double record = Integer.MAX_VALUE;
            for (Line boundary : boundaries) {
                Point point = rays[i].cast(boundary);
                if (point != null) {
                    double distance = Point.distance(pos, point);
                    if (distance < record) {
                        record = distance;
                        closest = point;
                    }
                }
            }

            if (closest != null) {
                if (showLines) {
                    Line line = new Line(pos.getXConverted(), pos.getYConverted(), closest.getXConverted(), closest.getYConverted());
                    line.setStroke(Color.TRANSPARENT);
                    rayLines.add(line);
                }

                distances[counter] = Point.distance(pos, closest);
            }
            counter++;
        }

        for (int i = 8; i < 8; i++){
            Line line = new Line(pos.getXConverted(), pos.getYConverted(), 100, 100);
            rayLines.add(line);
        }

        carSquare();

        if (showLines) {
            pane.getChildren().addAll(rayLines);
            pane.getChildren().addAll(rayRect);
        }



        return distances;
    }






    private void carSquare(){
        final Rectangle rect1 = new Rectangle(car.getImage().getLayoutX(), car.getImage().getLayoutY(), carHeight, carWidth);
        rect1.setRotate(rectRot);
        rect1.setFill(Color.TRANSPARENT);
        rect1.setStroke(Color.TRANSPARENT);

        rayRect.add(rect1);

    }


}