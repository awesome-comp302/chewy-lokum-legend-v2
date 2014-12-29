package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import XML.WriteXMLFile;
import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Player;

public class WriteXMLFileTest {
	
	private static GamePlay gp;
	private static WriteXMLFile wxf;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		new File("file.xml").delete();
		Level.clearLevelIDs();
		Board board = new Board(10, 10);
		gp = new GamePlay(new Level(10000, 20,board, 1));
		gp.setPlayer(new Player());
		gp.initBoard();
		
		Field field = WriteXMLFile.class.getDeclaredField("instance");
		field.setAccessible(true);
		field.set(null, null);
		field.setAccessible(false);
		
		wxf = WriteXMLFile.getInstance();
		Level.clearLevelIDs();
	}

	@After
	public void tearDown() throws Exception {
	}

	//-------------------------------------------------------------------------
	//BLACK BOX TESTING
	
	@Test
	public void testGetInstance() {
		assertTrue(WriteXMLFile.getInstance() != null);
	}

	@Test
	public void testSaveGame() {
		WriteXMLFile.getInstance().saveGame(gp);
		assert(wxf.getBoard() != null &&
				wxf.getGamePlay() != null &&
				wxf.getLevel() != null &&
				wxf.getPlayer() != null);
	}

	@Test
	public void testWrite() {
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();
		File f = new File("file.xml");
		assertTrue(f.exists() && !f.isDirectory());
	}
	
	//-------------------------------------------------------------------------
	//GLASS BOX TESTING

	/*
	 * WriteXMLFile.saveGame saves 4 values to WriteXMLFile singleton
	 * Lets test for each of these values being null
	 * Feature Space:
	 * All valid values,
	 * Null value
	 * NOTE:
	 * Why no "corrupted"/"bad" value partition?
	 *  - Because java is strongly-typed...we cant assign random objects to vars
	 */
	
	@Test (expected = Exception.class)
	public void testNullGamePlay(){
		wxf.saveGame((GamePlay)null);
	}

	@Test (expected = Exception.class)
	public void testNullBoard(){
		gp.setLevel(new Level(10, 10, (Board)null, 1));
		wxf.saveGame(gp);
		wxf.getBoard().cellAt(0, 0);
	}

	@Test (expected = Exception.class)
	public void testNullPlayer(){
		gp.setPlayer((Player)null);
		wxf.saveGame(gp);
		wxf.getPlayer().getName();
	}

	@Test (expected = Exception.class)
	public void testNullLevel(){
		gp.setLevel((Level)null);
		wxf.saveGame(gp);
	}
	
	/*
	 * WriteXML.write doesnt have a HUGE feature space
	 * There's...
	 * Valid values
	 * (A) null value(s)
	 * Unable to write to disk (Permissions error)
	 * NOTE: Obviously, cant test hardware/OS issues
	 */
	
	@Test (expected = FileNotFoundException.class)
	public void testNullValue(){
		wxf.saveGame(gp);
		wxf.write();
		File f = new File("file.xml");
		f.setReadOnly();
		wxf.saveGame(gp);
		wxf.write();
	}
	
	@Test (expected = FileNotFoundException.class)
	public void testBadFilePermissions(){
		gp.setLevel((Level)null);
		wxf.saveGame(gp);
		wxf.write();
		File f = new File("file.xml");
		f.setReadOnly();
		wxf.saveGame(gp);
		wxf.write();
	}
}
