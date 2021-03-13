package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Raycaster {
    public void setPos(Point pos) {
        this.pos = pos;
    }

    private Point pos;
    private Raycast[] rays = new Raycast[8];

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    private Pane pane;
    private ArrayList<Line> rayLines = new ArrayList<>();
    private Point directions[] = {
                new Point(0,1),
                new Point(1,1),
                new Point(1,0),
                new Point(1,-1),
                new Point(0,-1),
                new Point(-1,-1),
                new Point(-1,0),
                new Point(-1,1),
    };
    public Raycaster() {
        pos = new Point(0, 0);
    }

    //TODO consider renaming this e.g., toLine() might be more descriptive
    public Line[] show() {
        Line lines[] = new Line[8];
        for (int i=0; i<8; i++) {
            lines[i] = rays[i].show();
        }
        return lines;
    }

    public void castRays(ArrayList<Line> boundaries, boolean showLines) {
        //create 8 rays
        for (int i=0; i<8; i++) {
            rays[i] = new Raycast(pos, directions[i]);
        }
        pane.getChildren().removeAll(rayLines);
        rayLines.clear();
        for (Raycast ray: rays) {
            Point closest = null;
            double record = Integer.MAX_VALUE;
            for (Line boundary : boundaries) {
                Point point = ray.cast(boundary);
                if (point!=null) {
                    //System.out.println(point.toString());
                    double distance = Point.distance(pos, point);
                    if (distance < record) {
                        record = distance;
                        closest = point;
                    }
                }
            }
            if (closest!=null) {
                //System.out.println(closest.toString());
                Line line = new Line(pos.getXConverted(), pos.getYConverted(), closest.getXConverted(), closest.getYConverted());
                rayLines.add(line);
            }
        }
        pane.getChildren().addAll(rayLines);
    }

    public Point getPos() {
        return this.pos;
    }
}
