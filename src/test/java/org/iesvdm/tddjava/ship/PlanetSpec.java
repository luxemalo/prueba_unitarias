package org.iesvdm.tddjava.ship;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class PlanetSpec {

    private Planet planet;
    private final Point max = new Point(50, 50);
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        obstacles = new ArrayList<Point>();
        obstacles.add(new Point(12, 13));
        obstacles.add(new Point(16, 32));
        planet = new Planet(max, obstacles);
    }

    public void whenInstantiatedThenMaxIsSet() {
        assertEquals(planet.getMax(), max);
    }

    public void whenInstantiatedThenObstaclesAreSet() {
        assertEquals(planet.getObstacles(), obstacles);
    }

    public void givenEmptyObstaclesListWhenInstantiatedThenEmptyListIsSet() {
        obstacles = new ArrayList<>();
        planet = new Planet(max, obstacles);
        assertEquals(planet.getObstacles(), obstacles);
    }

    public void givenNullObstaclesListWhenInstantiatedThenEmptyListIsSet() {
        planet = new Planet(max, null);
        assertNull(planet.getObstacles(), "Es nulo");
        assertNotNull(planet.getObstacles(), "Es valido");
        assertTrue(planet.getObstacles().isEmpty());
    }

    public void whenSetObstaclesThenListIsUpdated() {
        List<Point> newObstacles = new ArrayList<>();
        newObstacles.add(new Point(20, 20));
        planet.setObstacles(newObstacles);
        assertEquals(planet.getObstacles(), newObstacles);
    }

}
