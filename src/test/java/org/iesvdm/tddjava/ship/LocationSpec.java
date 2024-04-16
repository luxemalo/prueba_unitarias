package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class LocationSpec {

    private final int x = 12;
    private final int y = 32;
    private final Direction direction = Direction.NORTH;
    private Point max;
    private Location location;
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        max = new Point(50, 50);
        location = new Location(new Point(x, y), direction);
        obstacles = new ArrayList<Point>();
    }

    public void whenInstantiatedThenXIsStored() {
        assertEquals(location.getX(), x);
    }

    public void whenInstantiatedThenYIsStored() {
        assertEquals(location.getY(), y);
    }

    public void whenInstantiatedThenDirectionIsStored() {
        assertEquals(location.getDirection(), direction);
    }

    public void givenDirectionNWhenForwardThenYDecreases() {
        int inicial = location.getY();
        location.forward();
        assertEquals(location.getY(), inicial - 1);
    }

    public void givenDirectionSWhenForwardThenYIncreases() {
        location.setDirection(Direction.SOUTH);
        int inicial = location.getY();
        location.forward();
        assertEquals(location.getY(), inicial + 1);
    }

    public void givenDirectionEWhenForwardThenXIncreases() {
        location.setDirection(Direction.EAST);
        int inicial = location.getX();
        location.forward();
        assertEquals(location.getX(), inicial + 1);
    }

    public void givenDirectionWWhenForwardThenXDecreases() {
        location.setDirection(Direction.WEST);
        int inicial = location.getX();
        location.forward();
        assertEquals(location.getX(), inicial - 1);
    }

    public void givenDirectionNWhenBackwardThenYIncreases() {
        location.setDirection(Direction.NORTH);
        int inicial = location.getY();
        location.backward();
        assertEquals(location.getY(), inicial + 1);
    }

    public void givenDirectionSWhenBackwardThenYDecreases() {
        location.setDirection(Direction.SOUTH);
        int inicial = location.getY();
        location.backward();
        assertEquals(location.getY(), inicial - 1);
    }

    public void givenDirectionEWhenBackwardThenXDecreases() {
        location.setDirection(Direction.EAST);
        int inicial = location.getX();
        location.backward();
        assertEquals(location.getX(), inicial - 1);
    }

    public void givenDirectionWWhenBackwardThenXIncreases() {
        location.setDirection(Direction.WEST);
        int inicial = location.getX();
        location.backward();
        assertEquals(location.getX(), inicial + 1);
    }

    public void whenTurnLeftThenDirectionIsSet() {
        Direction expectedDirection = direction.turnLeft();
        location.turnLeft();
        assertEquals(location.getDirection(), expectedDirection);
    }

    public void whenTurnRightThenDirectionIsSet() {
        Direction expectedDirection = direction.turnRight();
        location.turnRight();
        assertEquals(location.getDirection(), expectedDirection);
    }

    public void givenSameObjectsWhenEqualsThenTrue() {
        Location localizacion = new Location(new Point(x, y), direction);
        assertTrue(location.equals(localizacion));
    }

    public void givenDifferentObjectWhenEqualsThenFalse() {
        assertFalse(location.equals(new Object()));
    }

    public void givenDifferentXWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x + 1, y), direction);
        assertFalse(location.equals(differentLocation));
    }

    public void givenDifferentYWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x, y + 1), direction);
        assertFalse(location.equals(differentLocation));
    }

    public void givenDifferentDirectionWhenEqualsThenFalse() {
        Location differentLocation = new Location(new Point(x, y), Direction.SOUTH);
        assertFalse(location.equals(differentLocation));
    }

    public void givenSameXYDirectionWhenEqualsThenTrue() {
        Location localizacion = new Location(new Point(x, y), direction);
        assertTrue(location.equals(localizacion));
    }

    public void whenCopyThenDifferentObject() {
        Location copy = location.copy();
        assertNotSame(location, copy);
        }

    public void whenCopyThenEquals() {
        Location copy = location.copy();
        assertEquals(location, copy);
    }

    public void givenDirectionEAndXEqualsMaxXWhenForwardThen1() {
        location.setDirection(Direction.EAST);
        location.getPoint().setX(max.getX());
        assertTrue(location.forward());
        assertEquals(location.getX(), 1);
    }

    public void givenDirectionWAndXEquals1WhenForwardThenMaxX() {
        location.setDirection(Direction.WEST);
        location.getPoint().setX(1);
        assertTrue(location.forward());
        assertEquals(location.getX(), max.getX());
    }

    public void givenDirectionNAndYEquals1WhenForwardThenMaxY() {
        location.setDirection(Direction.NORTH);
        location.getPoint().setY(1);
        assertTrue(location.forward());
        assertEquals(location.getY(), max.getY());
    }

    public void givenDirectionSAndYEqualsMaxYWhenForwardThen1() {
        location.setDirection(Direction.SOUTH);
        location.getPoint().setY(max.getY());
        assertTrue(location.forward());
        assertEquals(location.getY(), 1);
    }

    public void givenObstacleWhenForwardThenReturnFalse() {
        obstacles.add(new Point(x + 1, y));
        location.setDirection(Direction.EAST);
        assertFalse(location.forward((Point) obstacles));
    }

    public void givenObstacleWhenBackwardThenReturnFalse() {
        obstacles.add(new Point(x - 1, y));
        location.setDirection(Direction.WEST);
        assertFalse(location.backward((Point) obstacles));
    }

}
