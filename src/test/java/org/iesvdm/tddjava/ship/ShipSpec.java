package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class ShipSpec {

    private Ship ship;
    private Location location;
    private Planet planet;

    @BeforeMethod
    public void beforeTest() {
        Point max = new Point(50, 50);
        location = new Location(new Point(21, 13), Direction.NORTH);
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(44, 44));
        obstacles.add(new Point(45, 46));
        planet = new Planet(max, obstacles);
//        ship = new Ship(location);
        ship = new Ship(location, planet);
    }

    public void whenInstantiatedThenLocationIsSet() {
//        Location location = new Location(new Point(21, 13), Direction.NORTH);
//        Ship ship = new Ship(location);
        assertEquals(ship.getLocation(), location);
    }

//    public void givenNorthWhenMoveForwardThenYDecreases() {
//        ship.moveForward();
//        assertEquals(ship.getLocation().getPoint().getY(), 12);
//    }
//
//    public void givenEastWhenMoveForwardThenXIncreases() {
//        ship.getLocation().setDirection(Direction.EAST);
//        ship.moveForward();
//        assertEquals(ship.getLocation().getPoint().getX(), 22);
//    }

    public void whenMoveForwardThenForward() {
        assertTrue(ship.moveForward());
        assertEquals(ship.getLocation().getY(), 12);
    }

    public void whenMoveBackwardThenBackward() {
        assertTrue(ship.moveBackward());
        assertEquals(ship.getLocation().getY(), 14);
    }

    public void whenTurnLeftThenLeft() {
        ship.turnLeft();
        assertEquals(ship.getLocation().getDirection(), Direction.WEST);

    }

    public void whenTurnRightThenRight() {
        ship.turnRight();
        assertEquals(ship.getLocation().getDirection(), Direction.EAST);
    }

    public void whenReceiveCommandsFThenForward() {
        assertEquals(ship.receiveCommands("f"), "O");
    }

    public void whenReceiveCommandsBThenBackward() {
        assertEquals(ship.receiveCommands("b"), "O");
    }

    public void whenReceiveCommandsLThenTurnLeft() {
        assertEquals(ship.receiveCommands("l"), "O");
    }

    public void whenReceiveCommandsRThenTurnRight() {
        assertEquals(ship.receiveCommands("r"), "O");
    }

    public void whenReceiveCommandsThenAllAreExecuted() {
        assertEquals(ship.receiveCommands("fflff"), "OOOOO");
    }

    public void whenInstantiatedThenPlanetIsStored() {
//        Point max = new Point(50, 50);
//        Planet planet = new Planet(max);
//        ship = new Ship(location, planet);
        assertEquals(ship.getPlanet(), planet);
    }

    public void givenDirectionEAndXEqualsMaxXWhenReceiveCommandsFThenWrap() {
        location.setDirection(Direction.EAST);
        location.getPoint().setX(planet.getMax().getX());
        ship = new Ship(location, planet);
        assertEquals(ship.receiveCommands("f"), "O");
        assertEquals(ship.getLocation().getX(), 1);
    }

    public void givenDirectionEAndXEquals1WhenReceiveCommandsBThenWrap() {
        location.setDirection(Direction.EAST);
        location.getPoint().setX(1);
        ship = new Ship(location, planet);
        assertEquals(ship.receiveCommands("b"), "O");
        assertEquals(ship.getLocation().getX(), planet.getMax().getX());
    }

    public void whenReceiveCommandsThenStopOnObstacle() {
        assertEquals(ship.receiveCommands("fffr"), "OOOO");
    }

    public void whenReceiveCommandsThenOForOkAndXForObstacle() {
        assertEquals(ship.receiveCommands("ff"), "OO");
        assertEquals(ship.receiveCommands("f"), "L");
    }

}
