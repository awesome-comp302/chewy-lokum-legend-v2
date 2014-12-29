package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.GameController;
import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.Player;
import XML.ReadXMLFile;
import XML.WriteXMLFile;

public class ReadXMLFileTest {
	
	private static ReadXMLFile rxf;
	private static GamePlay gp;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rxf = ReadXMLFile.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Level.clearLevelIDs();
		Board board = new Board(10, 10);
		gp = new GamePlay(new Level(10000, 20,board, 1));
		gp.setPlayer(new Player());
		gp.initBoard();
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();

		Level.clearLevelIDs();
	}

	@After
	public void tearDown() throws Exception {
	}

	//-------------------------------------------------------------------------
	//BLACK BOX TESTING
	
	@Test
	public void testGetInstance() {
		assertTrue(rxf != null);
	}


	@Test
	public void testRead() {
		rxf.read();
		assertTrue(rxf.loadGame() != null);
	}

	@Test
	public void testLoadGame() {
		rxf.read();
		assertSame(gp , rxf.loadGame());
	}
	
	//-------------------------------------------------------------------------
	//GLASS BOX TESTING
	
	/*
	 * For saving/reading, feature space:
	 * 1. Valid XML file composed of ASCII toolset
	 * 2. Corrupted File
	 * NOTE:
	 *  - Why not have a partition for valid XML schema but bad values (ie string for a number)?
	 *  	Because there is no way to induce that state in code, besides hand-editing the file.
	 *  	So its not a real partition. 
	 */
	
	@Test
	public void testUnicode() {
		gp.setPlayer(new Player());
		WriteXMLFile.getInstance().saveGame(gp);
		rxf.read();
		assertSame(gp.getPlayer().getName() , rxf.loadGame().getPlayer().getName());
	}
	
	@Test (expected = Exception.class)
	public void testCorrupted() {
		String fileName = "file.xml";
		
		FileWriter f;
		try {
			f = new FileWriter(fileName);

			f.write("<CONTENT>blaharhrhah</CONTENT>");
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}
		
		rxf.read();
	}

	//-------------------------------------------------------------------------
}
