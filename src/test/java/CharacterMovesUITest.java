import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterMovesTest {
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
        ElementButton pipe4 = gui.getElementButton("Pipe4EB");

        JDialog dialog = GuiActionRunner.execute(() -> {
            return pipe4.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Character_MovesToPipe_WhenItsNeighbor() {
        Game game = Game.getInstance();
        assertEquals("WaterSource2", game.getSaboteurGroup().get(0).getPosition().getName());

        window.button("Pipe4EBMove").click();

        assertEquals("Pipe4", game.getSaboteurGroup().get(0).getPosition().getName());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
