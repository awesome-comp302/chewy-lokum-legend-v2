package Controller;
import java.awt.Color;

import javax.swing.BorderFactory;

import XML.WriteXMLFile;
import GUI.CellButton;
import GUI.MainGameWindow;
import Logic.GamePlay;
import Logic.Move;

public class MainGameWindowController {
	
	@SuppressWarnings("unused")
	private MainGameWindow view;
	
	public static GamePlay gp;
	private static boolean specialButton;
	
	private CellButton click1;
	private CellButton click2;
	
	public MainGameWindowController(MainGameWindow view) {
		this.view = view;
	}
	
	public void setGP(GamePlay gap){
		gp = gap;
	}
	
	public void setSpecial(boolean b) {
		specialButton = b;
		System.out.println(specialButton);
	}

	public void saveExitButtonClicked(GamePlay gp) {
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();
		System.exit(0);
	}
	
	public void cellClicked(CellButton cb){
		if(click1 != null && (cb.coordX != click1.coordX || cb.coordY != click1.coordY)){
			click2 = cb;
			if(click1 == null || click2 == null){ System.out.println("ouch"); }
			click1.setBorder(BorderFactory.createEmptyBorder());
			click2.setBorder(BorderFactory.createEmptyBorder());
			sendSwap();
		}else {
			click1 = cb;
			cb.setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}
	
	public void releaseCells(){
		if(click1 != null){
			click1.setBorder(BorderFactory.createEmptyBorder());
			click1 = null;
		}
		if(click2 != null){
			click2.setBorder(BorderFactory.createEmptyBorder());
			click2 = null;
		}
	}
	
	public void sendSwap(){
		System.out.println(specialButton);
		
		boolean swapped;
		if (specialButton) {
			swapped = gp.specialSwap(click1.coordX, click1.coordY, click2.coordX, click2.coordY);
		} else {
			swapped = gp.swap(click1.coordX, click1.coordY, click2.coordX, click2.coordY);
		}
		
		if (swapped) {
			gp.updateBoard();
		}
		
		releaseCells();
	}

	public void saveButtonClicked(GamePlay gp2) {
		WriteXMLFile.getInstance().saveGame(gp);
		WriteXMLFile.getInstance().write();
	}

	public void exitButtonClicked(GamePlay gp2) {
		// to do something to end the gameplay
		view.dispose();
	}
	
	

}
