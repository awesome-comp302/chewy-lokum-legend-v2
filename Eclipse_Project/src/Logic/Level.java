package Logic;
import java.util.ArrayList;


public class Level {
	private int passingScore;
	private int levelId;
	private Board board;
	private static ArrayList<Integer> allLevelIds = new ArrayList<Integer>();
	private int possibleMovements;
	private int time;
	private int specialMoveCount;
	private boolean[] levelModes;


	/**
	 * Note: Some optimizations should be done to constructor.
	 * @requires
	 * board class exists and accessible from the Level, 
	 * allLevelIds ArrayList is non-null
	 * @modifies
	 * passingScore, levelId, board, allLevelIds, possibleMovements
	 * @ensures
	 * An IllegalArgumentException is thrown if passing score is negative or levelId is alreadey taken by another Level instance.
	 * Constructor arguments possibleMovements, passingScore, board, levelId are assigned to the relevant fields.
	 * allLevelIds arrayList is extended by the given levelId.
	 * 
	 */
	public Level(int passingScore, int possibleMovements, Board board, int levelId) throws IllegalArgumentException{
		if (passingScore <= 0) {
			throw new IllegalArgumentException("passing score should be positive");
		}
		if (allLevelIds.contains(levelId)) {
			throw new IllegalArgumentException("ID has been chosen");
		}
		this.possibleMovements = possibleMovements;
		this.passingScore = passingScore;
		this.levelId = levelId;
		allLevelIds.add(levelId);
		this.board = board;
	}
	
	public Level(int[] levelVars, boolean[] levelMode) throws IllegalArgumentException{
		setPassingScore(levelVars);
		setPossibleMovements(levelVars);
		setSpecialMoveCount(levelVars);
		setTime(levelVars);
		setLevelModes(levelMode);
		
		board = new Board(10,10);
		
	}

	
	
	/**
	 * 
	 * @return the board of the level
	 */
	public Board getBoard() {
		return board; 
	}

	/**
	 * 
	 * @return the id of the level
	 */
	public int getLevelId() {
		return levelId;
	}

	/**
	 * 
	 * @return the passing score of the level
	 */
	public int getPassingScore() {
		return passingScore;
	}

	/**
	 * @return the maximum number of movements can be done in that level
	 */
	public int getPossibleMovements() {
		return possibleMovements;
	}
	
	
	public int getTime() {
		return time;
	}
	
	public int getSpecialMoveCount() {
		return specialMoveCount;
	}
	

	/**
	 * Clears the LevelIDs list's history 
	 */
	public static void clearLevelIDs(){
		allLevelIds = new ArrayList<Integer>();
	}


	private void setPassingScore(int[] levelVars) {
		this.passingScore = levelVars[0];
	}

	private void setPossibleMovements(int[] levelVars) {
		this.possibleMovements = levelVars[1];
	}
	
	private void setTime(int[] levelVars) {
		this.time = levelVars[2];
	}
	
	private void setSpecialMoveCount(int[] levelVars) {
		this.specialMoveCount = levelVars[3];
	}
	
	
	private void setLevelModes(boolean[] levelModes) {
		this.levelModes = levelModes;
	}
	
	
	public void useSpecialMove(){
		specialMoveCount--;
	}
	
	public void boostTime(){
		time += 5;
	}
	
	public void countDownTimer(){
		time--;
	}

	
	public boolean hasSpecialLokums(){
		return levelModes[0];
	}
	
	public boolean hasTimer(){
		return levelModes[1];
	}
	
	public boolean hasTimerLokum(){
		return levelModes[2];
	}
	
	public boolean hasSpecialMove(){
		return levelModes[3];
	}
	
	/**
	 * Checks whether all data of the class is set legally 
	 */
	public boolean repOk() {
		//inspects whether the constructor did its job
		if (passingScore <= 0)
			return false;
		
		if (possibleMovements < 0)
			return false;
		
		
		if (levelId < 0)
			return false;
		
		if (!allLevelIds.contains(levelId))
			return false;
		
		if(board == null || !board.repOk())
			return false;
		
		
		return true;
	}

	/**
	Returns the data of the board as a formatted String.
	 */
	@Override
	public String toString() {

		return  "\nLEVEL INFO \n"+
				"Level ID: " +levelId+ "\n"+
				"Passing Score: " +passingScore+ "\n"+
				"Possible Movements: " +possibleMovements+ "\n"+
				"**"+
				board.status() + "\n"+
				"**\n"+
				"Possible Movements: "  +  possibleMovements;
	}
}
