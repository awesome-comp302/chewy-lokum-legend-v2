package Logic;

import java.util.Random;

public class BoardUpdater {

	private int scoreIncrease;
	private GamePlay gp;
	private RuleEngine rules;
	private int timeIncrease;
	private boolean cbsCollected;

	public BoardUpdater(GamePlay gp, RuleEngine rules) {
		this.gp = gp;
		scoreIncrease = 0;
		this.rules = rules;
		timeIncrease = 0;
		cbsCollected = false;
	}

	/**
	 * returns false if scoreIncrease is negative
	 */
	public boolean repOk() {
		if (scoreIncrease < 0) {
			return false;
		}
		return true;
	}

	public int getScoreIncrease() {
		return scoreIncrease;
	}

	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.rules is non null
	 *           and works properly
	 * @modifies gp.level.board
	 * @ensures No object left in the gp.level.board whose downside is empty
	 */
	public void dropAll() {
		Board board = gp.getLevel().getBoard();
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
						ChewyObject co = board.cellAt(i, temp)
								.getCurrentObject();
						board.fillCellAt(i, temp, new Nothing());
						board.fillCellAt(i, j, co);
						temp = j;
					}
				}
			}
		}
	}

	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have
	 *           non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board
	 * @ensures there are valid swap in the game
	 */
	public void shuffle() {

	}

	public boolean isThereAvailableMove() {
		Board board = gp.getLevel().getBoard();
		for (int i = 0; i < board.getWidth() - 1; i++) {
			for (int j = 0; j < board.getHeight() - 1; j++) {

				Move move = new Move(i, j, i + 1, j, gp, false);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;

				move = new Move(i, j, i + 1, j, gp, true);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i + 1, j + 1, gp, false);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;

				move = new Move(i, j, i + 1, j + 1, gp, true);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;

				move = new Move(i, j, i, j + 1, gp, false);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;

				move = new Move(i, j, i, j + 1, gp, true);
				if (rules.getSwapRules(move).isValid(gp, move))
					return true;
			}
		}
		return false;
	}

	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have
	 *           non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board, scoreIncrease
	 * @ensures relevant deletions in the game.level.board were done,
	 *          scoreIncrease is updated based on rules
	 */
	public void eraseAll() {
		MatchingScaleInformer[][] scaleMatrix = generateScaleMatrix();
		eraseForSpecial();
		if (gp.getLastMove() != null && !cbsCollected) {
			collectCBs();
		}
		
		eraseForNormal(scaleMatrix);
		
	}
	
	

	private void collectCBs() {
		//System.out.println("Ahan");
		
		Move move = gp.getLastMove();
		if (move.getSpecialType1().equals("Color Bomb")) {
			gp.getBoard().fillCellAt(move.getPosition2().getX(),
					move.getPosition2().getY(), new Nothing());
		}
		
		if (move.getSpecialType2().equals("Color Bomb")) {
			gp.getBoard().fillCellAt(move.getPosition1().getX(),
					move.getPosition1().getY(), new Nothing());
		}
		
		cbsCollected = true;
	}

	private void addTimeToGame(ChewyObject co) {
		if (co instanceof Lokum) {
			Lokum l = (Lokum)co;
			if (l.isTimed()) {
				timeIncrease += 5;
			}
		}
	}

	private void eraseForSpecial() {
		
		Board board = gp.getLevel().getBoard();
		EraseRules eraseRules = rules.getEraseRules(RuleEngine.SPECIAL_ERASE);
		
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Position p = new Position(x, y);
				if (eraseRules.shouldErased(EraseRules.PERSISTENT, gp, null, p)) {
					ChewyObject current = board.cellAt(x, y).getCurrentObject();
					//timed lokum case
					
					addTimeToGame(current);
					if (!(current instanceof Nothing)) {
						board.fillCellAt(x, y, new Nothing());
					}
				}	
				
			}
			
			
		}
		
		eraseRules.taskCompleted();
	}

	
	private void eraseForNormal(MatchingScaleInformer[][] scaleMatrix) {
		// rules for erasing
		EraseRules eraseRules = rules.getEraseRules(RuleEngine.NORMAL_ERASE);
		// rules for
		GenerationRules genRules = rules.getGenerationRules();

		Move lastMove = gp.getLastMove();

		scoreIncrease += calculateMatchingScore(scaleMatrix);
		
		ScoringRules scoringRules= rules.getScoringRules(gp);
		
		Board board = gp.getLevel().getBoard();
		for (int i = 0; i < scaleMatrix.length; i++) {
			for (int j = 0; j < scaleMatrix[0].length; j++) {
				int x = j;
				int y = i;

				ChewyObject objectToPut;
				if (lastMove != null) {
					if (lastMove.getPosition1().isSamePlace(x, y)) {
						objectToPut = genRules.getObject(lastMove.getType2(), scaleMatrix[i][j]);
					}
					else if (lastMove.getPosition2().isSamePlace(x, y)) {
						objectToPut = genRules.getObject(lastMove.getType1(), scaleMatrix[i][j]);
					}
					else {
						objectToPut = new Nothing();
					}
				}
				else {
					objectToPut = new Nothing();
				}
				
				scoreIncrease += scoringRules.getCreationScore(objectToPut);
				
				// erasing
				if (eraseRules.shouldErased(gp, scaleMatrix[i][j],
						new Position(x, y))) {
					/*ChewyObject current = board.cellAt(x, y).getCurrentObject();
					if (!(current instanceof Nothing)) {
						board.fillCellAt(x, y, new Nothing());
					}*/
					addTimeToGame(board.cellAt(x, y).getCurrentObject());
					board.fillCellAt(x, y, objectToPut);
				}

			}
		}
	}

	private int calculateMatchingScore(MatchingScaleInformer[][] scaleMatrix) {
		ScoringRules sr = rules.getScoringRules(gp);
		int score = 0;
		for (int i = 0; i < scaleMatrix.length; i++) {
			for (int j = 0; j < scaleMatrix[0].length; j++) {
				score += sr.getMatchingScore(scaleMatrix[i][j]);
				//System.out.println(score + "at "+ j + " " + i);
			}
		}
		return score;

	}

	private int calculateSpecialScore() {
		return 0;
	}

	private MatchingScaleInformer[][] generateScaleMatrix() {
		Board board = gp.getLevel().getBoard();
		MatchingScaleInformerFactory factory = MatchingScaleInformerFactory
				.getInstance();
		MatchingScaleInformer[][] scaleMatrix = new MatchingScaleInformer[board
				.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				scaleMatrix[j][i] = factory.getMatchingScaleInformer(board, i,
						j, board.cellAt(i, j).getCurrentObject());
			}
		}
		return scaleMatrix;
	}

	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have
	 *           non null cells, gp.rules is non null and works properly
	 * @modifies p.level.board, scoreIncrease
	 * @ensures there are no empty cells in the game.level.board
	 */
	public void fillEmptyCells() {
		Board board = gp.getLevel().getBoard();
		String str[] = Lokum.possibleTypes;
		final int timedPercentage = 10; 
		Random rand = new Random();
		
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject() instanceof Nothing) {
					Lokum currentLokum = new Lokum(
							str[rand.nextInt(str.length)]);
					//Adding timed lokum
					if (gp.getLevel().hasTimer()) {
						int timedValue = rand.nextInt(100);
						if (timedValue < timedPercentage) {
							currentLokum.setTimed(true);
						}
					}
					board.fillCellAt(i, j, currentLokum);
				}

			}

		}
	}

	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have
	 *           non null cells, gp.rules is non null and works properly
	 * @ensures returned false if and only if there are available moves, no cell
	 *          in the game.level.board is empty; and there are no object left
	 *          to erase in the gp.level.board
	 */
	public boolean stillToDo() {
		// shuffle needed
		 if (!isThereAvailableMove()) 
			 return true;
		// empty places
		if (isThereNothing()) {
			return true;
		}

		/*if (isThereAnythingToErase()) {
			return true;
		}*/

		return false;
	}

	private boolean isThereAnythingToErase() {
		Move lastMove = gp.getLastMove();
		
		if (lastMove != null) {
			if (!lastMove.getSpecialType1().equals("Color Bomb")
					|| lastMove.getSpecialType2().equals("Color Bomb")) {
				return true;
			}
		}
		
		Board board = gp.getBoard();
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				MatchingScaleInformer msi = MatchingScaleInformerFactory.getInstance().getMatchingScaleInformer(board, i, j);
				System.out.println(msi);
				if (msi.horizontalMatchTotalScale() >= 3) {
					return true;
				}
		
				if (msi.verticalMatchTotalScale() >= 3) {
					return true;
				}
			}
		}
				
		return false;

	}

	private boolean isThereNothing() {
		Board board = gp.getLevel().getBoard();
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject() instanceof Nothing) {
					return true;
				}
			}
		}
		return false;
	}

	public int getTimeIncrease() {
		// TODO Auto-generated method stub
		return timeIncrease;
	}
	
	public void resetScoreIncrease() {
		scoreIncrease = 0;
	}
	
	public void resetTimeIncrease() {
		timeIncrease = 0;
	}

}
