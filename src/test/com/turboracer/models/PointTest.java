package com.turboracer.models;

import org.junit.Test;
import com.turboracer.Main;

import static org.junit.Assert.*;

public class PointTest {



    @Test
    public void getXConverted() {
        Point point = new Point(0, 0);
        assertEquals(Main.maxWidth/2, point.getXConverted(), 0);

        Point point1 = new Point(10, 5);
        assertEquals(Main.maxWidth/2 + 10, point1.getXConverted(), 0);

        Point point2 = new Point(-200, -300);
        assertEquals(Main.maxWidth/2 -200, point2.getXConverted(), 0);
    }

    @Test
    public void getYConverted() {
        Point point = new Point(0 ,0);
        assertEquals(Main.maxHeight/2, point.getYConverted(), 0);

        Point point1 = new Point(25, 30);
        assertEquals(Main.maxHeight/2 - 30, point1.getYConverted(), 0);

        Point point2 = new Point(-100, -500);
        assertEquals(Main.maxHeight/2 + 500, point2.getYConverted(), 0);
    }

    @Test
    public void unconvertX() {
    }

    @Test
    public void unconvertY() {
    }

    @Test
    public void distance() {
    }

    @Test
    public void testToString() {
    }
}