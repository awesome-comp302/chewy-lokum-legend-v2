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
	private static int lastUnlockedLevel;


	/**
	 * -Going to be removed-
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
		
		if(lastUnlockedLevel < levelId) lastUnlockedLevel = levelId;
		
	}
	
	public static int getLastUnlockedLevel(){
		if(lastUnlockedLevel < 1) lastUnlockedLevel = 1;
		return lastUnlockedLevel;
	}
	
	
	
	
	/**
	 * @param
	 * levelVars Level variables which will determine the instances of the class.
	 * levelMode Level modes which will determine the active modes.
	 * 
	 * 
	 * @modifies
	 * passingScore, levelId, board, allLevelIds, possibleMovements, time, specialMoveCount, levelModes
	 * @throws
	 * IllegalArgumentException, if one of the setters request it.
	 * 
	 */
	public Level(int[] levelVars, boolean[] levelMode) throws IllegalArgumentException{
		setLevelModes(levelMode);
		setPassingScore(levelVars);
		setPossibleMovements(levelVars);
		setSpecialMoveCount(levelVars);
		setTime(levelVars);
		setLevelId(levelVars);
		
		board = new Board(6,6);
		
	}

	public void setBoardSize(int x, int y){
		board = new Board(x, y);
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
	
	/**
	 * @return the current time state of level.
	 */
	public int getTime() {
		return time;
	}
	
	
	/**
	 * @return the current special move state
	 */
	public int getSpecialMoveCount() {
		return specialMoveCount;
	}
	

	/**
	 * -Going to be removed-
	 * Clears the LevelIDs list's history 
	 */
	public static void clearLevelIDs(){
		allLevelIds.clear();
	}


	/**
	 * @modifies the passing score of level
	 * @throws IllegalArgumentException - if the passing score is not positive
	 */
	private void setPassingScore(int[] levelVars) {
		passingScore = levelVars[0];
		
		if (passingScore <= 0) {
			throw new IllegalArgumentException("Passing score should be positive");
		}
	}

	/**
	 * @modifies the possible move number of level
	 * @throws IllegalArgumentException - if the possible move number is not positive
	 */
	private void setPossibleMovements(int[] levelVars) {
		possibleMovements = levelVars[1];
		
		if (possibleMovements <= 0) {
			throw new IllegalArgumentException("Possible moves should be positive");
		}
	}
	

	/**
	 * @modifies the timer of level
	 * @throws IllegalArgumentException - if the timer is activated but it is not positive
	 */
	private void setTime(int[] levelVars) {
		time = levelVars[2];
		
		if (time <= 0 && hasTimer()) {
			throw new IllegalArgumentException("Timer should be positive");
		}
	}
	
	
	/**
	 * @modifies the special move number of level
	 * @throws IllegalArgumentException - if the special move is activated but it is not positive
	 */
	private void setSpecialMoveCount(int[] levelVars) {
		specialMoveCount = levelVars[3];
		
		if (specialMoveCount <= 0 && hasSpecialMove()) {
			throw new IllegalArgumentException("Special moves should be positive");
		}
	}
	
	/**
	 * @modifies the level id of level
	 */
	private void setLevelId(int[] levelVars) {
		this.levelId = levelVars[4];
	}
	
	
	/**
	 * @modifies the level modes of level
	 */
	private void setLevelModes(boolean[] levelModes) {
		this.levelModes = levelModes;
	}
	
	
	/**
	 * Reduces special move number by one.
	 * @modifies Special move number
	 */
	public void useSpecialMove(){
		specialMoveCount--;
	}
	
	/**
	 * Reduces possible move number by one.
	 * @modifies possible move number
	 */
	public void useMove(){
		possibleMovements--;
	}
	
	/**
	 * Increases timer by five.
	 * @modifies Timer
	 */
	public void boostTime(){
		time += 5;
	}
	
	/**
	 * Reduces timer by one.
	 * @modifies Timer
	 */
	public void countDownTimer(){
		time--;
	}

	
	/**
	 * @return True, if special lokum mode is activated.
	 */
	public boolean hasSpecialLokums(){
		return levelModes[0];
	}
	
	/**
	 * @return True, if timer mode is activated.
	 */
	public boolean hasTimer(){
		return levelModes[1];
	}
	
	/**
	 * @return True, if timer lokum mode is activated.
	 */
	public boolean hasTimerLokum(){
		return levelModes[2];
	}
	
	/**
	 * @return True, if special move mode is activated.
	 */
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
		
		if (levelModes == null)
			return false;
		
		if(board == null || !board.repOk())
			return false;
		
		if (levelModes == null)
			return false;
		
		
		return true;
	}

	/**
	@return the data of the board as a formatted String.
	 */
	@Override
	public String toString() {

		return  "\nLEVEL INFO \n"+
				"=======================\n"+
				"Level Mode: \n"+
				"------\n"+
				"Special lokum mode: " +hasSpecialLokums()+"\n"+
				"Timer mode: " +hasTimer()+"\n"+
				"Timer lokum mode: " +hasTimerLokum()+"\n"+
				"Special move mode: " +hasSpecialMove()+"\n"+
				"------\n"+
				"Level instances: \n"+
				"------\n"+
				"Level ID: " +levelId+ "\n"+
				"Passing Score: " +passingScore+ "\n"+
				"Possible Movements: " +possibleMovements+ "\n"+
				"Current timer status: " +time+ "\n"+
				"Special number count: " +specialMoveCount+ "\n"+
				"=======================\n"+
				board.status();
	}




	
	public boolean[] getLevelModes() {
		// TODO Auto-generated method stub
		return levelModes;
	}


	
	
}
