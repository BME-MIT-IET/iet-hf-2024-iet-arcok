import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JDialog;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaboteurActsOnPipeUITest {
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

        Game.getInstance().getCurrentCharacter().move(0);
        ElementButton pipe4 = gui.getElementButton("Pipe4EB");

        JDialog dialog = GuiActionRunner.execute(() -> {
            return pipe4.showActionButtonWindow();
        });
        window = new DialogFixture(dialog);
        window.show();
    }

    @Test
    void Saboteur_StabsPipe_WhenHeIsOnPipe() {
        Game game = Game.getInstance();
        Pipe pipe4 = (Pipe)game.getSaboteurGroup().get(0).getPosition();

        assertEquals(false, pipe4.getHoleOnPipe());

        window.button("Pipe4EBStab").click();

        assertEquals(true, pipe4.getHoleOnPipe());
    }

    @Test
    void Saboteur_SticksPipe_WhenHeIsOnPipe() {
        Game game = Game.getInstance();
        Pipe pipe4 = (Pipe)game.getSaboteurGroup().get(0).getPosition();

        assertEquals(0, pipe4.getSticky());

        window.button("Pipe4EBStick").click();

        assertEquals(-3, pipe4.getSticky());
    }

    @Test
    void Saboteur_SlimesPipe_WhenHeIsOnPipe() {
        Game game = Game.getInstance();
        Pipe pipe4 = (Pipe)game.getSaboteurGroup().get(0).getPosition();

        assertEquals(0, pipe4.getSlimey());

        window.button("Pipe4EBSlime").click();

        assertEquals(3, pipe4.getSlimey());
    }

    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }
}
