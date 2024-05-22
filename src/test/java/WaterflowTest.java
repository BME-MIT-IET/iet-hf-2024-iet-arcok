import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WaterflowTest extends MapTestBase
{
    @Test
    @DisplayName("Only Water Flow Test")
    public void FullWaterFlowTest()
    {
        assertAll("Stage 1, All Pipe and Pump is Empty",
                () -> assertFalse(pi1.getContainingWater()),
                () -> assertFalse(pi2.getContainingWater()),
                () -> assertFalse(pi3.getContainingWater()),
                () -> assertFalse(pi4.getContainingWater()),
                () -> assertFalse(p1.getContainingWater()),
                () -> assertFalse(p2.getContainingWater())
        );
        SimulateWaterFlowMock();
        assertAll("Stage 2",
                () -> assertTrue(pi1.getContainingWater()),
                () -> assertTrue(pi3.getContainingWater()),
                //True, because on P1 turn W fills pi1 and pi3, and from pi3 water can be extracted
                () -> assertTrue(p2.getContainingWater()),

                () -> assertFalse(p1.getContainingWater()),
                () -> assertFalse(pi2.getContainingWater()),
                () -> assertFalse(pi4.getContainingWater())
        );

        SimulateWaterFlowMock();
        assertAll("Stage 3",
                () -> assertTrue(pi1.getContainingWater()),
                () -> assertTrue(pi3.getContainingWater()),
                () -> assertTrue(p1.getContainingWater()),
                () -> assertTrue(p2.getContainingWater()),
                //True, because water can be pumped through a leaking pipe
                () -> assertTrue(pi4.getContainingWater()),

                //Water haven't reached pi2 yet
                () -> assertFalse(pi2.getContainingWater())

        );

        SimulateWaterFlowMock();
        assertAll("Stage 4, System filled with water",
                () -> assertTrue(pi1.getContainingWater()),
                () -> assertTrue(pi3.getContainingWater()),
                () -> assertTrue(p1.getContainingWater()),
                () -> assertTrue(p2.getContainingWater()),
                () -> assertTrue(pi2.getContainingWater()),
                //True, because water can be pumped through a leaking pipe
                () -> assertTrue(pi4.getContainingWater())
        );
    }
}
