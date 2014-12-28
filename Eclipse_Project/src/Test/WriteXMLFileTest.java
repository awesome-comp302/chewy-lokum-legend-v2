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

	@Test
	public void testGetInstance() {
		assertTrue(WriteXMLFile.getInstance() != null);
	}

	@Test
	public void testSaveGame() {
		WriteXMLFile.getInstance().saveGame(gp);
		assertTrue(wxf.repOk());
	}

	@Test
	public void testWrite() {
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();
		File f = new File("file.xml");
		assertTrue(f.exists() && !f.isDirectory());
	}

	@Test (expected = Exception.class)
	public void testNullGamePlay(){
		gp.setLevel((Level)null);
		wxf.saveGame(gp);
	}


	@Test (expected = Exception.class)
	public void testNullLevel(){
		gp.setLevel((Level)null);
		wxf.saveGame(gp);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void testBadFilePermissions(){
		wxf.saveGame(gp);
		wxf.write();
		File f = new File("file.xml");
		f.setReadOnly();
		wxf.saveGame(gp);
		wxf.write();
	}
}
