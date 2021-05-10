package sample.models;

import sample.Main;

import java.util.Objects;

/**
 * Utility class, represents a point in two dimensions.
 */
public class Point {

    /**
     * The x coordinate
     */
    private double x;

    /**
     * The y coordinate
     */
    private double y;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {this.x = x;}

    /**
     * Converts from coordinate system with origin at centre of screen to the coordinate system that JavaFX uses (origin at top-left of screen)
     * @return the converted x coordinate.
     */
    public double getXConverted() {
        return (Main.maxWidth/2 + x);
    }

    /**
     * Converts from coordinate system with origin at centre of screen to the coordinate system that JavaFX uses (origin at top-left of screen)
     * @return the converted y coordinate.
     */
    public double getYConverted() {
        return (Main.maxHeight/2 - y);
    }


    /**
     * Helper method to convert from JavaFX coordinate system to coordinate system with origin at the centre of the screen.
     * Reverses the operation of getXConverted().
     * @param x the x coordinate to unconvert
     * @return the unconverted x coordinate
     */
    public static double unconvertX(double x) {
        return (x - Main.maxWidth/2);
    }

    /**
     * Helper method to convert from JavaFX coordinate system to coordinate system with origin at the centre of the screen.
     * Reverses the operation of getXConverted().
     * @param y the y coordinate to unconvert
     * @return the unconverted y coordinate
     */
    public static double unconvertY(double y) {
        return (Main.maxHeight/2 - y);
    }


    /**
     * Calculates the euclidean distance between two points.
     * @param a The Point a
     * @param b The Point b
     * @return the euclidean distance between the two points
     */
    public static double distance(Point a, Point b) {
        //these constants are just to make the equation clearer
        final double x1 = a.getX();
        final double y1 = a.getY();
        final double x2 = b.getX();
        final double y2 = b.getY();

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

}
