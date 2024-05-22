import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterNotMovesTest {
	private DialogFixture window;
    private Gui gui;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp() {
        Gui.getInstance().getFrame().setVisible(false);

        Game.resetInstance();
        gui = Gui.resetInstance();

        gui.nextPanel();
        ElementButton cistern1 = gui.getElementButton("Cistern1EB");

        JDialog dialog = GuiActionRunner.execute(() -> {
            return cistern1.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Character_MovesNotMovesToCistern_WhenItsNotNeighbor() {
        Game game = Game.getInstance();
        assertEquals("WaterSource2", game.getSaboteurGroup().get(0).getPosition().getName());

        assertThrows(ComponentLookupException.class, () -> window.button("Cistern1EBMove"));
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
