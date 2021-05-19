package sample.models;

public class Projection {
    private final double min;
    private final double max;

    public Projection(double min, double max) {
        // Create the projection with it's two values: min, max
        this.min = min;
        this.max = max;
    }

    public boolean overlap(Projection p2) {
        // Check if this projection overlaps with the passed one
        return (!(p2.max < min || max < p2.min));
    }
}
