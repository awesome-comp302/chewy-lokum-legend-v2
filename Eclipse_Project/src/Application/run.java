package Application;
import GUI.BackgroundWindow;
import GUI.MainMenuWindow;
import GUI.SelectLevelWindow;


public class run {

	public static void main(String[] args) {
		BackgroundWindow.getInstance();
		SelectLevelWindow.getInstance().setVisible(false);
		MainMenuWindow.getInstance(); 
	}

}
