package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN() {
        assertEquals(Direction.getFromShortName('N'), Direction.NORTH);
    }

    public void whenGetFromShortNameWThenReturnDirectionW() {
        assertEquals(Direction.getFromShortName('W'), Direction.WEST);
    }

    public void whenGetFromShortNameBThenReturnNone() {

    }

    public void givenSWhenLeftThenE() {

    }

    public void givenNWhenLeftThenW() {

    }

    public void givenSWhenRightThenW() {

    }

    public void givenWWhenRightThenN() {

    }

}
