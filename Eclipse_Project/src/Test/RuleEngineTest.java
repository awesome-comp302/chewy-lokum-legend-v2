package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

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

	//-------------------------------------------------------------------------
	//GLASS BOX TESTING
	
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
		re.shouldErased(re.getMatchingScaleInformer(gp.getBoard(), 3, 3, gp.getBoard().cellAt(2, 3).getCurrentObject()));
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
		gp.getBoard().fillCellAt(0, 0, new Lokum("red rose", "Color Bomb"));
		gp.getBoard().fillCellAt(0, 1, new Lokum("red rose", "Wrapped"));
		assertTrue( re.getSpecialMoveScore(0, 0, 0, 1, gp.getBoard(), (Lokum)gp.getBoard().cellAt(0, 0).getCurrentObject(), (Lokum)gp.getBoard().cellAt(0, 1).getCurrentObject()) != 0);
	}

	@Test
	public void testGetUsingScore() {
		gp.getBoard().fillCellAt(0, 0, new Lokum("red rose", "Horizontal Striped"));
		assertTrue( re.getUsingScore(0, 0, gp.getBoard(), (Lokum)gp.getBoard().cellAt(0, 0).getCurrentObject()) != 0);
	}

	//-------------------------------------------------------------------------
	//Black Box Testing

	/*
	 * GetMatchingScaleInformer
	 * Feature Space:
	 * 	Valid board:
	 * 1	no matches
	 * 2	one match - do for each lokum type
	 * 3	two/more matches (same/different types)
	 * 4	all one regular color
	 * 5	one special lokum
	 * 6	two/more special lokums
	 * 7	all special lokums
	 * 8	special lokum inside match
	 * 9	special lokum and separate match
	 * 
	 * NOTE:
	 * Why no invalid board options?
	 *  - The method assumes a valid board
	 */
	
	@Test
	public void testGetMatchingScaleInformer1() {
		Board b = new Board(10, 10);
		b.fillCellAt(3, 1, new Lokum("red rose"));
		b.fillCellAt(3, 2, new Lokum("white coconut"));
		b.fillCellAt(2, 3, new Lokum("red rose"));
		gp.setLevel(new Level(10000, 10, b, 1));
		MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
		assertTrue(msi.getLeftScale() == 0 &&
				msi.getDownScale() == 0 &&
				msi.getRightScale() == 0 &&
				msi.getUpScale() == 0);
	}
	@Test
	public void testGetMatchingScaleInformer2() {
		
		for(String s : Lokum.possibleTypes){
			Board b = new Board(10, 10);
			b.fillCellAt(3, 1, new Lokum(s));
			b.fillCellAt(3, 2, new Lokum(s));
			b.fillCellAt(2, 3, new Lokum(s));
			gp.setLevel(new Level(10000, 10, b, 1));
			MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
			assertFalse(msi.getLeftScale() != 0 ||
					msi.getDownScale() != 0 ||
					msi.getRightScale() != 0 ||
					msi.getUpScale() != 0);
		}
	}
	@Test
	public void testGetMatchingScaleInformer5() {
		for(String s : Lokum.specialTypes){
			Board b = new Board(10, 10);
			b.fillCellAt(3, 1, new Lokum("red rose"));
			b.fillCellAt(3, 2, new Lokum("red rose"));
			b.fillCellAt(2, 3, new Lokum("red rose", s));
			gp.setLevel(new Level(10000, 10, b, 1));
			MatchingScaleInformer msi = re.getMatchingScaleInformer(gp.getBoard(), 3, 3, b.cellAt(2, 3).getCurrentObject());
			if(!(msi.getLeftScale() != 0 ||
					msi.getDownScale() != 0 ||
					msi.getRightScale() != 0 ||
					msi.getUpScale() != 0)){
				fail("Lokum " + s + " one special match failed to register");
			}
		}
	}
	
	/*
	 * ShouldErased
	 * Feature Space:
	 * 1	Both vertical/horiz > MINMATCHREQUIRED
	 * 2	Both horiz > MINMATCHREQUIRED
	 * 3	Both vertical > MINMATCHREQUIRED
	 * 4	None > MINMATCHREQUIRED
	 */

	@Test
	public void testShouldErased1() {
		re.shouldErased(re.getMatchingScaleInformer(gp.getBoard(), 3, 3, gp.getBoard().cellAt(2, 3).getCurrentObject()));
	}
	
	/*
	 * GameEndedByMovements
	 * Feature Space:
	 * 1	-1
	 * 2	0
	 * 3	1
	 */

	@Test
	public void testGameEndedByMovementsAll() {
		assertTrue(re.gameEndedByMovements(0) && re.gameEndedByMovements(-1) && !re.gameEndedByMovements(1));
	}
	
	/*
	 * GetStandardScore
	 * Feature Space:
	 * 1	LeftScale > 0
	 * 2	UpScale > 0
	 * 3	right >= MIN-1, bot < MIN-1
	 * 4	right < MIN-1, bot >= MIN-1
	 * 5	both > MIN-1
	 */

	@Test
	public void testGetStandardScore1() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		msi.setLeftScale(1);	
		assertTrue(re.getStandardScore(1, msi) == 0);
	}

	@Test
	public void testGetStandardScore2() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		msi.setUpScale(1);	
		assertTrue(re.getStandardScore(1, msi) == 0);
	}

	@Test
	public void testGetStandardScore3() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		msi.setRightScale( RuleEngine.MINIMUM_MATCH_REQUIRED );
		msi.setDownScale(RuleEngine.MINIMUM_MATCH_REQUIRED - 2);
		assertTrue(re.getStandardScore(1, msi) >= 0);
	}

	@Test
	public void testGetStandardScore4() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		msi.setDownScale( RuleEngine.MINIMUM_MATCH_REQUIRED );
		msi.setRightScale(RuleEngine.MINIMUM_MATCH_REQUIRED - 2);
		assertTrue(re.getStandardScore(1, msi) >= 0);
	}

	@Test
	public void testGetStandardScore5() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		msi.setRightScale( RuleEngine.MINIMUM_MATCH_REQUIRED );
		msi.setDownScale(RuleEngine.MINIMUM_MATCH_REQUIRED);
		assertTrue(re.getStandardScore(1, msi) >= 0);
	}
	
	/*
	 * GetSpecialtyCode
	 * Feature Space:
	 * 1	hms == 5 || vms == 5
	 * 2	hms == 3 && vms == 3
	 * 3	hms == 4
	 * 4	vms = 4
	 * 5	none of the above
	 */

	@Test
	public void testGetSpecialityCode1() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		// hms = dscale + uscale + 1
		msi.setDownScale(4);
		assertTrue(re.getSpecialityCode(msi) == re.COLOR_BOMB);
	}

	@Test
	public void testGetSpecialityCode2() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		// hms = dscale + uscale + 1
		msi.setDownScale(2);
		msi.setRightScale(2);
		assertTrue(re.getSpecialityCode(msi) == re.WRAPPED);
	}

	@Test
	public void testGetSpecialityCode3() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		// hms = dscale + uscale + 1
		msi.setDownScale(3);
		assertTrue(re.getSpecialityCode(msi) == re.VSTRIPED);
	}

	@Test
	public void testGetSpecialityCode4() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		// hms = dscale + uscale + 1
		msi.setRightScale(3);
		assertTrue(re.getSpecialityCode(msi) == re.HSTRIPED);
	}

	@Test
	public void testGetSpecialityCode5() {
		MatchingScaleInformer msi = new MatchingScaleInformer();
		assertTrue(re.getSpecialityCode(msi) == re.REGULAR);
	}
	
	/*
	 * IsSpecialCase
	 * Feature Space:
	 * 1	CB
	 * 2	HS
	 * 3	VS
	 * 4	WR
	 * 5	N/A
	 */

	@Test
	public void testIsSpecialCaseAll() {
		assertTrue(re.isSpecialCase(re.COLOR_BOMB) &&
				re.isSpecialCase(re.HSTRIPED) &&
				re.isSpecialCase(re.VSTRIPED) &&
				re.isSpecialCase(re.WRAPPED) &&
				!re.isSpecialCase(20)
				);
	}
	
	/*
	 * GetRelevantSpecialObject
	 * Feature Space:
	 * X	All combinations of regular/special types
	 * 1	bad reg string
	 * 2	null string(s)
	 */

	@Test
	public void testGetRelevantSpecialObjectX12() {
		ArrayList<String> nt = new ArrayList<String>(Arrays.asList( Lokum.possibleTypes.clone()));
		nt.add("HI");
		nt.add("");
		int[] st = new int[4];
		st[0] = RuleEngine.COLOR_BOMB;
		st[1] = re.HSTRIPED;
		st[2] = re.VSTRIPED;
		st[3] = RuleEngine.WRAPPED;

		for( String n : nt){
			for( int s : st){
				Lokum sp = re.getRelevantSpecialObject(n, s);
				if((!sp.isSpecial() || !Lokum.class.isInstance(sp)) && Arrays.asList(Lokum.possibleTypes).contains(n)) fail("sp not special lokum");
			}
		}
	}

	/*
	 * GetRelevantCreationScore
	 * Feature Space:
	 * 1	CB
	 * 2	WR
	 * 3	VS
	 * 4	HS
	 */
	
	@Test
	public void testGetRelevantCreationScoreAll() {
		assertTrue(re.getRelevantCreationScore(re.COLOR_BOMB) == 200 &&
				re.getRelevantCreationScore(re.WRAPPED) == 200 &&
				re.getRelevantCreationScore(re.VSTRIPED) == 120 &&
				re.getRelevantCreationScore(re.HSTRIPED) == 120
				);
	}
	
	/*
	 * GetSpecialMoveScore
	 * Feature Space:
	 * X	Each combo of special lokum
	 */

	@Test
	public void testGetSpecialMoveScoreX() {
		for(String sp1 : Lokum.specialTypes){
			for(String sp2 : Lokum.specialTypes)
			gp.getBoard().fillCellAt(0, 0, new Lokum("red rose", sp1));
			gp.getBoard().fillCellAt(0, 1, new Lokum("red rose", "sp2"));
			if(!( re.getSpecialMoveScore(0, 0, 0, 1, gp.getBoard(), (Lokum)gp.getBoard().cellAt(0, 0).getCurrentObject(), (Lokum)gp.getBoard().cellAt(0, 1).getCurrentObject()) != 0)){
				fail("Val == 0 with combo: " + ((Lokum)gp.getBoard().cellAt(0, 0).getCurrentObject()).getSpecialType() + " " + ((Lokum)gp.getBoard().cellAt(0, 1).getCurrentObject()).getSpecialType());
			}
		}
	}

	/*
	 * GetUsingScore
	 * Feature Space:
	 * X	Specials
	 */
	
	@Test
	public void testGetUsingScoreX() {
		for(String sp : Lokum.specialTypes){
			gp.getBoard().fillCellAt(0, 0, new Lokum("red rose", sp));
			if( re.getUsingScore(0, 0, gp.getBoard(), (Lokum)gp.getBoard().cellAt(0, 0).getCurrentObject()) == 0) fail("Bad t: " + sp);
		}
	}

}
