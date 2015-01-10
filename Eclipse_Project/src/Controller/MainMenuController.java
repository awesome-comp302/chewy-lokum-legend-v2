package Controller;
import java.io.File;

import GUI.MainMenuWindow;
import GUI.SelectLevelWindow;


public class MainMenuController {
	private MainMenuWindow view;
	
	public MainMenuController(MainMenuWindow view) {
		this.view = view;
	}

	public void startButtonClicked() {
		File file = new File("file.xml");
		file.delete();

		
		view.dispose();
	}
	

	public void levelButtonClicked() {
		view.dispose();
		
		@SuppressWarnings("unused")
		SelectLevelWindow sl = new SelectLevelWindow(); 
		
		
		
	}
	
	public void loadButtonClicked() {
		

		view.dispose();
	}
	
	public void exitButtonClicked() {
		System.exit(0);
	}

}
