package Logic;

public class BoardUpdater {
	
	private int scoreIncrease;
	private GamePlay gp;
	
	public BoardUpdater(GamePlay gp, RuleEngine rules){
		this.gp = gp;
		scoreIncrease = 0;
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
		
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board
	 * @ensures there are valid swap in the game
	 */
	public void shuffle(){
		
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies gp.level.board, scoreIncrease
	 * @ensures  relevant deletions in the game.level.board were done, scoreIncrease is updated based on rules
	 */
	public void eraseAll(){
		
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @modifies  p.level.board, scoreIncrease
	 * @ensures there are no empty cells in the game.level.board
	 */
	public void fillEmptyCells(){
		
	}
	
	/**
	 * @requires gp, gp.level, gp.level.board are non null, gp.level.board have non null cells, gp.rules is non null and works properly
	 * @ensures returned false if and only if there are available moves,  no cell in the game.level.board is empty; 
	 * and there are no object left to erase in the gp.level.board
	 */
	public boolean stillToDo(){
		
		
		return true;
	}
	


}
