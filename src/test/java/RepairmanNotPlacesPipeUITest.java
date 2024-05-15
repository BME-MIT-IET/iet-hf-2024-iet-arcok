import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.swing.exception.ComponentLookupException;

class RepairmanNotPlacesPipeTest {
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

        Game game = Game.getInstance();
        game.setCurrentCharacter(game.getRepairmanGroup().get(1));
        game.getRepairmanGroup().get(1).setHoldingPipe(null);
        
        ElementButton cistern2 = gui.getElementButton("Cistern2EB");
        JDialog dialog = GuiActionRunner.execute(() -> {
            return cistern2.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Repairman_NotPlacesPipe_WhenHeDoesNotHaveOne() {
        Game game = Game.getInstance();
        ArrayList<Element> elements = game.getGameElements();
        Element pipe6 = elements.get(elements.size() - 1);
        assertNull(game.getRepairmanGroup().get(1).getHoldingPipe());
        assertEquals(1, pipe6.getNeighbors().size());

        assertThrows(ComponentLookupException.class, () -> window.button("Cistern2EBPlacePipe").click());

        assertEquals(1, pipe6.getNeighbors().size());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
