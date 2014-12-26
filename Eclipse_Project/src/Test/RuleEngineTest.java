package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.MatchingScaleInformer;
import Logic.Player;
import Logic.RuleEngine;
import XML.WriteXMLFile;

public class RuleEngineTest {
	private static RuleEngine re;
	private static GamePlay gp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		re = RuleEngine.getInstance();
		
		Level.clearLevelIDs();
		Board board = new Board(10, 10);
		gp = new GamePlay(new Level(10000, 20,board, 1));
		gp.setPlayer(new Player());
		gp.initBoard();

		Level.clearLevelIDs();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetInstance() {
		assertTrue(re != null);
	}

	@Test
	public void testGetMatchingScaleInformer() {
		Board b = new Board(10, 10);
		b.fillCellAt(3, 1, new Lokum("red rose"));
		b.fillCellAt(3, 2, new Lokum("red rose"));
		b.fillCellAt(2, 3, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
		assertTrue(msi.getLeftScale() != 0 ||
				msi.getDownScale() != 0 ||
				msi.getRightScale() != 0 ||
				msi.getUpScale() != 0);
	}

	@Test
	public void testIsSwappableTrue() {
		Board b = new Board(10, 10);
		b.fillCellAt(3, 1, new Lokum("red rose"));
		b.fillCellAt(3, 2, new Lokum("red rose"));
		b.fillCellAt(2, 3, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		assertTrue(re.isSwappable(b, 2, 3, 3, 3));
	}
	
	@Test
	public void testIsSwappableFalse() {
		Board b = new Board(10, 10);
		/*
		 * W W R
		 * R 
		 * R
		 */
		b.fillCellAt(0, 0, new Lokum("white coconut"));
		b.fillCellAt(1, 0, new Lokum("white coconut"));
		b.fillCellAt(0, 1, new Lokum("red rose"));
		b.fillCellAt(0, 2, new Lokum("red rose"));
		b.fillCellAt(1, 2, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		assertFalse(re.isSwappable(b, 0, 0, 1, 0));
	}

	@Test
	public void testShouldErased() {
		re.getMatchingScaleInformer(gp.getBoard(), 3, 3, gp.getBoard().cellAt(2, 3).getCurrentObject());
	}

	@Test
	public void testGameEndedByMovements() {
		assertTrue(re.gameEndedByMovements(0) && re.gameEndedByMovements(-1) && !re.gameEndedByMovements(1));
	}

	@Test
	public void testGetStandardScore() {
		Board b = new Board(10, 10);
		b.fillCellAt(3, 1, new Lokum("red rose"));
		b.fillCellAt(3, 2, new Lokum("red rose"));
		b.fillCellAt(2, 3, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
		
		assertTrue(re.getStandardScore(1, msi) > 0);
	}

	@Test
	public void testGetSpecialityCode() {
		Board b = new Board(10, 10);
		b.fillCellAt(3, 1, new Lokum("red rose"));
		b.fillCellAt(3, 2, new Lokum("red rose"));
		b.fillCellAt(3, 3, new Lokum("red rose"));
		b.fillCellAt(3, 4, new Lokum("red rose"));
		b.fillCellAt(3, 5, new Lokum("red rose"));
		b.fillCellAt(3, 6, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
		
		assertTrue(re.getSpecialityCode(msi) != re.REGULAR);
	}

	@Test
	public void testIsSpecialCase() {
		assertTrue(re.isSpecialCase(re.COLOR_BOMB) &&
				re.isSpecialCase(re.HSTRIPED) &&
				re.isSpecialCase(re.VSTRIPED) &&
				re.isSpecialCase(re.WRAPPED)
				);
	}

	@Test
	public void testGetRelevantSpecialObject() {
		String[] nt = Lokum.possibleTypes.clone();
		int[] st = new int[4];
		st[0] = RuleEngine.COLOR_BOMB;
		st[1] = re.HSTRIPED;
		st[2] = re.VSTRIPED;
		st[3] = RuleEngine.WRAPPED;

		for( String n : nt){
			for( int s : st){
				Lokum sp = re.getRelevantSpecialObject(n, s);
				if(!sp.isSpecial() || !Lokum.class.isInstance(sp)) fail("sp not special lokum");
			}
		}
	}

	@Test
	public void testGetRelevantCreationScore() {
		assertTrue(re.getRelevantCreationScore(re.COLOR_BOMB) == 200 &&
				re.getRelevantCreationScore(re.WRAPPED) == 200 &&
				re.getRelevantCreationScore(re.VSTRIPED) == 120 &&
				re.getRelevantCreationScore(re.HSTRIPED) == 120
				);
	}

	@Test
	public void testGetSpecialMoveScore() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsingScore() {
		fail("Not yet implemented");
	}

}
