import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.UnexpectedException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JSpinnerFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class MenuPlayerCountAndRoundsUITest {
    private FrameFixture window;
    private Gui gui;
    private JSpinnerFixture spinner;

        
    static Stream<Arguments> spinnerNamesWithMinMaxBounds() {
        return Stream.of(Arguments.of("RoundSetter","1","100"), 
                       Arguments.of("PlayerCount","4","8"));
    }

    @BeforeClass
    public static void setUpOnce(){
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp(){
        Gui.getInstance().getFrame().setVisible(false);

        gui = Gui.resetInstance();

        JFrame frame = GuiActionRunner.execute(() -> {
            return gui.getFrame();
        });
        window = new FrameFixture(frame);


    }

    
    @ParameterizedTest(name = "{0}setEnteredValue")
    @MethodSource(value = "spinnerNamesWithMinMaxBounds")
    @Order(1)
    void setEnteredValue(String name,String minVal,String maxVal){
        spinner = window.spinner(name);
        assertNotEquals(spinner.text(),maxVal);
        String lastVal = spinner.text();

        spinner.enterTextAndCommit(maxVal);

        assertNotEquals(lastVal,spinner.text());
    }

    

    @ParameterizedTest(name = "{0}increasingWhenPressedTheIncrease")
    @MethodSource(value = "spinnerNamesWithMinMaxBounds")
    @Order(2)
    void increasingWhenPressedTheIncrease(String name,String minVal,String maxVal){
        spinner = window.spinner(name);
        spinner.enterTextAndCommit(minVal);

        spinner.increment();
        assertTrue(Integer.parseInt(minVal) < Integer.parseInt(spinner.text()));
    }


    @ParameterizedTest(name = "{0}decreasingWhenPressedTheDecrease")
    @MethodSource(value = "spinnerNamesWithMinMaxBounds")
    @Order(3)
    void decreasingWhenPressedTheDecrease(String name,String minVal,String maxVal){
        spinner = window.spinner(name);
        spinner.enterTextAndCommit(maxVal);

        spinner.decrement();
        assertTrue(Integer.parseInt(maxVal) > Integer.parseInt(spinner.text()));
    }


    @ParameterizedTest(name = "{0}minimumValueBoundCheck")
    @MethodSource(value = "spinnerNamesWithMinMaxBounds")
    void minimumValueBoundCheck(String name,String minVal,String maxVal){
        spinner = window.spinner(name);
        spinner.enterTextAndCommit(minVal);

        spinner.decrement();
        assertEquals(minVal, spinner.text());
        spinner.increment();
        assertNotEquals(minVal, spinner.text());

        String outBoundVal = Integer.toString(Integer.parseInt(minVal) - Integer.parseInt(maxVal));
        assertThrows(UnexpectedException.class, () -> spinner.enterTextAndCommit(outBoundVal));
    }

    @ParameterizedTest(name = "{0}maximumValueBoundCheck")
    @MethodSource(value = "spinnerNamesWithMinMaxBounds")
    void maximumValueBoundCheck(String name,String minVal,String maxVal){
        spinner = window.spinner(name);
        spinner.enterTextAndCommit(maxVal);

        spinner.increment();
        assertEquals(maxVal, spinner.text());
        spinner.decrement();
        assertNotEquals(maxVal, spinner.text());
        
        String outBoundVal = Integer.toString(Integer.parseInt(maxVal) + (Integer.parseInt(maxVal) - Integer.parseInt(minVal)));
        assertThrows(UnexpectedException.class, () -> spinner.enterTextAndCommit(outBoundVal));
    }


    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

}
