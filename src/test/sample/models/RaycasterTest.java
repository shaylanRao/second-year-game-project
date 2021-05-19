package sample.models;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RaycasterTest {

    private static Raycaster raycaster;

    @BeforeClass
    public static void init() {
        Pane pane = new Pane();
        Car car = new Car(pane, Settings.VehicleType.VEHICLE1);
        raycaster = new Raycaster(pane ,car);
    }

    @Test
    public void castRays() {
        //test with no boundaries
        //all the distances should be zero as none of the rays will intersect
        ArrayList<Line> emptyList = new ArrayList();
        double[] expected = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
        double[] actual = raycaster.castRays(emptyList, false);
        assertArrayEquals(expected, actual, 0);
    }

    @Test
    public void testRot() {
        //test to see if rotation is converted correctly
        //testd with various input values
        raycaster.setRot(0);
        double converted = raycaster.getRot();
        assertEquals(3.14159, converted, 0.1);

        raycaster.setRot(180);
        converted = raycaster.getRot();
        assertEquals(0, converted, 0);

        raycaster.setRot(-95);
        converted = raycaster.getRot();
        assertEquals(-1.48353, converted, 0.1);

        raycaster.setRot(1000);
        converted = raycaster.getRot();
        assertEquals(-1.74533, converted, 0.1);
    }
}