import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        
        ElementButton cistern2 = gui.getElementButton("Cistern2EB");
        JDialog dialog = GuiActionRunner.execute(() -> {
            return cistern2.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Repairman_NotPlacesPipe_WhenHeDoesNotHaveOne() {
        window.button("Cistern2EBEndMove").click();

        final ElementButton cistern2 = gui.getElementButton("Cistern2EB");
        cistern2.showActionButtonWindow();
        window.show();

        window.button("Cistern2EBEndMove").click();
        
        cistern2.showActionButtonWindow();
        window.show();

        window.button("Cistern2EBEndMove").click();
        
        window.cleanUp();
        JDialog lastDialog = GuiActionRunner.execute(() -> cistern2.showActionButtonWindow());
        DialogFixture lastDialogFixture = new DialogFixture(lastDialog);
        lastDialogFixture.show();

        Game game = Game.getInstance();
        ArrayList<Element> elements = game.getGameElements();
        Element pipe6 = elements.get(elements.size() - 1);
        game.getRepairmanGroup().get(1).setHoldingPipe(null);
        assertNull(game.getRepairmanGroup().get(1).getHoldingPipe());
        assertEquals(1, pipe6.getNeighbors().size());

        lastDialogFixture.button("Cistern2EBPlacePipe").click();

        assertNull(game.getRepairmanGroup().get(1).getHoldingPipe());
        assertEquals(1, pipe6.getNeighbors().size());
        
        lastDialogFixture.cleanUp();
    }
}
