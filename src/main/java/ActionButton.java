import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

/**
 * A játékban végrehajtható akciókat megvalósító gombokat reprezentáló osztály.
 * A gombokat az ElementButton kattintására hozzuk létre, és a gombon megjelenítjük az adott akciót.
 */
public class ActionButton extends JButton{
    private ArrayList<Integer> actions;

    ActionButton(ArrayList<Integer> acts/*Element _element*/)
    {
		actions = acts;

		if(acts==null)
			actions = new ArrayList<Integer>();

		//Hozzaad 2 db parametert ami nem lesz hasznalva, csak ha nem kap eleg parametert a konstuktor
		actions.add(0);
		actions.add(0);

        /** Ezen keresztul tudjuk meghivni a Control fuggvenyeit.*/
        Control instance = Control.getInstance();


		addActionListener(e -> {
			Game.getInstance().getCurrentCharacter().decreaseRemainingSteps();
			switch(e.getActionCommand()) {
				case "Move":
					instance.Move(actions.get(0));
					break;
				case "Stab":
					instance.Stab();
					break;
				case "PlacePump":
					instance.PlacePump();
					break;
				case "PlacePipe":
					instance.PlacePipe();
					break;
				case "PickupPump":
					instance.PickupPump();
					break;
				case "PickUpPipe":
					instance.PickUpPipe(actions.get(0));
					break;
				case "Repair":
					instance.Repair();
					break;
				case "Stick":
					instance.Stick();
					break;
				case "Slime":
					instance.Slime();
					break;
				case "Adjust":
					instance.Adjust(actions.get(0), actions.get(1));
					break;
				case "EndMove":
					instance.EndMove();
					break;
			}


        });
    }
}
