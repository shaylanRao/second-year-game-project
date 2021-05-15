package sample.utilities;

public class Mapper {
    /**
     * Converts a value in a given range to a value in a new range.
     *
     * @param value  the original value
     * @param istart the lower bound of the input range
     * @param istop  the upper bound of the input range
     * @param ostart the lower bound of the output range
     * @param ostop  the upper bound of the output range
     * @return the value in the new range
     */
    public static float map(float value, float istart, float istop, float ostart, float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}
