package sample.models;

import javafx.scene.shape.Rectangle;

public class ProjectionRectangle {
    private CollisionNode[] nodes;
    double centrex;
    double centrey;
    double[] topleft;
    double[] topright;
    double[] bottomleft;
    double[] bottomright;
    double[] tleft;
    double[] tright;
    double[] bleft;
    double[] bright;
    double width;
    double height;

    public ProjectionRectangle(double[] x, double[] y) {
        nodes = new CollisionNode[x.length];
        for(int i=0; i<x.length; i++) {
            nodes[i] = new CollisionNode(x[i], y[i]);
            //System.out.println("Point: "+nodes[i].x+", "+nodes[i].y);
        }
        //System.out.println(nodes.length);
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
        }
    }

    public ProjectionRectangle(PlayerCar rect, Rectangle re) {

        this.centrex = re.getX() - rect.getImage().getBoundsInLocal().getWidth()/2;
        this.width = rect.getImage().getBoundsInLocal().getWidth();
        this.height = rect.getImage().getBoundsInLocal().getHeight();
        this.centrey = re.getY() + rect.getImage().getBoundsInLocal().getHeight()/2;

        this.bottomright = new double[]{centrex + width / 2, centrey + height / 2};
        this.bottomleft = new double[]{centrex - width / 2, centrey + height / 2};
        this.topright = new double[]{centrex + width / 2, centrey - height / 2};
        this.topleft = new double[]{centrex - width / 2, centrey - height / 2};


        this.bright = GetPointRotated(bottomright[0],bottomright[1] , centrex, centrey, rect.getImage().getRotate());
        this.bleft = GetPointRotated(bottomleft[0], bottomleft[1], centrex, centrey, rect.getImage().getRotate());
        this.tright = GetPointRotated(topright[0], topright[1], centrex, centrey, rect.getImage().getRotate());
        this.tleft = GetPointRotated(topleft[0], topleft[1], centrex, centrey, rect.getImage().getRotate());
        double[] xs = new double[]{bright[0] ,bleft[0],tleft[0], tright[0]};
        double[] ys = new double[]{bright[1], bleft[1], tleft[1],tright[1]};
        nodes = new CollisionNode[4];
        for(int i=0; i<4; i++) {
            nodes[i] = new CollisionNode(xs[i], ys[i]);
            //System.out.println("Point: "+nodes[i].x+", "+nodes[i].y);
        }
        //System.out.println(nodes.length);
    }

    private double[] GetPointRotated(double X, double Y,double Xos,double Yos, double rot){
        double rotatedX = Xos + ((X - Xos)  * Math.cos(Math.toRadians(rot))) - ((Y - Yos) * Math.sin(Math.toRadians(rot)));
        double rotatedY = Yos + ((X - Xos)  * Math.sin(Math.toRadians(rot))) + ((Y - Yos) * Math.cos(Math.toRadians(rot)));
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
