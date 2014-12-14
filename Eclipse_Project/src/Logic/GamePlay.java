package Logic;
import java.io.Serializable;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class GamePlay.
 */
@SuppressWarnings("serial")
public class GamePlay implements Serializable{

	/** The score of the game. */
	private int score;

	/** The board. */
	private Board board;

	/** The level. */
	private Level level;

	/** The movements left. */
	private int movementsLeft;

	/** The rules. */
	private RuleEngine rules;

	private Position successfullSwapLog[];
	private Lokum swappedObject1 = new Lokum("red rose"), swappedObject2= new Lokum("red rose");
	

	private Player player;

	/**
	 * Instantiates a new game play.
	 *
	 * @param level
	 *            : The Level played
	 * @requires:
	 * @modifies:
	 * @ensures:
	 */
	public GamePlay(Level level) {
		rules = RuleEngine.getInstance();
		this.level = level;
		score = 0;
		movementsLeft = level.getPossibleMovements();
		board = level.getBoard();
		successfullSwapLog = new Position[2];
		// TODO Auto-generated constructor stub
	}
	
	public void setPlayer(Player player){
		this.player = player;
	}
	
	public void setScore(int score){
		this.score = score;
	}

	/**
	 * Gets the score.
	 *
	 * @requires: repOk
	 * @ensures: current score data is returned
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets the level id.
	 *
	 * @requires: level is non null
	 * @ensures: is of the played level is returned
	 * @return the level id
	 */
	public int getLevelId() {
		return level.getLevelId();
	}

	/**
	 * Gets the movements left.
	 *
	 * @requires movementsLeft exists and initialized
	 * @ensures: current movementsLeft field is returned
	 */
	public int getMovementsLeft() {
		return movementsLeft;
	}

	/**
	 * Tries to swap the objects in two cells specified by their coordinates. If
	 * swap becomes successful, returns true. Otherwise, returns false.
	 *
	 * @requires <ul>
	 *           <li>board field is non-null</li>
	 *           <li>rules field is non-null</li>
	 *           <li>RuleEngine has instance predicate methods
	 *           gameEndedByMovements(int) and isSwappable(Board, int, int,
	 *           int).
	 *           <li>board field is non-null, contains non-null cells with
	 *           non-null ChewyObjects</li>
	 *           <li>Board has an instance method fillCellAt(int, int,
	 *           ChewyObject)</li>
	 *           <li>integer field movementsLeft is initialized</li>
	 *           </ul>
	 * @modifies if returned true;
	 *           <ul>
	 *           <li>board field</li>
	 *           <li>level field</li>
	 *           <li>movementsLeft field</li>
	 *           </ul>
	 * @ensures If swap returns true, then ChewyObjects of the cells at the
	 *          board positions (x1, y1), (x2, y2) is exchanged.<br>
	 *          Otherwise, nothing will be changed.
	 */

	public boolean swap(int x1, int y1, int x2, int y2) {
		if (rules.gameEndedByMovements(movementsLeft)) {
			return false;
		}

		if (!rules.isSwappable(board, x1, y1, x2, y2)) {
			return false;
		}

		successfullSwapLog[0] = new Position(x1, y1);
		successfullSwapLog[1] = new Position(x2, y2);
		
		Cell cell1 = board.cellAt(x1, y1);
		Cell cell2 = board.cellAt(x2, y2);
		swappedObject1 = (Lokum)cell1.getCurrentObject();
		swappedObject2  = (Lokum)cell2.getCurrentObject();
		
		score += rules.getSpecialMoveScore(x1, y1, x2, y2, board, swappedObject1, swappedObject2);
		score += rules.getUsingScore(x1, y1, board, swappedObject1);
		score += rules.getUsingScore(x2, y2, board, swappedObject2);
		
		ChewyObject temp = cell1.getCurrentObject();
		board.fillCellAt(x1, y1, cell2.getCurrentObject());
		board.fillCellAt(x2, y2, temp);
		movementsLeft--;
		return true;
	}

	/**
	 * Returns the level played.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * 
	 * @requires GamePlay has a field named Level of type Level
	 * @modifies: level
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}


	
	
	
public void initBoard() {
		
		while(true){
			while (isThereNothing()) {
				
				// Checking if the board is playable
				fillAllNothingsRandomly();
			
				// generate scaling matrix
				MatchingScaleInformer[][] scaleMatrix = generateScaleMatrix();

				// erase all matched cells
				eraseAllMatches(scaleMatrix);


				// drop objects if necessary
				dropAll();
			
				
			}
			
			if(isThereAvailableMove()) break;
			else {
				
				System.out.println("There is no available move. New board is initilized.");
				createNewBoard();
			}
		}
		
		


	}

	/**
	 * Generate scale matrix.
	 *
	 * @return the matching scale informer[][]
	 */
	public MatchingScaleInformer[][] generateScaleMatrix() {

		MatchingScaleInformer[][] scaleMatrix = new MatchingScaleInformer[board
				.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				scaleMatrix[j][i] = rules.getMatchingScaleInformer(board, i, j,
						board.cellAt(i, j).getCurrentObject());
			}
		}
		return scaleMatrix;
	}

	/**
	 * Erase all matches.
	 *
	 * @param scaleMatrix
	 *            the scale matrix
	 */
	public void eraseAllMatches(MatchingScaleInformer[][] scaleMatrix) {
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				MatchingScaleInformer currentMSI = scaleMatrix[j][i];
				if (rules.shouldErased(currentMSI)) {
					eraseForNormal(currentMSI, i, j);

				}
				
			}
		}
	}


	private void eraseForNormal(MatchingScaleInformer currentMSI, int i, int j) {
		String type;
		if (board.cellAt(i, j).getCurrentObject() instanceof Lokum) {
			Lokum lokum = (Lokum) board.cellAt(i, j).getCurrentObject();
			type = lokum.getSpecialType();
		}else{
			type = "Nothing";
		}
		Lokum temp;
		if(swappedObject1.isSpecial()){
			temp = swappedObject2 ;
		}else{ 
			temp = swappedObject1;
		}
		

		try {
			if (type.equalsIgnoreCase("Regular")) {
				board.fillCellAt(i, j, new Nothing());
			} else if (type.equalsIgnoreCase("vertical striped")) {
				clearColumn(j);
			} else if (type.equalsIgnoreCase("horizontal striped")) {
				clearRow(i);
			} else if (type.equals("Wrapped")) {
				clearArea(i, j);
			} else if (type.equals("Color Bomb")) {
				clearSameObjects(temp);
			}
		} catch (Exception e) {
			
		}
		Position p = new Position(i, j);
		if (recentlySwapped(p)) {
			
			Lokum swapped = null;
			if (p.isSamePlace(successfullSwapLog[0])) {
				swapped = swappedObject2;
			} else {
				swapped = swappedObject1;
			}
			
			int specialityCode = rules.getSpecialityCode(currentMSI);
			if (rules.isSpecialCase(specialityCode)) {
				score += rules.getRelevantCreationScore(specialityCode);
				board.fillCellAt(i, j,
						rules.getRelevantSpecialObject(swapped.getType(), specialityCode));
			}
		}

	}
	

	private void clearSameObjects(Matchable m) {
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				Lokum current = (Lokum)board.cellAt(i, j).getCurrentObject();
					if(m.isMatched(current))
						board.fillCellAt(i, j, new Nothing());
			}
		}
	}

	private void clearArea(int x, int y) {

		for (int i = x-2; i < x+2; i++) {
			for (int j =  y-2; j < y+2; j++) {
					board.fillCellAt(i, j, new Nothing());
			}

		}	
	}


	private void clearRow(int y) {
		for (int x = 0; x < board.getWidth(); x++) {
			board.fillCellAt(x, y, new Nothing());
		}
	}

	private void clearColumn(int x) {
		for (int y = 0; y < board.getHeight(); y++) {
			board.fillCellAt(x, y, new Nothing());
		}
	}
	//end of eraseForSpecial helper definitions
	

	private boolean recentlySwapped(Position p) {
		return p.isSamePlace(successfullSwapLog[0])
				|| p.isSamePlace(successfullSwapLog[1]);
	}


	/**
	 * Calculate score.
	 *
	 * @param msi
	 *            the msi
	 * @return the int
	 */
	public int calculateScore(MatchingScaleInformer[][] msi) {
		int eraseCount = 0;
		
		for (int i = 0; i < msi.length; i++) {
			for (int j = 0; j < msi[0].length; j++) {
				if (rules.shouldErased(msi[i][j])) {
					eraseCount++;
				}
				score += rules.getStandardScore(eraseCount, msi[i][j]);
			}
		}
		return score;
	}

	public void dropAll() {
		for (int i = board.getWidth() - 1; i > -1; i--) {
			for (int j = board.getHeight() - 1; j > -1; j--) {
				if (board.cellAt(i, j).getCurrentObject().getType()
						.equals("Empty")) {
					int temp = j;
					while (board.cellAt(i, temp).getCurrentObject().getType()
							.equals("Empty")
							&& temp > 0) {
						temp--;
					}
					if (!board.cellAt(i, temp).getCurrentObject().getType()
							.equals("Empty")) {
						ChewyObject co = board.cellAt(i, temp).getCurrentObject();
						board.fillCellAt(i, temp, new Nothing());
						board.fillCellAt(i, j, co);
						temp = j;
					}
				}
			}
		}

	}

	public boolean isThereAvailableMove() {
		for (int i = 0; i < board.getWidth() - 1; i++) {
			for (int j = 0; j < board.getHeight() - 1; j++) {
				if (rules.isSwappable(board, i, j, i + 1, j))
					return true;
				if (rules.isSwappable(board, i, j, i + 1, j + 1))
					return true;
				if (rules.isSwappable(board, i, j, i, j + 1))
					return true;
			}
		}

		return false;
	}

	public boolean isThereNothing() {
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject().getType()
						.equalsIgnoreCase("empty")) {
					return true;
				}
			}
		}
		return false;
	}

	public void fillAllNothingsRandomly() {
		String str[] = Lokum.possibleTypes;
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject().getType()
						.equalsIgnoreCase("empty")) {
					Lokum currentLokum = new Lokum(
							str[new Random().nextInt(str.length)]);
					board.fillCellAt(i, j, currentLokum);
				}

			}

		}
	}
	
	public boolean isGameOver(){
		return rules.gameEndedByMovements(movementsLeft);
	}
	
	public void createNewBoard(){
		String str[] = Lokum.possibleTypes;
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
					Lokum currentLokum = new Lokum(
							str[new Random().nextInt(str.length)]);
					board.fillCellAt(i, j, currentLokum);
			}

		}
		
		
	}


	/**
	 * Rep ok.
	 *
	 * @return true, if successful
	 */
	public boolean repOk() {
		// inspects whether the constructor did its job
		if (movementsLeft < 0)
			return false;

		if (level == null || !level.repOk())
			return false;

		if (board == null || !board.repOk())
			return false;

		return true;
	}

	/**
	 * Movements left.
	 *
	 * @return the string
	 */
	public String movementsLeft() {
		return "Movements Left: " + movementsLeft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return "\nGAME INFO \n" + "Game Score: " + score + "\n" + "Level ID: "
				+ level.getLevelId() + "\n" + "Movements Left: "
				+ movementsLeft + "\n" + "is board ok? " + board.repOk();
	}

	public Player getPlayer() {
		return player;
	}

}
