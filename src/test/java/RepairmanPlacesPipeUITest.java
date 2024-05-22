import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RepairmanPlacesPipeTest {
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
        
        ElementButton cistern2 = gui.getElementButton("Cistern2EB");
        JDialog dialog = GuiActionRunner.execute(() -> {
            return cistern2.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Repairman_PlacesPipe_WhenHeIsOnCistern() {
        Game game = Game.getInstance();
        ArrayList<Element> elements = game.getGameElements();
        Element pipe6 = elements.get(elements.size() - 1);
        assertNotNull(game.getRepairmanGroup().get(1).getHoldingPipe());
        assertEquals(1, pipe6.getNeighbors().size());

        window.button("Cistern2EBPlacePipe").click();

        assertNull(game.getRepairmanGroup().get(1).getHoldingPipe());
        assertEquals(2, pipe6.getNeighbors().size());
        assertEquals("Cistern2", pipe6.getNeighbors().get(1).getName());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
