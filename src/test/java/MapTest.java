import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MapTest extends MapTestBase
{
    @Test
    @DisplayName("Map Initialization Test")
    public void MapInitTest()
    {
        TestNeighbors();

        TestPumpBreak();

        TestHole();

        TestSliminess();

        TestStickiness();
    }

    private void TestNeighbors()
    {
        assertAll("Pipe Neighbors Test",
                () -> assertTrue(w.getNeighbors().contains(pi1)),
                () -> assertTrue(w.getNeighbors().contains(pi3)),

                () -> assertTrue(pi1.getNeighbors().contains(w)),
                () -> assertTrue(pi1.getNeighbors().contains(p1)),

                () -> assertTrue(pi2.getNeighbors().contains(p1)),
                () -> assertTrue(pi2.getNeighbors().contains(c)),

                () -> assertTrue(pi3.getNeighbors().contains(w)),
                () -> assertTrue(pi3.getNeighbors().contains(p2)),

                () -> assertTrue(pi4.getNeighbors().contains(p2)),
                () -> assertTrue(pi4.getNeighbors().contains(c)),

                () -> assertTrue(c.getNeighbors().contains(pi2)),
                () -> assertTrue(c.getNeighbors().contains(pi4))
        );
    }

    private void TestStickiness()
    {
        assertEquals(0, pi1.getSticky());
        assertEquals(0, pi2.getSticky());
        assertEquals(0, pi3.getSticky());
        assertEquals(0, pi4.getSticky());

        pi3.stick();

        assertEquals(0, pi1.getSticky());
        assertEquals(0, pi2.getSticky());
        assertEquals(0, pi4.getSticky());
        assertNotEquals(0, pi3.getSticky());
    }

    private void TestHole()
    {
        //Only pi4 should be broken
        assertFalse(pi1.getHoleOnPipe());
        assertFalse(pi2.getHoleOnPipe());
        assertFalse(pi3.getHoleOnPipe());
        assertTrue(pi4.getHoleOnPipe());
    }

    private void TestSliminess()
    {
        assertEquals(0, pi1.getSlimey());
        assertEquals(0, pi2.getSlimey());
        assertEquals(0, pi3.getSlimey());
        assertEquals(0, pi4.getSlimey());

        pi2.slime();

        assertEquals(0, pi1.getSlimey());
        assertEquals(0, pi3.getSlimey());
        assertEquals(0, pi4.getSlimey());
        assertNotEquals(0, pi2.getSlimey());
    }

    private void TestPumpBreak()
    {
        //Pumps are Not broken at first
        assertFalse(p1.getBroken());
        assertFalse(p2.getBroken());

        //If we break one it should be broken
        p2.breakPump();

        assertTrue(p2.getBroken());
        assertFalse(p1.getBroken());
    }
}
