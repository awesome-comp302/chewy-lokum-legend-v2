package Logic;

import java.util.HashMap;

/**
 * @author Berk
 *
 */
public class LevelData {
	
	private static LevelData ld;
	
	private HashMap<Integer, int[]> idsToLevelsMap = new HashMap<Integer, int[]>();
	private HashMap<Integer, boolean[]> idsToLevelsMapMode = new HashMap<Integer, boolean[]>();
	
	//{SpLokums, Timer, TimerLokum, SpMove}
	private boolean[] level1Mode = new boolean[]{false, false, false, false};
	private boolean[] level2Mode = new boolean[]{true, false, false, false};
	private boolean[] level3Mode = new boolean[]{true, true, false, false};
	private boolean[] level4Mode = new boolean[]{true, true, true, false};
	private boolean[] level5Mode = new boolean[]{true, true, true, true};
	
	//{PassingScore, PossibleMovement, Timer, PossibleSpMove}
	private int[] level1 = new int[]{1000000, 50, 0, 0};
	private int[] level2 = new int[]{2000000, 35, 0, 0};
	private int[] level3 = new int[]{2000000, 35, 90, 0};
	private int[] level4 = new int[]{2500000, 30, 60, 0};
	private int[] level5 = new int[]{3000000, 20, 60, 5};
	
	private Level level;
	
	
	public static LevelData getInstance(){
		if(ld == null)
			ld = new LevelData();
		
		return ld;
	}

	private LevelData() {
		idsToLevelsMapMode.put(1, level1Mode);
		idsToLevelsMapMode.put(2, level2Mode);
		idsToLevelsMapMode.put(3, level3Mode);
		idsToLevelsMapMode.put(4, level4Mode);
		idsToLevelsMapMode.put(5, level5Mode);
		
		idsToLevelsMap.put(1, level1);
		idsToLevelsMap.put(2, level2);
		idsToLevelsMap.put(3, level3);
		idsToLevelsMap.put(4, level4);
		idsToLevelsMap.put(5, level5);
	}
	
	public Level getLevelById(int id){
		//level = new Level(idsToLevelsMap.get(id), idsToLevelsMapMode.get(id));
		
		
		return level;
	}
	
	
	public boolean repOk(){
		for(int i = 1; i<6; i++){
			if(idsToLevelsMapMode.get(i) == null) return false;
			if(idsToLevelsMap.get(i) == null) return false;
		}
		
		return true;
	}
	
	
	
	
}
