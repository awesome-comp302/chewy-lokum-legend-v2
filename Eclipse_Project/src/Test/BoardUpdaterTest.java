package Test;

import Logic.Board;
import Logic.GamePlay;
import Logic.BoardUpdater;
import Logic.Level;
import Logic.RuleEngine;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class BoardUpdaterTest {
	
	private Board board;
	private Level level;
	private GamePlay gp;
	private BoardUpdater bUpdater;
	private RuleEngine re;
	
	
	@Before 
	public void before(){
		System.out.println("Before testing...");
		
		board = new Board(10,10);
		level = new Level(20,10,board,1);
		gp.createNewBoard(); 
		System.out.println("Initial board \n" +gp.getLevel().getBoard() );
		gp = new GamePlay(level);
		bUpdater = new BoardUpdater(gp, re);
	}
	
	@After
	public void after(){
		System.out.println("After testing...");
		
		board = null;
		level = null;
		gp = null;
		bUpdater = null;
	}
	
	@Test
	public void constructorTest(){
		assertTrue(bUpdater.repOk());
	}
	
	public void nothingToDoTest(){
		assertTrue(!bUpdater.stillToDo());
	}
	
	/**
	 * Can:
	 * I added the following tests
	 */
	public void shuffleTest(){
		int initScore = bUpdater.getScoreIncrease();
		bUpdater.shuffle();
		//no score change
		assertTrue(initScore == bUpdater.getScoreIncrease());
		assertTrue(gp.isThereAvailableMove());
	}
	
	/**
	 *All cells should be filled by fillEmptyCells method
	 */
	public void fillEmptyCellsTest() {
		bUpdater.eraseAll();
		bUpdater.fillEmptyCells();
		assertFalse(gp.isThereNothing());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
