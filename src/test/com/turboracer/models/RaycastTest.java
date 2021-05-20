package com.turboracer.models;

import javafx.scene.shape.Line;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaycastTest {

    @Test
    void cast() {
        //create a vertical line
        Line testBoundary = new Line(100, -50, 100, 50);
        //create a raycast originating at 0,0 going in the direction (1, 0) (i.e., horizontal to the right)
        Raycast raycast = new Raycast(new Point(0, 0), new Point(1, 0));
        //raycast.cast() should return the point where the raycast intersects with testBoundary
        Point intersection = raycast.cast(testBoundary);
        assertNotNull(intersection);
        assertEquals(intersection, new Point(100, 0));

        //create a raycast with the same origin as before but in the vertical direction
        raycast = new Raycast(new Point(0, 0), new Point(0, 1));
        //this should not intersect
        assertNull(raycast.cast(testBoundary));

        //testing in negative direction
        testBoundary = new Line(-100, -100, -100, 100);
        raycast = new Raycast(new Point(50, 50), new Point(-1, 0));
        intersection = raycast.cast(testBoundary);
        assertNotNull(intersection);
        assertTrue(intersection.equals(new Point(-100, 50)));

    }
}