package Controller;
import java.awt.Color;

import javax.swing.BorderFactory;

import XML.WriteXMLFile;
import GUI.CellButton;
import GUI.MainGameWindow;
import Logic.GamePlay;

public class MainGameWindowController {
	private MainGameWindow view;
	public static GameController gc;
	
	private CellButton click1;
	private CellButton click2;
	
	public MainGameWindowController(MainGameWindow view) {
		this.view = view;
	}
	
	public void setGC(GameController gCont){
		gc = gCont;
	}

	public void saveExitButtonClicked(GamePlay gp) {
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();
		System.exit(0);
	}
	
	public void cellClicked(CellButton cb){
		if(click1 != null && (cb.coordX != click1.coordX || cb.coordY != click1.coordY)){
			click2 = cb;
			if(click1 == null || click2 == null || gc == null){ System.out.println("ouch"); }
			sendSwap();
		} else if(click1 != null && cb.coordX == click1.coordX && cb.coordY == click1.coordY) {
			click1.setBorder(BorderFactory.createEmptyBorder());
			click1 = null;
		} else {
			click1 = cb;
			cb.setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}
	
	public void sendSwap(){
		gc.setSwapCoordinates(click1.coordX, click1.coordY, click2.coordX, click2.coordY);
		click1 = null;
		click2 = null;
	}
	
	public void updateBoard(GamePlay gp){
		view.updateBoard(gp);
	}

}
