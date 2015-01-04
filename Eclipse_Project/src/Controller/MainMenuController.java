package Controller;
import java.io.File;

import GUI.MainMenuWindow;
import GUI.SelectLevelWindow;


public class MainMenuController {
	private MainMenuWindow view;
	private GameController gc = new GameController();
	private Thread t = new Thread(gc);
	
	public MainMenuController(MainMenuWindow view) {
		this.view = view;
	}

	public void startButtonClicked() {
		File file = new File("file.xml");
		file.delete();
		gc = new GameController();
		t = new Thread(gc);
		t.start();
		
		view.dispose();
	}
	

	public void levelButtonClicked() {
		view.dispose();
		
		@SuppressWarnings("unused")
		SelectLevelWindow sl = new SelectLevelWindow(); 
		
		
		
	}
	
	public void loadButtonClicked() {
		
		
		gc = new GameController();
		t = new Thread(gc);
		t.start();
		view.dispose();
	}
	
	public void exitButtonClicked() {
		System.exit(0);
	}

}
