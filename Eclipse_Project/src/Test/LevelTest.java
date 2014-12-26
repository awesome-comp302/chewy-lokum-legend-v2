package Test;


import Logic.Level;
import Logic.Board;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class LevelTest {

	private Level level;
	private Board board;
	
	
	@Before
	public void before(){
		System.out.println("Before testing...");
		
		board = new Board(10,10);
		level = new Level(20,10,board,1);
	}
	
	@After
	public void after(){
		System.out.println("After testing...");
		
		board = null;
		level = null;
	}
	
	@Test
	public void constructorTest(){
		assertTrue(level.repOk());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void passingScoreTest(){
		level = new Level(-1,10,board,0);
	}
	

	
	
}
