package Test;
import static org.junit.Assert.*;

import org.junit.*;

import XML.ReadXMLFile;
import XML.WriteXMLFile;
import Logic.Board;
import Logic.Cell;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.Player;


public class TestsV1 {
	
	private static Board bPrepared = new Board(3, 3);;
	private static Level testLevel;
	@SuppressWarnings("unused")
	private static GamePlay testGame;
	
	

	//Class Board, method Board, testing legal constructor values
	@SuppressWarnings("unused")
	@Test
	public void LegalBoardConstructor(){
		Board b = new Board(10,10);
		System.out.println("LegalBoardConstructor passsed.");
	}

	//Class Board, method Board, testing illegal constructor values
	@SuppressWarnings("unused")
	@Test (expected = IllegalArgumentException.class)
	public void IllegalBoardConstructor1(){
		Board b = new Board(0,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void IllegalBoardConstructor2(){
		Board b = new Board(1,0);
	}
	@Test (expected = IllegalArgumentException.class)
	public void IllegalBoardConstructor3(){
		Board b = new Board(0,1);
	}
	
	//Class Level, method Level, testing legal constructor values
	@SuppressWarnings("unused")
	@Test
	public void LegalLevelConstructor(){
		int passingScore = 1;
		int possibleMovements = 1;
		Board b = new Board(10,10);
		int levelId = 0;
		Level l = new Level(passingScore,possibleMovements,b,levelId);
	}

	//Class Level, method Level, testing illegal constructor values
	@SuppressWarnings("unused")
	@Test (expected = IllegalArgumentException.class)
	public void IllegalLevelConstructor(){
		int passingScore = 0;
		int possibleMovements = 1;
		Board b = new Board(10,10);
		int levelId = 0;
		
		Level l = new Level(passingScore,possibleMovements,b,levelId);
	}
	
	//Class Board, method cellAt, testing accessing a legal position of the board
	@SuppressWarnings("unused")
	@Test
	public void LegalcellAt(){	
		Board b = new Board(5, 5);
		Cell c = b.cellAt(2, 2);
		System.out.println("LegalCellAt passed");
	}

	//Class Board, method CellAt, testing trying to access cells of illegal positions
	@SuppressWarnings("unused")
	@Test (expected = IllegalArgumentException.class)
	public void IllegalcellAt1(){
		Board b = new Board(10,10);
		Cell c = b.cellAt(20, 20);
	}
	@Test (expected = IllegalArgumentException.class)
	public void IllegalcellAt2(){
		Board b = new Board(10,10);
		Cell c = b.cellAt(-10, 2);
	}
	@Test (expected = IllegalArgumentException.class)
	public void IllegalcellAt3(){
		Board b = new Board(10,10);
		Cell c = b.cellAt(2, -10);
	}
	
	//Class Board, method fillCellAt, testing filling board with legal ChewyObject and position
	@Test
	public void LegalfillCellAt(){
		Board b = new Board(10,10);
		Lokum l = new Lokum("A");
		b.fillCellAt(5, 5, l);
	}

	//Class Board, method fillCellAt, testing filling board with illegal ChewyObject and position
	@Test (expected = Exception.class)
	public void IllegalfillCellAt1(){
		Board b = new Board(10,10);
		Lokum l = null;
		b.fillCellAt(5, 5, (Lokum)l);
	}
	@Test (expected = Exception.class)
	public void IllegalfillCellAt2(){
		Board b = new Board(10,10);
		Lokum l = new Lokum("A");
		b.fillCellAt(15, 5, l);
	}
	@Test (expected = Exception.class)
	public void IllegalfillCellAt3(){
		Board b = new Board(10,10);
		Lokum l = new Lokum("A");
		b.fillCellAt(5, 15, l);
	}
	


	//Class Board, method fillCellAt, Class Lokum, method Lokum, testing filling certain spots on board with new Lokums
	@Test
	public void prapareBoardForFurtherOp(){
		bPrepared.fillCellAt(0, 0, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(0, 1, new Lokum("green pistachio"));
		bPrepared.fillCellAt(0, 2, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(1, 0, new Lokum("green pistachio"));
		bPrepared.fillCellAt(1, 1, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(1, 2, new Lokum("red rose"));
		bPrepared.fillCellAt(2, 0, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(2, 1, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(2, 2, new Lokum("red rose"));
		
		assertTrue(bPrepared.repOk());
	}
	
	//Class Level, method repOK, testing RepOK of normal constructor
	@Test
	public void initLevel(){
		testLevel = new Level(20, 5, bPrepared, 1);
		assertTrue(testLevel.repOk());
	}
	
	//Class GamePlay, method GamePlay, testing constructor with legal value
	/*@Test
	public void initGame(){	
		testGame = new GamePlay(testLevel);
		
		if(testGame.repOk()){
			System.out.println("Successfull!! Test game is created.");
			System.out.println(testGame);
		}
		else
			System.out.println("Fail!! Test game is not created.");
		
	}
	
	//Class GamePlay, method swap, testing legal horizontal swap
	@Test
	public void successfulHorSwap(){
		System.out.println("Horizontal Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (0,1) and (1,1)");
		if(testGame.swap(0, 1, 1, 1))
			System.out.println("Horizontal Swap is Successfull!\n");
		else
			System.out.println("Horizontal Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}

	//Class GamePlay, method swap, testing illegal horizontal swap
	@Test
	public void failedHorSwap(){
		System.out.println("Horizontal Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (2,1) and (1,1)");
		if(testGame.swap(2, 1, 1, 1))
			System.out.println("Horizontal Swap is Successfull!\n");
		else
			System.out.println("Horizontal Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}

	//Class GamePlay, method swap, testing legal vertical swap
	@Test
	public void successfulVerSwap(){
		System.out.println("Vercital Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (1,0) and (1,1)");
		if(testGame.swap(1, 0, 1, 1))
			System.out.println("Vercital Swap is Successfull!\n");
		else
			System.out.println("Vercital Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}

	//Class GamePlay, method swap, testing illegal vertical swap
	@Test
	public void FailedVerSwap(){
		System.out.println("Vercital Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (1,2) and (1,1)");
		if(testGame.swap(1, 2, 1, 1))
			System.out.println("Vercital Swap is Successfull!\n");
		else
			System.out.println("Vercital Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}

	//Class GamePlay, method swap, testing legal cross swap
	@Test
	public void successfulCrossSwap(){
		System.out.println("Cross Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (2,2) and (1,1)");
		if(testGame.swap(2, 2, 1, 1))
			System.out.println("Cross Swap is Successfull!\n");
		else
			System.out.println("Cross Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}

	//Class GamePlay, method swap, testing illegal cross swap
	@Test
	public void FailedCrossSwap(){
		System.out.println("Cross Swap Operation is started.");
		System.out.println("Before");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
		System.out.println("Swapping (1,2) and (0,1)");
		if(testGame.swap(1, 2, 0, 1))
			System.out.println("Cross Swap is Successfull!\n");
		else
			System.out.println("Cross Swap is Failed!");
		System.out.println("After");
		System.out.println(bPrepared);
		System.out.println(testGame.movementsLeft());
	}
	
	@Test
	public void dropTest(){
		Board b3 = new Board(3, 6);
		Level l3 = new Level(10, 10, b3, 4);
		GamePlay gp3 = new GamePlay(l3);
		b3.fillCellAt(0, 0, new Lokum("brown hazelnut"));
		b3.fillCellAt(1, 0, new Lokum("green pistachio"));
		b3.fillCellAt(2, 0, new Obstacke("Standard Obstackle"));
		b3.fillCellAt(2, 1, new SpecialLokum("Striped"));
		b3.fillCellAt(1, 4, new Lokum("green pistachio"));
		System.out.println(b3);
		gp3.dropAll();
		System.out.println(b3);
		
	}
	
	@Test
	public void isThereNothingTest() {
		Board b2 = new Board(3, 3);
		Level l2 = new Level(10, 10, b2, 50);
		GamePlay gp2 = new GamePlay(l2);
		
		b2.fillCellAt(0, 0, new Lokum("brown hazelnut"));
		b2.fillCellAt(1, 0, new Lokum("green pistachio"));
		b2.fillCellAt(2, 0, new Lokum("red rose"));
		b2.fillCellAt(0, 2, new Lokum("red rose"));
		b2.fillCellAt(1, 1, new Lokum("brown hazelnut"));
		b2.fillCellAt(2, 1, new Lokum("white coconut"));
		b2.fillCellAt(1, 2, new Lokum("green pistachio"));
		b2.fillCellAt(0, 1, new Lokum("red rose"));
		if(gp2.isThereNothing()) System.out.println("There is an empty cell.");
		else System.out.println("All cells has component.");
		System.out.println(b2);
		
		b2.fillCellAt(2, 2, new Lokum("white coconut"));
		if(gp2.isThereNothing()) System.out.println("There is an empty cell.");
		else System.out.println("All cells has component.");
		System.out.println(b2);
	}
	
	
	@Test
	public void isThereAvailableMoveTest() {
		Board b2 = new Board(3, 3);
		Level l2 = new Level(10, 10, b2, 3);
		GamePlay gp2 = new GamePlay(l2);
		
		b2.fillCellAt(0, 0, new Lokum("brown hazelnut"));
		b2.fillCellAt(1, 0, new Lokum("green pistachio"));
		b2.fillCellAt(2, 0, new Lokum("red rose"));
		b2.fillCellAt(0, 2, new Lokum("red rose"));
		b2.fillCellAt(1, 1, new Lokum("brown hazelnut"));
		b2.fillCellAt(2, 2, new Lokum("white coconut"));
		b2.fillCellAt(2, 1, new Lokum("white coconut"));
		b2.fillCellAt(1, 2, new Lokum("green pistachio"));
		b2.fillCellAt(0, 1, new Lokum("red rose"));
		gp2.updateBoard();
		System.out.println(b2);
		
		

		b2.fillCellAt(1, 0, new Lokum("white coconut"));
		gp2.updateBoard();
		gp2.swap(1, 0, 2, 0);
		System.out.println(b2);
		
		
	}
	
	
	@Test
	public void fillRandomlyTest(){
		Board b3 = new Board(5, 5);
		Level l3 = new Level(10, 10,b3 , 10);
		GamePlay gp3 = new GamePlay(l3);
		while (gp3.isThereNothing()) {
			gp3.fillAllNothingsRandomly();
			gp3.updateBoard();
		}
		System.out.println(b3);
	}*/

	//@Test
	private static void playerTest(){
		Player tPlayer = new Player();
		System.out.println(tPlayer);
		Player[] testPlayerList = new Player[999];
		
		for(int i=0; i<999; i++){
			testPlayerList[i] = new Player();
		}
		for(int i=0; i<999; i++){
			System.out.println(testPlayerList[i]);
		}
		Player.printIdList();
		
	}
	
	//@Test
	private static void xmlWriteTest(){
		Player player = new Player();
		Board board = new Board(5, 5);
		Level level = new Level(500, 50, board, 56);
		GamePlay gp = new GamePlay(level);
		gp.setPlayer(player);
		
		
		
		WriteXMLFile writer = WriteXMLFile.getInstance();
		
		writer.saveGame(gp);
		writer.write();
		
	}
	
	@SuppressWarnings("unused")
	//@Test
	public void xmlReadTest(){
		ReadXMLFile reader = ReadXMLFile.getInstance();
		
		
		reader.read();
		GamePlay gp = reader.loadGame();
		
		System.out.println(gp.getLevel().getBoard());
		
	}	

	@SuppressWarnings("unused")
	private String testSplitter(String s){
		return "\n----------------\n" +
	           "  Next Test  \n" +
			   "Test Description: \n"+s+"\n"+
		       "----------------\n";
	}

	

}
