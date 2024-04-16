package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class PointSpec {

    private Point point;
    private final int x = 12;
    private final int y = 21;

    @BeforeMethod
    public void beforeTest() {
        point = new Point(x, y);
    }

    public void whenInstantiatedThenXIsSet() {
        assertEquals(point.getX(), x);
    }

    public void whenInstantiatedThenYIsSet() {
        assertEquals(point.getY(), y);
    }

    public void whenSetXThenValueIsUpdated() {
        int newX = 30;
        point.setX(newX);
        assertEquals(point.getX(), newX);
    }

    public void whenSetYThenValueIsUpdated() {
        int newY = 45;
        point.setY(newY);
        assertEquals(point.getY(), newY);
    }

}
