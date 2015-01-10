package Logic;

import java.util.HashMap;

/**
 * @author Berk
 *
 */
public class LevelData {
	
	private static LevelData ld;
	
	private HashMap<Integer, int[]> idsToLevelVars = new HashMap<Integer, int[]>();
	private HashMap<Integer, boolean[]> idsToLevelModes = new HashMap<Integer, boolean[]>();	
	
	
	/**
	 * @return Instance of Class
	 */
	public static LevelData getInstance(){
		if(ld == null)
			ld = new LevelData();
		
		return ld;
	}


	/**
	 * @param id The id that will call the associated level data
	 * @return A level according to given id.
	 */
	public Level getLevelById(int id){
		return new Level(idsToLevelVars.get(id), idsToLevelModes.get(id));
	}

	private LevelData() {
		createModesForLevels();
		createVarsForLevels();
	}
	
	
	
	private int[] createLevelVar(int PassingScore, int PossibleMovement, int Timer, int PossibleSpMove, int levelID){
		return new int[]{PassingScore, PossibleMovement, Timer, PossibleSpMove, levelID};
	}
	
	private boolean[] createLevelMode(boolean SpLokums, boolean Timer, boolean TimerLokum, boolean SpMove){
		return new boolean[]{SpLokums, Timer, TimerLokum, SpMove};
	}
	
	private void createModesForLevels(){
		idsToLevelModes.put(1, createLevelMode(false, false, false, false));
		idsToLevelModes.put(2, createLevelMode(true, false, false, false));
		idsToLevelModes.put(3, createLevelMode(true, true, false, false));
		idsToLevelModes.put(4, createLevelMode(true, true, true, false));
		idsToLevelModes.put(5, createLevelMode(true, true, true, true));
		
	}
	
	private void createVarsForLevels(){
		idsToLevelVars.put(1, createLevelVar(1000000, 50, -1, 0, 1));
		idsToLevelVars.put(2, createLevelVar(2000000, 35, -1, 0, 2));
		idsToLevelVars.put(3, createLevelVar(2000000, 35, 90, 0, 3));
		idsToLevelVars.put(4, createLevelVar(2500000, 30, 60, 0, 4));
		idsToLevelVars.put(5, createLevelVar(3000000, 90, 900, 65, 5));
		
	}
	
	
	
	/**
	 * Checks whether all data of the class is set legally 
	 */
	public boolean repOk(){
		for(int i = 1; i<6; i++){
			if(idsToLevelVars.get(i) == null) return false;
			if(idsToLevelModes.get(i) == null) return false;
		}
		return true;
	}

	/**
	 * @return RepOk result of class for testing purposes.
	 * 
	 */
	@Override
	public String toString() {
		return "LevelData [repOk()=" + repOk() + "]";
	}
	
	
	
	
	
	
	
}
