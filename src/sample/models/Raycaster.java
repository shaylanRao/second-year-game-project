package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Raycaster {


    private Point pos;
    private final PlayerCar playercar;

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
            180,
            23, -23,  158, -158
    };


    public Raycaster(Pane pane, PlayerCar playerCar) {
        this.pos = new Point(0, 0);
        this.pane = pane;
        this.playercar = playerCar;
        this.carHeight = playercar.getCarHeightWidth()[0];
        this.carWidth = playercar.getCarHeightWidth()[1];

    }

    public void setPos(Point pos) {
        this.pos = pos;
    }

    private double rectRot;

    public void setRot(double rotation) {
        this.rectRot = rotation;
        this.rot = convertRot(rotation);
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
//                line.setStartX(playerCar.getImage().getBoundsInParent().getMinX());
//                line.setStartY(playerCar.getImage().getBoundsInParent().getMinY());
//                line.setEndX(playerCar.getImage().getBoundsInParent().getMaxX());
//                line.setEndY(playerCar.getImage().getBoundsInParent().getMaxY());

                distances[counter] = Point.distance(pos, closest);
            }
            counter++;
        }

        for (int i = 8; i < 8; i++){
            Line line = new Line(pos.getXConverted(), pos.getYConverted(), 100, 100);
            rayLines.add(line);
        }

        this.carSquare();

        if (showLines) {
            pane.getChildren().addAll(rayLines);
            pane.getChildren().addAll(rayRect);
        }



        return distances;
    }






    private void carSquare(){
        final Rectangle rect1 = new Rectangle(playercar.getImage().getLayoutX(), playercar.getImage().getLayoutY(), carHeight, carWidth);
        rect1.setRotate(rectRot);
        rect1.setFill(Color.TRANSPARENT);
        rect1.setStroke(Color.BLUEVIOLET);

        rayRect.add(rect1);




        final Line line1 = new Line();
//        final Line line2 = new Line();
//        final Line line3 = new Line();
//        final Line line4 = new Line();
////        line.setStartX(10);
////        line.setStartY(10);
////        line.setEndX(100);
////        line.setEndY(100);
//
        line1.setStartX(this.playercar.getImage().getBoundsInParent().getMinX());
        line1.setStartY(this.playercar.getImage().getBoundsInParent().getMinY());
        line1.setEndX(this.playercar.getImage().getBoundsInParent().getMinX() + 2);
        line1.setEndY(this.playercar.getImage().getBoundsInParent().getMinY() + 2);
//
//
//        line2.setStartX(this.playercar.getImage().getBoundsInParent().getMaxX());
//        line2.setStartY(this.playercar.getImage().getBoundsInParent().getMaxY());
//        line2.setEndX(this.playercar.getImage().getBoundsInParent().getMaxX() + 2);
//        line2.setEndY(this.playercar.getImage().getBoundsInParent().getMaxY() + 2);
//
//        line3.setStartX(this.playercar.getImage().getBoundsInParent().getMinX());
//        line3.setStartY(this.playercar.getImage().getBoundsInParent().getMaxY());
//        line3.setEndX(this.playercar.getImage().getBoundsInParent().getMaxX() + 2);
//        line3.setEndY(this.playercar.getImage().getBoundsInParent().getMinY() + 2);

        line1.setStroke(Color.RED);
//        line2.setStroke(Color.YELLOW);
//        line3.setStroke(Color.BLUEVIOLET);
//
//        carBound.add(line1);
//        carBound.add(line2);
//
//
////        for (Line ray : carBound) {
////            rayLines.add(ray);
////        }
//
//        rayLines.add(line1);
//        rayLines.add(line2);
//        rayLines.add(line3);

    }


}