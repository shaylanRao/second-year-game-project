package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import sample.Main;

import java.util.ArrayList;

public class Raycaster {

    public void setPos(Point pos) {
        this.pos = pos;
    }

    private Point pos;

    public void setRot(double rotation) {
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

    private double rot;
    private Raycast[] rays = new Raycast[8];
    private Pane pane;
    private ArrayList<Line> rayLines = new ArrayList<>();

    //this is the list of angles in degrees that the rays should be casted at (from the car)
    private final double directions[] = {
            0,
            23,
            -23,
            90,
            -90,
            158,
            -158,
            180,
    };

    public Raycaster(Pane pane) {
        this.pos = new Point(0, 0);
        this.pane = pane;
    }

    public Line[] show() {
        Line lines[] = new Line[8];
        for (int i=0; i<8; i++) {
            lines[i] = rays[i].show();
        }
        return lines;
    }

    public double[] castRays(ArrayList<Line> boundaries, boolean showLines) {
        //create 8 rays
        for (int i=0; i<8; i++) {
            double cos = Math.cos(rot+convertRot(directions[i]));
            double sin = Math.sin(rot+convertRot(directions[i]));
            rays[i] = new Raycast(pos, new Point(cos,sin));
        }
        if (showLines) {
            pane.getChildren().removeAll(rayLines);
            rayLines.clear();
        }
        int counter = 0;
        double distances[] = new double[8];
        for (Raycast ray: rays) {
            Point closest = null;
            double record = Integer.MAX_VALUE;
            for (Line boundary : boundaries) {
                Point point = ray.cast(boundary);
                if (point != null) {
                    //System.out.println(point.toString());
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
        if (showLines) {
            pane.getChildren().addAll(rayLines);
        }
        return distances;
    }
}
