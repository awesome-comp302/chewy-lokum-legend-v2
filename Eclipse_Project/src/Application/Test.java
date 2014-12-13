package Application;
import XML.ReadXMLFile;
import XML.WriteXMLFile;
import Logic.Board;
import Logic.Cell;
import Logic.GamePlay;
import Logic.Level;
import Logic.Lokum;
import Logic.Player;


public class Test {
	
	private static Board bPrepared = new Board(3, 3);;
	private static Level testLevel;
	@SuppressWarnings("unused")
	private static GamePlay testGame;
	
	

	//Class Board, method Board, testing legal constructor values
	@SuppressWarnings("unused")
	public static void LegalBoardConstructor(){
		try{
			Board b = new Board(10,10);
			System.out.println("LegalBoardConstructor passsed.");
		} catch (Exception e){
			System.out.println("LegalBoardConstructor failed: " + e.getMessage());
		}
	}

	//Class Board, method Board, testing illegal constructor values
	@SuppressWarnings("unused")
	public static void IllegalBoardConstructor(){
		try{
			Board b = new Board(0,0);
			System.out.println("IllegalBoardConstructor 1 failed. IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalBoardConstructor 1 passed : " + e.getMessage());
		}
		try{
			Board b = new Board(1,0);
			System.out.println("IllegalBoardConstructor 2 failed. IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalBoardConstructor 2 passed : " + e.getMessage());
		}
		try{
			Board b = new Board(0,1);
			System.out.println("IllegalBoardConstructor 3 failed. IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalBoardConstructor 3 passed : " + e.getMessage());
		}
	}
	
	//Class Level, method Level, testing legal constructor values
	@SuppressWarnings("unused")
	public static void LegalLevelConstructor(){
		int passingScore = 1;
		int possibleMovements = 1;
		Board b = new Board(10,10);
		int levelId = 0;
		try{
			Level l = new Level(passingScore,possibleMovements,b,levelId);
			System.out.println("IllegalLevelConstructor 1 passed");
		} catch(IllegalArgumentException e){
			System.out.println("IllegalLevelConstructor 1 failed : " + e.getMessage());
		}
	}

	//Class Level, method Level, testing illegal constructor values
	@SuppressWarnings("unused")
	public static void IllegalLevelConstructor(){
		int passingScore = 0;
		int possibleMovements = 1;
		Board b = new Board(10,10);
		int levelId = 0;
		try{
			Level l = new Level(passingScore,possibleMovements,b,levelId);
			System.out.println("IllegalLevelConstructor 1 failed : IllegalArgumentException not thrown.");
		} catch(IllegalArgumentException e){
			System.out.println("IllegalLevelConstructor 1 passed : " + e.getMessage());
		}
	}
	
	//Class Board, method cellAt, testing accessing a legal position of the board
	@SuppressWarnings("unused")
	public static void LegalcellAt(){
		try{
			Board b = new Board(5, 5);
			Cell c = b.cellAt(2, 2);
			System.out.println("LegalCellAt passed");
		} catch(IllegalArgumentException e){
			System.out.println("LegalCellAt failed : " + e.getMessage());
		}
	}

	//Class Board, method CellAt, testing trying to access cells of illegal positions
	@SuppressWarnings("unused")
	public static void IllegalcellAt(){
		Board b = new Board(10,10);
		try {
			Cell c = b.cellAt(20, 20);
			System.out.println("IllegalCellAt 1 failed : IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalCellAt 1 passed.");
		}
		
		try {
			Cell c = b.cellAt(-10, 2);
			System.out.println("IllegalCellAt 1 failed : IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalCellAt 1 passed.");
		}
		
		try {
			Cell c = b.cellAt(2, -10);
			System.out.println("IllegalCellAt 1 failed : IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalCellAt 1 passed.");
		}
	}
	
	//Class Board, method fillCellAt, testing filling board with legal ChewyObject and position
	public static void LegalfillCellAt(){
		Board b = new Board(10,10);
		try {
			Lokum l = new Lokum("A");
			b.fillCellAt(5, 5, l);
			System.out.println("LegalfillCellAt passed");
		} catch (Exception e){
			System.out.println("LegalfillCellAt failed : " + e.getMessage());
		}
	}

	//Class Board, method fillCellAt, testing filling board with illegal ChewyObject and position
	public static void IllegalfillCellAt(){
		Board b = new Board(10,10);
		try {
			Lokum l = null;
			b.fillCellAt(5, 5, (Lokum)l);
			System.out.println("IllegalfillCellAt 1 failed : Exception about null lokum not thrown.");
		} catch (Exception e){
			System.out.println("IllegalfillCellAt 1 passed.");
		}
		
		try {
			Lokum l = new Lokum("A");
			b.fillCellAt(15, 5, l);
			System.out.println("IllegalfillCellAt 1 failed : IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalfillCellAt 1 passed.");
		}
		
		try {
			Lokum l = new Lokum("A");
			b.fillCellAt(5, 15, l);
			System.out.println("IllegalfillCellAt 1 failed : IllegalArgumentException not thrown.");
		} catch (IllegalArgumentException e){
			System.out.println("IllegalfillCellAt 1 passed.");
		}
	}
	


	//Class Board, method fillCellAt, Class Lokum, method Lokum, testing filling certain spots on board with new Lokums
	public static void prapareBoardForFurtherOp(){
		bPrepared.fillCellAt(0, 0, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(0, 1, new Lokum("green pistachio"));
		bPrepared.fillCellAt(0, 2, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(1, 0, new Lokum("green pistachio"));
		bPrepared.fillCellAt(1, 1, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(1, 2, new Lokum("red rose"));
		bPrepared.fillCellAt(2, 0, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(2, 1, new Lokum("brown hazelnut"));
		bPrepared.fillCellAt(2, 2, new Lokum("red rose"));
		
		if(bPrepared.repOk()){
			System.out.println("Successfull!! Prepared Board is ready to further tests.");
		}
		else
			System.out.println("Fail!! Prepared Board is not ready to further tests.");
		
	}
	
	//Class Level, method repOK, testing RepOK of normal constructor
	public static void initLevel(){
		testLevel = new Level(20, 5, bPrepared, 1);
		
		if(testLevel.repOk()){
			System.out.println("Successfull!! Test level is created.");
			System.out.println(testLevel);
		}
		else
			System.out.println("Fail!! Test level is not created.");
			
		
	}
	
	//Class GamePlay, method GamePlay, testing constructor with legal value
	/*public static void initGame(){	
		testGame = new GamePlay(testLevel);
		
		if(testGame.repOk()){
			System.out.println("Successfull!! Test game is created.");
			System.out.println(testGame);
		}
		else
			System.out.println("Fail!! Test game is not created.");
		
	}
	
	//Class GamePlay, method swap, testing legal horizontal swap
	public static void successfulHorSwap(){
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
	public static void failedHorSwap(){
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
	public static void successfulVerSwap(){
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
	public static void FailedVerSwap(){
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
	public static void successfulCrossSwap(){
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
	public static void FailedCrossSwap(){
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
	
	private static void dropTest(){
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
	
	private static void isThereNothingTest() {
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
	
	
	private static void isThereAvailableMoveTest() {
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
	
	
	private static void fillRandomlyTest(){
		Board b3 = new Board(5, 5);
		Level l3 = new Level(10, 10,b3 , 10);
		GamePlay gp3 = new GamePlay(l3);
		while (gp3.isThereNothing()) {
			gp3.fillAllNothingsRandomly();
			gp3.updateBoard();
		}
		System.out.println(b3);
	}*/
	
	@SuppressWarnings("unused")
	private static void playerTest(){
		Player tPlayer = new Player("Berk");
		System.out.println(tPlayer);
		tPlayer.setName("Can");
		System.out.println(tPlayer);
		
		Player[] testPlayerList = new Player[999];
		
		for(int i=0; i<999; i++){
			testPlayerList[i] = new Player("test");
		}
		for(int i=0; i<999; i++){
			System.out.println(testPlayerList[i]);
		}
		Player.printIdList();
		
	}
	
	@SuppressWarnings("unused")
	private static void xmlWriteTest(){
		Player player = new Player("berk");
		Board board = new Board(5, 5);
		Level level = new Level(500, 50, board, 56);
		GamePlay gp = new GamePlay(level);
		gp.setPlayer(player);
		
		
		
		WriteXMLFile writer = WriteXMLFile.getInstance();
		
		writer.saveGame(gp);
		writer.write();
		
	}
	
	@SuppressWarnings("unused")
	private static void xmlReadTest(){
		ReadXMLFile reader = ReadXMLFile.getInstance();
		
		
		reader.read();
		GamePlay gp = reader.loadGame();
		
		System.out.println(gp.getLevel().getBoard());
		
	}
	
	

		
	public static void main(String[] args) {
		/*LegalBoardConstructor();
		IllegalBoardConstructor();
		LegalLevelConstructor();
		IllegalLevelConstructor();
		LegalcellAt();
		IllegalcellAt();
		LegalfillCellAt();
		IllegalfillCellAt();

		
		System.out.println(testSplitter("Initializing a board by hand. The board will be used further operations."));
		prapareBoardForFurtherOp();
		System.out.println(bPrepared.status());
		System.out.println(bPrepared);
		
		System.out.println(testSplitter("Initializing a level."));
		initLevel();
		
		System.out.println(testSplitter("Initializing a game."));
		initGame();
		
		System.out.println(testSplitter("Successful horizontal swap test."));	
		successfulHorSwap();
		System.out.println("Resetting the prepared board.");
		prapareBoardForFurtherOp();
		
		System.out.println(testSplitter("Successful vertical swap test."));
		successfulVerSwap();
		System.out.println("Resetting the prepared board.");
		prapareBoardForFurtherOp();
		
		System.out.println(testSplitter("Successful cross swap test."));
		successfulCrossSwap();
		System.out.println("Resetting the prepared board.");
		prapareBoardForFurtherOp();
		
		System.out.println(testSplitter("Failed horizontal swap test."));
		failedHorSwap();
		
		System.out.println(testSplitter("Failed vertical swap test."));
		FailedVerSwap();
		
		System.out.println(testSplitter("Failed cross swap test."));
		FailedCrossSwap();
		
		System.out.println(testSplitter("Testing the method that searches for an empty cell on board."));
		isThereNothingTest();
		
		System.out.println(testSplitter("Testing the drop method which make objects fall until their bottom is not empty."));
		dropTest();
		
		System.out.println(testSplitter("Testing the isThereAvailableMove method which searches for an available move."));
		isThereAvailableMoveTest();
		
		System.out.println(testSplitter("Initializing an empty board, a associated level and game,then filling that board randomly."));
		fillRandomlyTest();
		*/
		
		//System.out.println(testSplitter("Player Test"));
		//playerTest();
		
		//System.out.println(testSplitter("XML Write Test"));
		//xmlWriteTest();
		
		//System.out.println(testSplitter("XML Read Test"));
		//xmlReadTest();
		
		//@SuppressWarnings("unused")
		//LoadGameWindow lg = new LoadGameWindow(); 
	}
	

	@SuppressWarnings("unused")
	private static String testSplitter(String s){
		return "\n----------------\n" +
	           "  Next Test  \n" +
			   "Test Description: \n"+s+"\n"+
		       "----------------\n";
	}

	

}
