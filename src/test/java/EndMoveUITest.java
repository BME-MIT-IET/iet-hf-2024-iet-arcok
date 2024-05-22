import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EndMoveUITest {
	private FrameFixture window;
    private Gui gui;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        Gui.getInstance().getFrame().setVisible(false);

        Game.resetInstance();
        Game.getInstance().setTurns(2);
        gui = Gui.resetInstance();

        gui.nextPanel();

        JFrame frame = GuiActionRunner.execute(() -> {
            return gui.getFrame();
        });
        window = new FrameFixture(frame);
    }

    @Test
    void Move_Ends_WhenEndingTurn() {
        assertEquals("Saboteur1-s Turn", window.label("WhosTurnLabel").text());

        window.button("EndMoveButton").click();
        assertEquals("Saboteur2-s Turn", window.label("WhosTurnLabel").text());

        window.button("EndMoveButton").click();
        assertEquals("Repairman1-s Turn", window.label("WhosTurnLabel").text());

        window.button("EndMoveButton").click();
        assertEquals("Repairman2-s Turn", window.label("WhosTurnLabel").text());
        assertEquals("0", window.label("TurnCounterLabel").text());

        window.button("EndMoveButton").click();
        assertEquals("Saboteur1-s Turn", window.label("WhosTurnLabel").text());
        assertEquals("1", window.label("TurnCounterLabel").text());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
