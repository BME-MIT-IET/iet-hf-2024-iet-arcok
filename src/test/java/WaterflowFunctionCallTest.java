import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WaterflowFunctionCallTest extends MapTestBase{

    @Override
    @BeforeEach
    public void MapInit() {
        c = Mockito.spy(new Cistern());
        c.setName("Spy c");

        w = Mockito.spy(new WaterSource());
        w.setName("Spy w");

        p1 = Mockito.spy(new Pump());
        p1.setName("Spy p1");

        p2 = Mockito.spy(new Pump());
        p2.setName("Spy p2");

        pi1 = Mockito.spy(new Pipe());
        pi1.setName("Spy pi1");
        pi2 = Mockito.spy(new Pipe());
        pi2.setName("Spy pi2");
        pi3 = Mockito.spy(new Pipe());
        pi3.setName("Spy pi3");
        pi4 = Mockito.spy(new Pipe());
        pi4.setName("Spy pi4");

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

        elements = new ArrayList<>(List.of(w, c, p1, p2, pi1, pi2, pi3, pi4));
        saboteurPointSources= new ArrayList<>(List.of( p1, p2, pi1, pi2, pi3, pi4));
        pumps = new ArrayList<>(List.of( p1, p2));

        game.load(elements,saboteurPointSources,new ArrayList<>(List.of(c)),new ArrayList<>(),new ArrayList<>(),0,0,1,2,2,pumps);
    }

    @Test
    @DisplayName("Water Flow Function Call Test")
    public void WaterflowInvocationTest()
    {
        SimulateWaterFlowMock();
        //The Cistern is the beginning of water simulation
        verify(c,times(1)).step();

        //WaterSource called 2 times, because 2 path of water can reach it
        verify(w,times(2)).step();

        verify(p1,times(1)).step();
        verify(p2,times(1)).step();

        verify(pi1,times(1)).step();
        verify(pi2,times(1)).step();
        verify(pi3,times(1)).step();
        //Damaging the pipe also calls step, beside the water flow simulation
        verify(pi4,times(2)).step();

    }
}
