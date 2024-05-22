import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.exception.ComponentLookupException;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JPanelFixture;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;


@TestMethodOrder(OrderAnnotation.class)
class MenuPlayerCountUITest {
    private FrameFixture window;
    private Gui gui;

    @BeforeClass
    public static void setUpOnce(){
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeEach
    public void setUp(){
        Gui.getInstance().getFrame().setVisible(false);

        Game.resetInstance();
        gui = Gui.resetInstance();


        JFrame frame = GuiActionRunner.execute(() -> {
            return gui.getFrame();
        });
        window = new FrameFixture(frame);
    }
    
    
    @Test
    @Order(1)
    void MenuStartButtonClick(){
        
        assertThrows(ComponentLookupException.class, () -> window.panel("GamePanel"));
        window.button("StartButton").click();
        assertThrows(ComponentLookupException.class, () -> window.panel("MenuPanel"));
        assertEquals(JPanelFixture.class, window.panel("GamePanel").getClass());
    }

    @Test
    @Order(2)
    void GameValuesChangingAfterStartButtonClickAtMenu(){
        String playerCount = "8";
        String rounds = "10";
        window.spinner("RoundSetter").enterTextAndCommit("10");
        window.spinner("PlayerCount").enterTextAndCommit("8");
        window.button("StartButton").click();
        assertEquals(Integer.parseInt(rounds), Game.getInstance().getTurns());
        String allPlayersInGame = Integer.toString(Game.getInstance().repairmanGroup.size() + Game.getInstance().saboteurGroup.size());
        assertEquals(playerCount, allPlayersInGame);
    }



    @AfterEach
    public void tearDown() {
        window.cleanUp();
    }

}
