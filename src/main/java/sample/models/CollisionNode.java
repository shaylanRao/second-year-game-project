package sample.models;

public class CollisionNode {
    public double x;
    public double y;

    public CollisionNode(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the dot product between this vector and the passed vector as a double.
     *
     * @param node the CollisionNode to be calculated with
     * @return double
     */
    public double dot(CollisionNode node){
        return x*node.x + y*node.y;
    }

    /**
     * Returns the normal of this vector.
     *
     * @return CollisionNode
     */
    public CollisionNode normal() {
        // The normal of this vector
        return new CollisionNode(-1*y, x);
    }

    /**
     * Returns the unit vector of this vector.
     *
     * @return CollisionNode
     */
    public CollisionNode normalize() {
        double d = Math.sqrt(x*x+y*y);
        if(d == 0) {
            d = 1;
        }
        return new CollisionNode(x/d,y/d);
    }
}
