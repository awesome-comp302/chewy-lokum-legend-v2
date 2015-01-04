package GUI;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Controller.MainMenuController;


@SuppressWarnings("serial")
public class MainMenuWindow extends JFrame {
	private JButton startGameButton;
	private JButton loadGameButton;
	private JButton exitButton;
	MainMenuController controller;
	private JButton selectLevel;
	
	public MainMenuWindow() {
		super("Game");
		
		prepareFrame();
		
		Interact interact = new Interact();
		
		addButtons(interact);
		
		
	}
	
	private class Interact implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object srcButton =  e.getSource();
			if (srcButton == startGameButton) {
				controller.startButtonClicked();
			} else if (srcButton == selectLevel) {
				controller.levelButtonClicked();
				
			} else if (srcButton == loadGameButton) {
				controller.loadButtonClicked();
				
			} else if (srcButton == exitButton) {
				controller.exitButtonClicked();
			}
		}
		
		
	}
	
	private void addButtons(Interact interact){
		startGameButton = new JButton("Start");
		add(startGameButton);
		startGameButton.addActionListener(interact);
		
		selectLevel = new JButton("Select Level");
		add(selectLevel);
		selectLevel.addActionListener(interact);
		
		loadGameButton = new JButton("Load");
		add(loadGameButton);
		loadGameButton.addActionListener(interact);
		
		
		exitButton = new JButton("Exit");
		add(exitButton);
		exitButton.addActionListener(interact);
	}
	
	private void prepareFrame(){
		controller = new MainMenuController(this);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(3);
		setVisible(true);
		setLayout(new GridLayout(4, 1));
		setSize(300, 400);
	}
}
