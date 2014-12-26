package Logic;
import java.util.ArrayList;


public class Level {
	private int passingScore;
	private int levelId;
	private Board board;
	private static ArrayList<Integer> allLevelIds = new ArrayList<Integer>();
	private int possibleMovements;


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

	public Board getBoard() {
		return board; 
	}

	public int getLevelId() {
		return levelId;
	}

	public int getPassingScore() {
		return passingScore;
	}

	public int getPossibleMovements() {
		return possibleMovements;
	}
	
	public static void clearLevelIDs(){
		allLevelIds = new ArrayList<Integer>();
	}
	
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
