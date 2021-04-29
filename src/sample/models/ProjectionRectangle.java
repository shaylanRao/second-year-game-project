package sample.models;

import javafx.scene.shape.Rectangle;

public class ProjectionRectangle {
    private CollisionNode[] nodes;

    public ProjectionRectangle(double[] x, double[] y) {
        nodes = new CollisionNode[x.length];
        //System.out.println("New Shape");
        for(int i=0; i<x.length; i++) {
            nodes[i] = new CollisionNode(x[i], y[i]);
            System.out.println("Point: "+nodes[i].x+", "+nodes[i].y);
        }
        System.out.println(nodes.length);
    }

    public ProjectionRectangle(Rectangle rect) {
        double tleftx = rect.getX();
        double tlefty = rect.getY();
        double width = rect.getWidth();
        double height = rect.getHeight();
        double[] xs = new double[]{tleftx,tleftx - width,tleftx, tleftx - width};
        double[] ys = new double[]{tlefty - height, tlefty, tlefty,tlefty - height};
        nodes = new CollisionNode[4];
        for(int i=0; i<4; i++) {
            nodes[i] = new CollisionNode(xs[i], ys[i]);
            //System.out.println("Point: "+nodes[i].x+", "+nodes[i].y);
        }
        //System.out.println(nodes.length);
    }

    public ProjectionRectangle(PlayerCar rect) {
        double tleftx = rect.getImage().getLayoutX();
        //System.out.println(tleftx);
        double tlefty = rect.getImage().getLayoutY();
        //System.out.println(tlefty);
        double width = rect.getImage().getBoundsInLocal().getWidth();
        //System.out.println(width);
        double height = rect.getImage().getBoundsInLocal().getHeight();
        //System.out.println(height);
        double f = rect.getImage().getRotate() % 45;
        double d = rect.getImage().localToScreen(rect.getImage().getBoundsInLocal()).getMaxX();
        //System.out.println(d);
        double tlx = tleftx - width;
        double tly = tlefty + height;
        double[] xs = new double[]{tlx ,tlx,tleftx, tleftx};
        double[] ys = new double[]{tly, tlefty, tly,tlefty};
        //double[] xs = GetPointRotated();
        //double[] ys = GetPointRotated();
        nodes = new CollisionNode[4];
        for(int i=0; i<4; i++) {
            nodes[i] = new CollisionNode(xs[i], ys[i]);
            //System.out.println("Point: "+nodes[i].x+", "+nodes[i].y);
        }
        //System.out.println(nodes.length);
    }

    private double[] GetPointRotated(double X, double Y,double Xos,double Yos, double rot){
// Xos, Yos // the coordinates of your center point of rect
// R      // the angle you wish to rotate

//The rotated position of this corner in world coordinates
        double rotatedX = X + (Xos  * Math.cos(Math.toRadians(rot))) - (Yos * Math.sin(Math.toRadians(rot)));
        double rotatedY = Y + (Xos  * Math.sin(Math.toRadians(rot))) + (Yos * Math.cos(Math.toRadians(rot)));
        double[] p = new double[]{rotatedX, rotatedY};
        return p;
    }

    public CollisionNode[] getAxes() {
        // Return the normal of every edge of the polygon
        CollisionNode[] axes = new CollisionNode[nodes.length];
        for(int i = 0; i < nodes.length; i++) {
            // Get the vector of the edge
            CollisionNode vector = new CollisionNode(nodes[i].x - nodes[i+1==nodes.length ? 0:i+1].x,
                    nodes[i].y - nodes[i+1==nodes.length ? 0:i+1].y);
            // Get the normal of the unit vector of the edge
            axes[i] = vector.normal().normalize();
        }
        return axes;
    }

    public CollisionNode getNode(int i, CollisionNode axis) {
        // Get CollisionNode at index i
        return nodes[i];
    }

    public int getNumOfNodes() {
        // Get the number of nodes in the Polygon
        return nodes.length;
    }
}
