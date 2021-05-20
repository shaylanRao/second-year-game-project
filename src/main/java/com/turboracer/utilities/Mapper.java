package com.turboracer.utilities;

public class Mapper {
    static public final float map(float value,
                                  float istart,
                                  float istop,
                                  float ostart,
                                  float ostop) {
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }
}