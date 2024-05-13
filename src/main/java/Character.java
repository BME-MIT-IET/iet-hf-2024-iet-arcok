import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Character osztaly, mely a jatekosok által irányított karaktereket reprezentálja.
 * @author Bodnar Mark*/
public abstract class Character {
    private String Name;
	private int remainingSteps = 3;
    private Logger logger;
    
    String getName() {return Name;}
    void setName(String name) {this.Name=name;}
	public void move(int dir) {}/** Csorendszeren mozgas.*/
	public void adjustPump(int src, int dest) {}/** Pumpak ki- es bemenetenek valtoztatasa*/
	void dealDamage() {}/** Lyukasztas*/

	public int resetRemainingSteps() {
		remainingSteps=3;
		return remainingSteps;
	}
	public int getRemainingSteps() {
		return remainingSteps;
	}
	public int decreaseRemainingSteps() {
		Control.getInstance().appendToLog("Remaining steps: "+remainingSteps);
		remainingSteps--;
		return remainingSteps;
	}
	public synchronized void step() {
		resetRemainingSteps();
		try {
			wait();
		}
        catch (ThreadDeath e) {
            throw e;
        }
        catch (Exception e) {
			logger.log(Level.FINER, e.toString());
		}
	}
	public void makeSticky(){}/** Ragadossa teszi az adott csovet amin all*/
	
	public synchronized void wakeUp() {
		this.notifyAll();
	}
}
