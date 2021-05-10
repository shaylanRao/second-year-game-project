package sample.models;

import javafx.scene.shape.Line;

/**
 * Represents a single raycast. Used to measure distance from the car to a given object in a given direction.
 * @see Raycaster
 */
public class Raycast {
    /**
     * The origin of the raycast
     * */
    private final Point pos;


    /**
     * The direction of the raycast (2D vector representation)
     */
    private final Point dir;

    public Raycast(Point pos, Point dir) {
        this.pos = pos;
        this.dir = dir;
    }

    /** Produces a line segment representation of the raycast vector.
     * @return A line segment representation of the raycast vector
     */
    public Line show() {
        return new Line(pos.getXConverted(), pos.getYConverted(), pos.getXConverted()+dir.getX()*100, pos.getYConverted()+dir.getY()*100);
    }

    /** Calculates the point of intersection between the raycast and the boundary.
     * @param boundary the boundary to test intersection with the raycast
     * @return The point of intersection between the raycast and the boundary or null if there is no intersection
     */
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
            return new Point(x1+t*(x2-x1), y1+t*(y2-y1));
        }
        return null;
    }
}
