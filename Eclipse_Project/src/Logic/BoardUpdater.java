package Logic;

import java.util.Random;

public class BoardUpdater {
	
	private int scoreIncrease;
	private GamePlay gp;
	private RuleEngine rules;
	
	public BoardUpdater(GamePlay gp, RuleEngine rules){
		this.gp = gp;
		scoreIncrease = 0;
		this.rules = rules;
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
	 * @requires gp, gp.level, gp.level.board are non null, gp.rules is non null and works properly
	 * @modifies gp.level.board
	 * @ensures No object left in the gp.level.board whose downside is empty 
	 */
	public void dropAll(){
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
						ChewyObject co = board.cellAt(i, temp).getCurrentObject();
						board.fillCellAt(i, temp, new Nothing());
						board.fillCellAt(i, j, co);
						temp = j;
					}
				}
			}
		}
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board
	 * @ensures there are valid swap in the game
	 */
	public void shuffle(){
		
	}
	
	public boolean isThereAvailableMove() {
		Board board = gp.getLevel().getBoard(); 
		for (int i = 0; i < board.getWidth() - 1; i++) {
			for (int j = 0; j < board.getHeight() - 1; j++) {
				
				Move move = new Move(i, j, i + 1, j, gp, false);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i + 1, j, gp, true);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i + 1, j + 1, gp, false);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i + 1, j + 1, gp, true);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i, j+1, gp, false);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
				
				move = new Move(i, j, i, j+1, gp, true);
				if(rules.getSwapRules(move).isValid(gp, move))
					return true;
			}
		}			
		return false;
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board, scoreIncrease
	 * @ensures  relevant deletions in the game.level.board were done, scoreIncrease is updated based on rules
	 */
	public void eraseAll(){
		eraseForNormal();
		eraseForSpecial();
	}


	private void eraseForSpecial() {
		scoreIncrease += calculateSpecialScore();
		Board board = gp.getLevel().getBoard();
		EraseRules eraseRules = rules.getEraseRules(RuleEngine.SPECIAL_ERASE);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Position p = new Position(x, y);
				if (eraseRules.shouldErased(EraseRules.PERSISTENT, gp, null, p)) {
					ChewyObject current = board.cellAt(x, y).getCurrentObject();
					if (!(current instanceof Nothing)) {
						board.fillCellAt(x, y, new Nothing());
					}
				}
			}
		}
	}


	private void eraseForNormal() {
		EraseRules eraseRules = rules.getEraseRules(RuleEngine.NORMAL_ERASE);
		MatchingScaleInformer scaleMatrix[][] = generateScaleMatrix();
		scoreIncrease += calculateMatchingScore(scaleMatrix);
		Board board = gp.getLevel().getBoard();
		for (int i = 0; i < scaleMatrix.length; i++) {
			for (int j = 0; j < scaleMatrix[0].length; j++) {
				int x = j;
				int y = i;
				if (eraseRules.shouldErased(null, scaleMatrix[i][j], new Position(x, y))) {
					ChewyObject current = board.cellAt(x, y).getCurrentObject();
					if (!(current instanceof Nothing)) {
						board.fillCellAt(x, y, new Nothing());
					}
				}
			}
		}
	}


	private int calculateMatchingScore(MatchingScaleInformer[][] scaleMatrix) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private int calculateSpecialScore() {
		return 0;
	}


	private MatchingScaleInformer[][] generateScaleMatrix() {
		Board board = gp.getLevel().getBoard();
		MatchingScaleInformerFactory factory = MatchingScaleInformerFactory.getInstance();
		MatchingScaleInformer[][] scaleMatrix = new MatchingScaleInformer[board.getHeight()][board.getWidth()];
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				scaleMatrix[j][i] = factory.getMatchingScaleInformer(board, i, j,
						board.cellAt(i, j).getCurrentObject());
			}
		}
		return scaleMatrix;
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies  p.level.board, scoreIncrease
	 * @ensures there are no empty cells in the game.level.board
	 */
	public void fillEmptyCells(){
		Board board = gp.getLevel().getBoard();
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
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @ensures returned false if and only if there are available moves,  no cell in the game.level.board is empty; 
	 * and there are no object left to erase in the gp.level.board
	 */
	public boolean stillToDo(){
		//shuffle needed
		if (!isThereAvailableMove()) {
			return true;
		}
		//empty places
		if (isThereNothing()) {
			return true;
		}
		
		if (isThereAnythingToErase()) {
			return true;
		}
		
		return false;
	}
	
	private boolean isThereAnythingToErase() {
		return false;
		
	}


	private boolean isThereNothing() {
		Board board = gp.getLevel().getBoard();
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
	


}
