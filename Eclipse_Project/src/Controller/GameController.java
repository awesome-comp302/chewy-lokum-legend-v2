package Controller;
import java.io.File;

import javax.swing.JOptionPane;

import XML.ReadXMLFile;
import GUI.MainGameWindow;
import Logic.Board;
import Logic.GamePlay;
import Logic.Level;
import Logic.MatchingScaleInformer;
import Logic.Player;


public class GameController implements Runnable {
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private boolean activity = false;

	private Board board;
	private Level level;
	private GamePlay gp;
	private MainGameWindowController mgwCont; 
	private MainGameWindow mgw;

	
	public void run() {
		
		File f = new File("file.xml");
		if(f.exists() && !f.isDirectory()) {
			ReadXMLFile reader = ReadXMLFile.getInstance();
			reader.read();
			GamePlay gp = reader.loadGame();
			this.gp = gp;
			mgw = new MainGameWindow(this.gp);
			mgwCont = new MainGameWindowController(mgw);
			mgwCont.setGC(this);
		
		}else{
			board = new Board(10, 10);
			level = new Level(10000, 20,board, 1);
			gp = new GamePlay(level);
			gp.setPlayer(new Player("Player1"));
			gp.initBoard();
			mgw = new MainGameWindow(gp);
			mgwCont = new MainGameWindowController(mgw);
			mgwCont.setGC(this);
		}
		

		mgwCont.updateBoard(gp);// give the initial board to gui at here
		
		while(true){

			if(activity){ //GUI will set activity to true, if any interaction is done 
				if(gp.swap(x1,y1,x2,y2)){//GUI will set x1,y1,x2,y2 with the interaction 
					while (gp.isThereNothing() || activity) {
						activity = false;

						if(gp.isThereNothing()){
							mgwCont.updateBoard(gp);
							
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							gp.fillAllNothingsRandomly();
						}
						

						mgwCont.updateBoard(gp);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						// generate scaling matrix
						MatchingScaleInformer[][] scaleMatrix = gp.generateScaleMatrix();

						// erase all matched cells
						gp.eraseAllMatches(scaleMatrix);
						
						mgwCont.updateBoard(gp);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						// update the score;
						gp.setScore(gp.calculateScore(scaleMatrix));

						// drop objects if necessary
						gp.dropAll();	
						
					}
					mgwCont.updateBoard(gp);
					
				}else{
					//notifies the GUI about the swap is illegal, and GUI throws a warning to player
					JOptionPane.showMessageDialog(null, "move is illegal", "Error", JOptionPane.WARNING_MESSAGE);
					activity = false;
				}
				
			}
			
			if(gp.isGameOver()) break;

			
		}
		

	}
	
	public void setSwapCoordinates(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
		activity= true;
	}

	
	
}
