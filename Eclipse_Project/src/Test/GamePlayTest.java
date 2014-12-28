package Test;

import Logic.GamePlay;
import Logic.Level;
import Logic.Board;
import Logic.MatchingScaleInformer;
import Logic.RuleEngine;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GamePlayTest {
	
	private GamePlay gp;
	private Level level;
	private Board board;
	
	@Before
	public void before(){
		System.out.println("Before testing...");
		board = new Board(10,10);
		level = new Level(20,10,board,1);
		gp = new GamePlay(level);
		
	}
	
	@After
	public void after(){
		System.out.println("After testing...");
		board = null;
		level = null;
		gp = null;
	}
	
	@Test
	public void constructorTest(){
		assertTrue(gp.repOk());
	}
	
	@Test
	/**
	 * Can Gümeli:
	 * The biggest risk during the board initialization is updating game data. 
	 * I updated the initTest with an emphasis of this issue.
	 * I also added tests for other specifications, such as game's being playable after the init.
	 */
	public void initTest(){
		int initScore = gp.getScore();
		int initMovements = gp.getMovementsLeft();
		int initSpecialMovements = gp.getSpecialMovementsLeft();
		
		gp.initBoard();
		
		assertTrue(gp.repOk());
		
		//score and movement numbers should not be effected by initialization process
		assertEquals(initScore, gp.getScore());
		assertEquals(initMovements, gp.getMovementsLeft());
		assertEquals(initSpecialMovements, gp.getSpecialMovementsLeft());
		
		//board supposed to be full of nonempty cells
		assertFalse(gp.isThereNothing());
		
		//game should be playable
		assertTrue(gp.isThereAvailableMove());
		
		//there should be no matches
		MatchingScaleInformer m[][] = gp.generateScaleMatrix();
		boolean match = false;
		for (int i = 0; i < m.length && !match; i++) {
			for (int j = 0; j < m[0].length && !match; j++) {
				if (RuleEngine.getInstance().shouldErased(m[i][j])) {
					match = true;
				}	
			}
		}
		assertFalse(match);
	}
	
	@Test
	public void noMovementTest(){
		gp.setScore(21);
		boolean check = gp.swap(1, 1, 1, 2);
		assertTrue(check == false);
	}
	
	@Test
	public void gameOverTest(){
		gp.setScore(21);
		assertEquals(gp.isGameOver(), true);
	}
	
	@Test
	public void consecutivenessTest(){
		boolean check = gp.swap(1, 1, 7, 7);
		assertTrue(!check);
	}
	
	@Test
	public void succesfulSwapTest(){
		boolean check = false;
		while(check == false){
			for(int i=0;i<10;i++){
				for(int j=0;j<9;j++){
					gp.swap(j, i, j+1, i);
				}
			}
		}
		
		assertEquals(gp.getMovementsLeft(), 9);
		assertTrue(gp.getScore()>0);
		
		
	}
	
	@Test
	public void fillingTest(){
		
		gp.fillAllNothingsRandomly();
		boolean b = gp.isThereNothing();
		assertEquals(b, false);
	}
	
	@Test
	public void availableMoveTest(){
		assertTrue(gp.isThereAvailableMove());
	}
	
	@Test
	/**
	 * Can Gümeli
	 *Test for GamePlay.specialSwap
	 *We could not manage this tests since level class requires some fundameetal changes that will be done during the implementation.
	 */
	public void specialSwapTest() {
		int initialSM = gp.getSpecialMovementsLeft();
		
		
		
	}
}
