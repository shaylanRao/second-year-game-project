package sample.models;

public class Projection {
    private final double min;
    private final double max;

    public Projection(double min, double max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Check if this projection overlaps with the passed projection.
     * @param p2 the passed Projection
     * @return boolean
     */
    public boolean overlap(Projection p2) {
        return (!(p2.max < min || max < p2.min));
    }
}
