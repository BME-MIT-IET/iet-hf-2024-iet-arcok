import java.util.ArrayList;
import java.util.Scanner;
/**
 * Az elemek tarolasaert es a jatek mukodeseert felelos osztaly
 * @author Szikszai Levente
 */
public class Game {

	//A Szerelő játékosok.
	ArrayList<Repairman> repairmanGroup;
	//A Szabotőr játékosok
	ArrayList<Saboteur> saboteurGroup;
	//A Szerelők pontja
	private int repairmanPoints;
	//A Szabotőrök pontja.
	private int saboteurPoints;
	//A játék által tárolt elemek.
	private ArrayList<Element> gameElements;
	//A játék által tárolt Ciszternák.
	private ArrayList<Cistern> cisterns;
	//A játék által tárolt elemek, melyekből tud elfolyni víz a sivatagba.
	private ArrayList<SaboteurPointSource> saboteurPointSources;
	//A hátralévő körök száma
	private int remainingRounds;
	//A Pumpákat elrontó DestructionTimer
	private DestructionTimer timer;
	//Be vannak-e kapcsolva a random események
	private boolean randomEnabled;
	//Hány körig lehet egy Cső csúszós
	public static int slimey = 3;
	//Hány körig lehet egy cső ragadós
	public static int sticky = 3;
	//Éppen körön lévő játékos
	private Character currentCharacter;
	//A Singleton Game objektum
	private static Game instance = new Game();

	/**
	 * Létrehozza a Game-et.
	 */
	protected Game()
	{
		repairmanGroup=new ArrayList<Repairman>();
		saboteurGroup=new ArrayList<Saboteur>();
		saboteurPointSources = new ArrayList<SaboteurPointSource>();
		gameElements=new ArrayList<Element>();
		cisterns = new ArrayList<Cistern>();
		timer = new DestructionTimer();
		repairmanPoints=0;
		saboteurPoints=0;
		remainingRounds=1;
		randomEnabled = true;
	}

	/**
	 * Visszatér a Singleton Game-el
	 * @return Game objektum
	 */
	public static Game getInstance()
	{
		return instance;
	}

    public static void resetInstance() {
        instance = new Game();
    }
	
	/**
	 * Beállítja a Game megfelelő változóit.
	 * @param gameE A csőhálózatot alkotó objektumok.
	 * @param SabPointSources A SaboteurPointSource interfészt megvalósító elemek
	 * @param cis A létező Ciszternák
	 * @param repairmanG A Szerelő játékosok
	 * @param saboteurG A Szabotőr játékosok
	 * @param repPoints A Szerelő csapat pontszáma
	 * @param sabPoints A Szabotőr csapat pontszáma
	 * @param rounds Hátralévő körök száma
	 * @param _slimey Hány körig állítódik csúszósra egy cső
	 * @param _sticky Hány körig állítódik ragadósra egy cső
	 * @param pumps A pumpákat tartalmazó lista
	 */
	
	public synchronized void load(ArrayList<Element> gameE,ArrayList<SaboteurPointSource> SabPointSources,ArrayList<Cistern> cis,
	ArrayList<Repairman> repairmanG,ArrayList<Saboteur> saboteurG, int repPoints, int sabPoints, int rounds, int _slimey, int _sticky, ArrayList<Pump> pumps)
	{
		repairmanGroup=repairmanG;
		saboteurGroup=saboteurG;
		saboteurPointSources = SabPointSources;
		gameElements= gameE;
		cisterns = cis;
		repairmanPoints=repPoints;
		saboteurPoints=sabPoints;
		remainingRounds=rounds;
		slimey = _slimey;
		sticky = _sticky;

		timer.resetPumps();
		for(Pump p : pumps)
		{
			timer.addPump(p);
		}
	}



	/**
	 * Beállítja a randomEnabled értékét a paraméternek megfelelően.
	 * @param random Legyenek-e engedélyezve a random események.
	 */
	public void setRandomEnabled(boolean random)
	{
		randomEnabled = random;
		timer.setRandomEnabled(random);
		if(random==true)
		{
			Control.getInstance().appendToLog("Random events Enabled");
		}
		else 
		{
			Control.getInstance().appendToLog("Random events Disabled");
		}
	}

	/**
	 * Viszaadja, hogy engedélyezve vannek-e a véletlen események
	 * @return randomEnabled
	 */
	public boolean isRandomEnabled()
	{
		return randomEnabled;
	}

	/**
	 * Visszaadja a Szerelő járékosokat
	 * @return repairmanGroup: A szerelő játékosokat tartalmazó ArrayList
	 */
	public ArrayList<Repairman> getRepairmanGroup()
	{
		return repairmanGroup;
	}

	/**
	 * Visszaadja a Szabotőr járékosokat
	 * @return saboteurGroup: A szabotőr játékosokat tartalmazó ArrayList
	 */
	public ArrayList<Saboteur> getSaboteurGroup()
	{
		return saboteurGroup;
	}
	
	/**
	 * Elindítja a játékot, és menedzseli melyik karaknernek van a köre.
	 */
	public void playGame()
	{
		while(remainingRounds>0)
		{
			for(int i = 0;i<saboteurGroup.size();i++)
			{
				currentCharacter=saboteurGroup.get(i);
				currentCharacter.resetRemainingSteps();
				saboteurGroup.get(i).step();
				endTurn();
			}
			for(int i = 0;i<repairmanGroup.size();i++)
			{
				currentCharacter=repairmanGroup.get(i);
				repairmanGroup.get(i).step();
				endTurn();
			}
			remainingRounds--;
		}
		endGame();
	}
	/**
	 * Játék befejezése, a győztes kihirdetése.
	 */
	public void endGame()
	{
		if(saboteurPoints>repairmanPoints)
		{
			System.out.println("Saboteur Team Won!");
		}
		else if(repairmanPoints>saboteurPoints)
		{
			System.out.println("Repairman Team Won!");
		}else
		{
			System.out.println("It's a Draw!");
		}
		Gui.getInstance().nextPanel();
	}
	
	/**
	 * Adott kör véget ér, elindul a Víz Szimuláció
	 */
	public void setTimer(DestructionTimer dt) {
		timer=dt;
	}
	public void endTurn()
	{
		timer.tick();
		this.SimulateWaterflow();
		int repairmanWater = 0;
		int saboteurWater = 0;
		for(int i=0;i<cisterns.size();i++) {
			int ciWater=cisterns.get(i).measureAndResetWaterFlown();
			repairmanWater+=ciWater;
		}
		
		for(int i=0;i<saboteurPointSources.size();i++) {
			int sWater=saboteurPointSources.get(i).measureAndResetLeakedWaterAmount();
			saboteurWater+=sWater;
		}
		this.repairmanPoints+=repairmanWater;
		this.saboteurPoints+=saboteurWater;
		if(remainingRounds==0) {
			endGame();
		}
	}
	
	/**
	 * Elindítja a víz szimulációt a Ciszternáktól.
	 */
	public void SimulateWaterflow()
	{
		for(int i = 0;i<cisterns.size();i++)
		{
			cisterns.get(i).step();
		}
	}
	
	/**
	 * Hozzáad egy Ciszternát a játékhoz
	 * @param c Ciszterna
	 */
	public void addCistern(Cistern c)
	{
		cisterns.add(c);
		gameElements.add(c);
	}
	
	/**
	 * Hozzáad egy Element-et a játékhoz.
	 * @param e Element
	 */
	public void addElement(Element e)
	{
		gameElements.add(e);
	}
	
	/**
	 * Visszaadja a tárolt elemeket a Game.
	 * @return gameElements
	 */
	public ArrayList<Element> getGameElements()//Jó lesz-e ArrayList??
	{
		return gameElements;
	}
	
	/**
	 * Hozzáad egy Saboteur-t a játékhoz.
	 * @param sab A hozzáadandó Saboteur
	 */
	public void addSaboteur(Saboteur sab)
	{
		saboteurGroup.add(sab);
	}
	
	/**
	 * Hozzáad egy Repairman-t a játékhoz.
	 * @param rep A hozzáadandó Repairman
	 */
	public void addRepairman(Repairman rep)
	{
		repairmanGroup.add(rep);
	}
	
	/**
	 * Hozzáad egy Csövet a játékhoz.
	 * @param p Cső
	 */
	public void addPipe(Pipe p)
	{
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
	}
	
	/**
	 * Növeli a pontokat a Ciszternába befolyt meg a Csövekből és Pumpákból elfolyt víz mennyisége alapján.
	 */
	public void calculatePoints()
	{
		for(int i = 0;i<cisterns.size();i++)
		{
			repairmanPoints+= cisterns.get(i).measureAndResetWaterFlown();
		}
		
		for(int i = 0; i<saboteurPointSources.size();i++)
		{
			saboteurPoints+=saboteurPointSources.get(i).measureAndResetLeakedWaterAmount();
		}
	}
	
	/**
	 * Hozzáad egy pumpát a játékhoz.
	 * @param p Pumpa
	 */
	public void addPump(Pump p)
	{
		saboteurPointSources.add(p);
		gameElements.add(p);//Ez kell-e??
		timer.addPump(p);
	}
	
	/**
	 * Minden Ciszternának szól hogy létrehozhatnak új csövet.
	 * Ha létrejön új cső azt a Game eltárolja.
	 */
	public void newPipe()
	{
		
		for(int i =0;i<cisterns.size();i++)
		{
			Pipe p = cisterns.get(i).newPipe();
			p.setName("createdPipe"+(i+1));
			if(p!=null)
			{
				addPipe(p);
			}
		}
	}

	/**
	 * Az osztály fontosabb attribútumait összefűzve adja vissza egy String-gé
	 * @return String
	 */
	@Override
	public String toString()
	{
		return remainingRounds+" "+repairmanPoints+" "+saboteurPoints+" "+slimey+" "+sticky;
	}

	/**
	 * Visszaadja a soron lévő játékost
	 */
	public Character getCurrentCharacter(){
		return currentCharacter;
	}

	public void setCurrentCharacter(Character c){
		this.currentCharacter=c;
	}

	/** Hátralevő körök beállítása */
	public void setTurns(int turns)
	{
		this.remainingRounds = turns;
	}

	/** Hátralevő körök beállítása */
	public int getTurns()
	{
		return this.remainingRounds;
	}

	public int getSaboteurPoints(){
		return saboteurPoints;
	}

	public int getRepairmanPoints(){
		return repairmanPoints;
	}
}
