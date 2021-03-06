package com.turboracer.models;

import com.turboracer.Main;

public class Point {

    private final double x;
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

    public double getXConverted() {
        return (Main.maxWidth/2 + x);
    }

    public double getYConverted() {
        return (Main.maxHeight/2 - y);
    }

    public static double unconvertX(double x) {
        return (x - Main.maxWidth/2);
    }

    public static double unconvertY(double y) {
        return (Main.maxHeight/2 - y);
    }

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
