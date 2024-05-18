import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


abstract class MapTestBase{

    protected Game game = Game.getInstance();
    protected Cistern c;
    protected WaterSource w;
    protected Pump p1;
    protected Pump p2;
    protected Pipe pi1;
    protected Pipe pi2;
    protected Pipe pi3;
    protected Pipe pi4;
    protected int repairmanPoints = 0;
    protected int saboteurPoints = 0;

    protected ArrayList<Element> elements;
    protected ArrayList<SaboteurPointSource> saboteurPointSources;
    protected ArrayList<Pump> pumps;
    @BeforeEach
    public void MapInit()
    {
        c = new Cistern();
        c.setName("c");

        w = new WaterSource();
        w.setName("w");

        p1 = new Pump();
        p1.setName("p1");

        p2 = new Pump();
        p2.setName("p2");

        pi1 = new Pipe();
        pi1.setName("pi1");
        pi2 = new Pipe();
        pi2.setName("pi2");
        pi3 = new Pipe();
        pi3.setName("pi3");
        pi4 = new Pipe();
        pi4.setName("pi4");

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

    protected void addNeighbor(NonPipe np,Pipe pi)
    {
        np.addNeighbor(pi);
        pi.addNeighbor(np);
    }

    protected void addNeighbor(Pipe pi,NonPipe np)
    {
        np.addNeighbor(pi);
        pi.addNeighbor(np);
    }

    protected void SimulateWaterFlowMock()
    {
        game.SimulateWaterflow();
        CalculatePointsMock(List.of(c),saboteurPointSources);
    }

    protected void CalculatePointsMock(List<Cistern> cisterns, List<SaboteurPointSource> saboteurPointSources)
    {
        int repairmanWater = 0;
        int saboteurWater = 0;
        for (Cistern cistern : cisterns) {
            int ciWater = cistern.measureAndResetWaterFlown();
            repairmanWater += ciWater;
        }

        for (SaboteurPointSource saboteurPointSource : saboteurPointSources) {
            int sWater = saboteurPointSource.measureAndResetLeakedWaterAmount();
            saboteurWater += sWater;
        }
        repairmanPoints+=repairmanWater;
        saboteurPoints+=saboteurWater;
    }
}
