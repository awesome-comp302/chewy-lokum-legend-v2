package Test;

import Logic.Board;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
	
	private Board board;
	
	@Before
	public void before(){
		System.out.println("Before testing...");
		
		board = new Board(10,10);
	}
	
	@After
	public void after(){
		System.out.println("After testing...");
		
		board = null;
	}
	
	@Test
	public void constructorTest(){
		assertTrue(board.repOk());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void nonExistingCellTest(){
		board.cellAt(13,14);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sizeTest(){
		board = new Board(-2,10);
	}

}
