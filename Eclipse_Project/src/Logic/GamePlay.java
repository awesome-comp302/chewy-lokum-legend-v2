package Logic;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;


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
	private int specialMovementsLeft;
	private Move lastMove;
	
	/** The rules. */
	private RuleEngine rules;

	private Position successfullSwapLog[];
	
	
	private Player player;
	private BoardUpdater updater;
	


	/**
	 *private field for storing listeners 
	 */
	private ArrayList<GameUpdateListener> listeners;
	
	private int timeLeft;
	private Timer timer;
	
	/**
	 * Instantiates a new game play.
	 *
	 * @param level
	 *            : The Level will be played
	 */
	public GamePlay(Level level) {
		rules = RuleEngine.getInstance();
		this.level = level;
		score = 0;
		movementsLeft = level.getPossibleMovements();
		board = level.getBoard();
		player = new Player();
		timeLeft = level.getTime();
		specialMovementsLeft = level.getSpecialMoveCount();
		ActionListener runner = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!isGameOver()){
					timeLeft--;
	
					publishGame(UpdateType.timeLabel);
				}
				else {
					publishGame(UpdateType.showEndGame);
				}
			}
		};
		
		
		Timer timer = new Timer(1000,runner);
		timer.setRepeats(true);
		if (level.hasTimer()) {
			timer.start();
		}
		
		//successfullSwapLog = new Position[2];
		
		/*aTask = new TimerTask() {
			public void run() {
				timeLeft--;
				publishGame(UpdateType.timeLabel);
			}
		};
		
		timer = new Timer();
		timer.scheduleAtFixedRate(aTask, 1000, 1000);*/
		
		
	}
	
	public void countDownTimer(){
		timeLeft--;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	public void increaseTime() {
		timeLeft += 5;
	}
	
	public void addListener(GameUpdateListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<>();
		}
		listeners.add(listener);
	}
	
	/**
	 * Sets a new player to the GamePlay
	 * @param player
	 */
	public void setPlayer(Player player){
		this.player = player;
	}
	
	/**
	 * Sets a new score to the GamePlay
	 * @param score
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/**
	 * @return current board
	 */
	public Board getBoard(){
		return board;
	}

	/**
	 * Gets the score.
	 *
	 * @requires repOk
	 * @ensures current score data is returned
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Gets the level id.
	 *
	 * @requires level is non null
	 * @ensures is of the played level is returned
	 * @return the level id
	 */
	public int getLevelId() {
		return level.getLevelId();
	}

	/**
	 * Gets the movements left.
	 *@requires movementsLeft exists and initialized
	 *@ensures current movementsLeft field is returned
	 */
	public int getMovementsLeft() {
		return movementsLeft;
	}

	public Move getLastMove() {
		return lastMove;
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
	 *           <li>level.board is non-null, contains non-null cells with
	 *           non-null ChewyObjects</li>
	 *           <li>Board has an instance method fillCellAt(int, int,
	 *           ChewyObject)</li>
	 *           <li>integer field movementsLeft is initialized</li>
	 *           </ul>
	 * @modifies if returned true;
	 *           <ul>
	 *           <li>level.board</li>
	 *           <li>movementsLeft field</li>
	 *           </ul>
	 * @ensures If swap returns true, then ChewyObjects of the cells at the
	 *          board positions (x1, y1), (x2, y2) are exchanged.<br>
	 *          Otherwise, nothing will be changed.
	 */
public boolean swap(int x1, int y1, int x2, int y2) {
		
		Move move = new Move(x1, y1, x2, y2, this, false);
		
		SwapRules swapRules = rules.getSwapRules(move);		
		if (!swapRules.isValid(this, move)) {
			return false;
		}
		
		
		//ScoringRules scoringRules = rules.getScoringRules(move);
		lastMove = move;
		//score += scoringRules.getSwapScore(move);
		
		Cell cell1 = board.cellAt(move.getPosition1().getX(), move.getPosition1().getY());
		Cell cell2 = board.cellAt(move.getPosition2().getX(), move.getPosition2().getY());

		ChewyObject temp = cell1.getCurrentObject();
		board.fillCellAt(move.getPosition1().getX(), move.getPosition1().getY(), cell2.getCurrentObject());
		board.fillCellAt(move.getPosition2().getX(), move.getPosition2().getY(), temp);
		movementsLeft--;
		publishGame(UpdateType.movementLeftLabel);
		publishGame(UpdateType.boardPanel);
		return true;
		
		
	}

	
	


		
	/**
	 *@requires RuleEngine generates non null objects that works correctly based on specified rules, level, level.board are non null and level.board have non null cells
	 *@modifies score, specialMovementsLeft, movementsLeft, level.board
	 *@ensures if specialMove is valid, 
	 */
	public boolean specialSwap(int x1, int y1, int x2, int y2) {
		
		Move move = new Move(x1, y1, x2, y2, this, true);		
		SwapRules swapRules = rules.getSwapRules(move);
		
		if (!swapRules.isValid(this, move)) {
			return false;
		}
		
		lastMove = move;
		Cell cell1 = board.cellAt(x1, y1);
		Cell cell2 = board.cellAt(x2, y2);
		
		ChewyObject temp = cell1.getCurrentObject();
		
		board.fillCellAt(x1, y1, cell2.getCurrentObject());
		board.fillCellAt(x2, y2, temp);
		
		movementsLeft--;
		publishGame(UpdateType.movementLeftLabel);
		specialMovementsLeft--;
		publishGame(UpdateType.specialMovementLeftLabel);
		return true;
	}
	
	
	
	public int getSpecialMovementsLeft() {
		return specialMovementsLeft;
	}

	/**
	 * Returns the level played.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Sets the level.
	 * @requires GamePlay has a field named Level of type Level
	 * @modifies level
	 * @param level
	 */
	public void setLevel(Level level) {
		this.level = level;
	}


	public void updateBoard() {
		
		
		updater = new BoardUpdater(this, rules);
		updater.eraseAll();
		publishGame(UpdateType.boardPanel);
		
		while(updater.stillToDo()) {
			
			updater.eraseAll();
			publishGame(UpdateType.boardPanel);
			score += updater.getScoreIncrease();
			updater.resetScoreIncrease();
			publishGame(UpdateType.scoreLabel);
			
			updater.dropAll();
			publishGame(UpdateType.boardPanel);
			
			updater.fillEmptyCells();
			publishGame(UpdateType.boardPanel);

			updater.eraseAll();
			publishGame(UpdateType.boardPanel);
			score += updater.getScoreIncrease();
			updater.resetScoreIncrease();
			publishGame(UpdateType.scoreLabel);
			
			timeLeft += updater.getTimeIncrease();
			updater.resetTimeIncrease();
			
			if (isGameOver()) {
				publishGame(UpdateType.showEndGame);
			}
			
		}
		
		
		
		
	}
	

	void publishGame(UpdateType type) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onGameUpdate(this, type);
		}		
	}
	
	boolean anyListeners() {
		return !(listeners == null || listeners.isEmpty());
	}

	public void initBoard() {

		updater = new BoardUpdater(this, rules);
		
		
		while(updater.stillToDo()){ 
			updater.fillEmptyCells();
			
			updater.eraseAll();
		}
		
		publishGame(UpdateType.all);

		
	}

	/**
	 * Generate scale matrix.
	 *
	 * @return the matching scale informer[][]
	 */
	public MatchingScaleInformer[][] generateScaleMatrix() {

		MatchingScaleInformerFactory factory = MatchingScaleInformerFactory.getInstance();
		MatchingScaleInformer[][] scaleMatrix = new MatchingScaleInformer[board
				.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				scaleMatrix[j][i] = factory.getMatchingScaleInformer(board, i, j,
						board.cellAt(i, j).getCurrentObject());
			}
		}
		return scaleMatrix;
	}

	/**
	 * Erase all matches.
	 *
	 * @param scaleMatrix the scale matrix
	 */

	

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
		//int eraseCount = 0;
		
		for (int i = 0; i < msi.length; i++) {
			for (int j = 0; j < msi[0].length; j++) {
				/*if (rules.shouldErased(msi[i][j])) {
					eraseCount++;
				}
				score += rules.getStandardScore(eraseCount, msi[i][j]);*/
			}
		}
		return score;
	}

	/**
	 *@requires
	 *board is non-null,
	 *cells of the board are non-null,
	 *Board has instance methods cellAt(int, int) and fillCellAt(int, int, ChewyObject),
	 *Nothing and Lokum inherits ChewyObject.
	 *Default type of the Nothing object is String "empty".
	 *
	 *@modifies
	 *level, board.
	 *@ensures
	 *There is no cell in the board whose bottom contains a Nothing abject.
	 */
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
				/*if (rules.isSwappable(board, i, j, i + 1, j))
					return true;
				if (rules.isSwappable(board, i, j, i + 1, j + 1))
					return true;
				if (rules.isSwappable(board, i, j, i, j + 1))
					return true;*/
			}
		}

		return false;
	}
	
	

	/**
	 * @requires
	 * board is non-null, 
	 * board's all cells returned are non-null and contain non-null objects that are that has
	 * getType methods returning type as String. 
	 * Nothing objects type is "empty", ignoring case.
	 * @ensures
	 * returned true if there is any Nothing object(any empty cell) in the board, false o/w.  
	 */
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

	/**
	 * @requires
	 * board is non-null, 
	 * board's all cells returned are non-null and contain non-null objects that are that has
	 * getType methods returning type as String. 
	 * Nothing objects type is "empty", ignoring case.
	 * @modifies
	 * board, level
	 * @ensures
	 * all cells are filled with ChewyObjects that are not Nothing.  
	 */
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
		//return rules.gameEndedByMovements(movementsLeft);
		if (movementsLeft <= 0) {
			return true;
		}
		
		if (timeLeft == 0) {
			return true;
		}
		return false;
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
	 * Checks for correct initialization.
	 *
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
	 * @return movementsLeft as a String for testing purposes
	 */
	public String movementsLeft() {
		return "Movements Left: " + movementsLeft;
	}

	/**
	 * @return the info of a GamePlay instance as a formated String for testing purposes. 
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
