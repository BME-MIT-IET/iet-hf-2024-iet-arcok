import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class PointsTest extends MapTestBase
{
    @Test
    @DisplayName("Calculate Points After Waterflow Test")
    public void CalculatePointsTest()
    {
        assertAll("Stage 1, All Pipe and Pump is Empty",
                () -> assertFalse(pi1.getContainingWater()),
                () -> assertFalse(pi2.getContainingWater()),
                () -> assertFalse(pi3.getContainingWater()),
                () -> assertFalse(pi4.getContainingWater()),
                () -> assertFalse(p1.getContainingWater()),
                () -> assertFalse(p2.getContainingWater())
        );
        //All Point is 0 before start
        CalculatePointsMock(List.of(c),saboteurPointSources);
        assertEquals(0,repairmanPoints);
        assertEquals(0,saboteurPoints);

        //3 iterations of Water Flow Simulation, to water reach the leaking pipe
        SimulateWaterFlowMock();
        SimulateWaterFlowMock();
        SimulateWaterFlowMock();

        assertEquals(0,repairmanPoints);
        assertEquals(1,saboteurPoints);

        //Water reaches the Cistern
        SimulateWaterFlowMock();
        assertEquals(1,repairmanPoints);
        assertEquals(2,saboteurPoints);
    }
}
