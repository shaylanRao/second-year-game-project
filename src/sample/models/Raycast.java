package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Raycast {
    private Point pos;
    private Point dir;

    public Raycast(Point pos, Point dir) {
        this.pos = pos;
        this.dir = dir;
    }

    //TODO consider renaming this e.g., toLine() might be more descriptive
    public Line show() {
        Line line = new Line(pos.getXConverted(), pos.getYConverted(), pos.getXConverted()+dir.getX()*100, pos.getYConverted()+dir.getY()*100);
        return line;
    }

    public Point cast(Line boundary) {
        //these constants are just to make the equation clearer
        final double x1 = Point.unconvertX(boundary.getStartX());
        final double y1 = Point.unconvertY(boundary.getStartY());
        final double x2 = Point.unconvertX(boundary.getEndX());
        final double y2 = Point.unconvertY(boundary.getEndY());

        final double x3 = this.pos.getX();
        final double y3 = this.pos.getY();
        final double x4 = this.pos.getX()+this.dir.getX();
        final double y4 = this.pos.getY()+this.dir.getY();

        final double denominator = (x1-x2) * (y3-y4) - (y1-y2) * (x3-x4);
        //if value of denominator is zero then the ray is parallel to the boundary
        if (denominator == 0) {
            return null;
        }
        final double t = ((x1-x3) * (y3-y4) - (y1-y3) * (x3-x4)) / denominator;
        final double u = -((x1-x2) * (y1-y3) - (y1-y2) * (x1-x3)) / denominator;
        if (t>0 && t<1 && u>0) {
            //System.out.println("ray intersects");
            Point point = new Point(x1+t*(x2-x1), y1+t*(y2-y1));
            return point;
        }
        return null;
    }
}
