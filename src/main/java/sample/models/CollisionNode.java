package sample.models;

public class CollisionNode {
    public double x;
    public double y;

    public CollisionNode(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(CollisionNode node){
        // Dot Product between this vector and the passed one
        return x*node.x + y*node.y;
    }

    public CollisionNode normal() {
        // The normal of this vector
        return new CollisionNode(-1*y, x);
    }

    public CollisionNode normalize() {
        // The unit vector of this one
        double d = Math.sqrt(x*x+y*y);
        if(d == 0) {
            d = 1;
        }
        return new CollisionNode(x/d,y/d);
    }
}
