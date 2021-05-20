package sample.models;

import javafx.scene.shape.Rectangle;

public class ProjectionRectangle {
    private final CollisionNode[] nodes;
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


    public ProjectionRectangle(PlayerCar rect, Rectangle re) {

        centrex = re.getX() - rect.getImage().getBoundsInLocal().getWidth()/2;
        width = rect.getImage().getBoundsInLocal().getWidth();
        height = rect.getImage().getBoundsInLocal().getHeight();
        centrey = re.getY() + rect.getImage().getBoundsInLocal().getHeight()/2;

        bottomright = new double[]{centrex + width / 2, centrey + height / 2};
        bottomleft = new double[]{centrex - width / 2, centrey + height / 2};
        topright = new double[]{centrex + width / 2, centrey - height / 2};
        topleft = new double[]{centrex - width / 2, centrey - height / 2};


        bright = GetPointRotated(bottomright[0],bottomright[1] , centrex, centrey, rect.getImage().getRotate());
        bleft = GetPointRotated(bottomleft[0], bottomleft[1], centrex, centrey, rect.getImage().getRotate());
        tright = GetPointRotated(topright[0], topright[1], centrex, centrey, rect.getImage().getRotate());
        tleft = GetPointRotated(topleft[0], topleft[1], centrex, centrey, rect.getImage().getRotate());
        double[] xs = new double[]{bright[0] ,bleft[0],tleft[0], tright[0]};
        double[] ys = new double[]{bright[1], bleft[1], tleft[1],tright[1]};
        nodes = new CollisionNode[4];
        for(int i=0; i<4; i++) {
            nodes[i] = new CollisionNode(xs[i], ys[i]);
        }
    }

    private double[] GetPointRotated(double X, double Y,double Xos,double Yos, double rot){
        double rotatedX = Xos + ((X - Xos)  * Math.cos(Math.toRadians(rot))) - ((Y - Yos) * Math.sin(Math.toRadians(rot)));
        double rotatedY = Yos + ((X - Xos)  * Math.sin(Math.toRadians(rot))) + ((Y - Yos) * Math.cos(Math.toRadians(rot)));
        double[] p = new double[]{rotatedX, rotatedY};
        return p;
    }

    /**
     * Return the normal of every edge of the rectangle.
     *
     * @return CollisionNode[]
     */
    public CollisionNode[] getAxes() {
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

    /**
     * Get CollisionNode at index i.
     *
     * @return CollisionNode
     */
    public CollisionNode getNode(int i, CollisionNode axis) {
        return nodes[i];
    }

    /**
     * Get the number of nodes in the polygon.
     *
     * @return int
     */
    public int getNumOfNodes() {
        return nodes.length;
    }
}
