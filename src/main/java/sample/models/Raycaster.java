package sample.models;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import sample.Main;

import java.util.ArrayList;

/**
 * Represents a raycaster, made up of indivdual raycasts.
 * @see Raycast
 */
public class Raycaster {


    private final Car car;
    public Point pos;
    private double rot;
    /**
     * The individual raycasts constituting the raycaster.
     */
    private final Raycast[] rays = new Raycast[8];
    private final Pane pane;
    /**
     * The line representations of the raycast vectors
     */
    private final ArrayList<Line> rayLines = new ArrayList<>();

    private final ArrayList<Rectangle> rayRect = new ArrayList<>();

    public double carHeight;
    public double carWidth;


    //this is the list of angles in degrees that the rays should be casted at (from the car)
    /**
     * The angles (in degrees) around the car in which the rays are casted.
     */
    private final double[] directions = {
            0,
            23,
            -23,
            90,
            -90,
            158,
            -158,
            //todo maybe fix this shit
            179.999999999,
    };


    public Raycaster(Pane pane, Car car) {
        this.pos = new Point(0, 0);
        this.pane = pane;
        this.car = car;
        this.carHeight = car.getCarHeightWidth()[0];
        this.carWidth = car.getCarHeightWidth()[1];
        //this.rectRot = 0;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    public ArrayList<Rectangle> getRayRect() {
        return rayRect;
    }

    private double rectRot;

    /**
     * Used in testing
     * @return the rotation of the raycaster
     */
    public double getRot() {
        return rot;
    }

    public void setRot(double rotation) {
        this.rectRot = rotation;
        this.rot = convertRot(rotation);
    }


    /** Converts from degrees to radians in the range [-pi,pi]
     * @param rotation the rotation in degrees
     * @return the converted rotation
     */
    //converts from degrees to radians in the range -pi,pi
    private double convertRot(double rotation) {
        rotation = 180 - rotation;
        while (rotation > 180) {
            rotation -= 360;
        }
        while (rotation < -180) {
            rotation += 360;
        }
        return Math.toRadians(rotation);
    }

    //    public double[] carColl(ArrayList<Rectangle> carRect, boolean showLines) {
//
//
//    }


    /**
     * Casts the rays in the specified directions and calculates the distances between the centre of the raycaster and the points of intersection.
     * @param boundaries the boundaries to test intersection with
     * @param showLines if true, it generates a list of Line objects so that they can be rendered.
     * @return an array of the measured distances for each ray being cast. N.b. If a given ray does not intersect then its respective element in the array will be zero.
     * @see Raycaster#directions
     */
    public double[] castRays(ArrayList<Line> boundaries, boolean showLines) {
        //create 8 rays
        for (int i = 0; i < 8; i++) {
            double cos = Math.cos(rot + convertRot(directions[i]));
            double sin = Math.sin(rot + convertRot(directions[i]));
            rays[i] = new Raycast(pos, new Point(cos, sin));
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
        for (int i = 0; i < 8; i++) {
            Point closest = null;
            double record = Integer.MAX_VALUE;
            for (Line boundary : boundaries) {
                Point point = this.rays[i].cast(boundary);
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
                    rayLines.add(line);
                }

                distances[counter] = Point.distance(pos, closest);
            }
            counter++;
        }

        for (int i = 8; i < 8; i++) {
            Line line = new Line(pos.getXConverted(), pos.getYConverted(), 100, 100);
            rayLines.add(line);
        }

        this.carSquare();

        if (showLines && !Main.settings.getPlayMode().equals(Settings.PlayMode.AI_TRAIN)) {
            pane.getChildren().addAll(rayLines);
            pane.getChildren().addAll(rayRect);

        }


        return distances;
    }

    private void carSquare() {
        final Rectangle rect1 = new Rectangle(car.getImage().getLayoutX(), car.getImage().getLayoutY(), carWidth, carHeight);
        rect1.setRotate(rectRot);
        rect1.setFill(Color.TRANSPARENT);
        rect1.setStroke(Color.BLUEVIOLET);

        rayRect.add(rect1);

    }


}
