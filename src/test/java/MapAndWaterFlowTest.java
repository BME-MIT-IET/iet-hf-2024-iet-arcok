import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapAndWaterFlowTest {

    Game game = Game.getInstance();
    Cistern c;
    WaterSource w;
    Pump p1;
    Pump p2;
    Pipe pi1;
    Pipe pi2;
    Pipe pi3;
    Pipe pi4;
    @BeforeEach
    public void MapInit()
    {
        c = new Cistern();
        w = new WaterSource();

        p1 = new Pump();
        p2 = new Pump();

        pi1 = new Pipe();
        pi2 = new Pipe();
        pi3 = new Pipe();
        pi4 = new Pipe();

        addNeighbor(w, pi1);
        addNeighbor(pi1, p1);
        addNeighbor(p1, pi2);
        p1.adjust(0,1);
        addNeighbor(pi2, c);

        addNeighbor(w, pi3);
        addNeighbor(pi3, p2);
        addNeighbor(p2, pi4);
        p2.adjust(0,1);
        addNeighbor(pi4, c);

        pi4.damage();

        ArrayList<Element> elements = new ArrayList<>(List.of(w, c, p1, p2, pi1, pi2, pi3, pi4));
        ArrayList<SaboteurPointSource> saboteurPointSources= new ArrayList<>(List.of( p1, p2, pi1, pi2, pi3, pi4));
        ArrayList<Pump> pumps = new ArrayList<>(List.of( p1, p2));

        game.load(elements,saboteurPointSources,new ArrayList<>(List.of(c)),new ArrayList<>(),new ArrayList<>(),0,0,1,2,2,pumps);

    }

    @Test
    @DisplayName("Map Initialization Test")
    public void MapInitTest()
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

        //Pumps are Not broken at first
        assertFalse(p1.getBroken());
        assertFalse(p2.getBroken());

        //If we break one it should be broken
        p2.breakPump();
        assertTrue(p2.getBroken());

        //Only pi4 should be broken
        assertFalse(pi1.getHoleOnPipe());
        assertFalse(pi2.getHoleOnPipe());
        assertFalse(pi3.getHoleOnPipe());
        assertTrue(pi4.getHoleOnPipe());
    }

    @Test
    @DisplayName("Water Flow Test")
    public void WaterFlowTest()
    {
        assertAll("All Pipe and Pump is Empty",
                () -> assertFalse(pi1.getContainingWater()),
                () -> assertFalse(pi2.getContainingWater()),
                () -> assertFalse(pi3.getContainingWater()),
                () -> assertFalse(pi4.getContainingWater()),
                () -> assertFalse(p1.getContainingWater()),
                () -> assertFalse(p2.getContainingWater())
        );

        game.SimulateWaterflow();
        assertAll("",
                () -> assertTrue(pi1.getContainingWater()),
                () -> assertTrue(pi2.getContainingWater()),//false
                () -> assertTrue(pi3.getContainingWater()),


                () -> assertTrue(p1.getContainingWater()),
                () -> assertTrue(p2.getContainingWater()),

                () -> assertFalse(pi4.getContainingWater())
        );

    }

    private void addNeighbor(NonPipe np,Pipe pi)
    {
        np.addNeighbor(pi);
        pi.addNeighbor(np);
    }

    private void addNeighbor(Pipe pi,NonPipe np)
    {
        np.addNeighbor(pi);
        pi.addNeighbor(np);
    }
}
