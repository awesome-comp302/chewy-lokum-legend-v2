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
	
	public MainMenuWindow() {
		super("Game");
		controller = new MainMenuController(this);
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(3);
		setVisible(true);
		setLayout(new GridLayout(3, 1));
		setSize(300, 400);
		
		Interact interact = new Interact();
		
		startGameButton = new JButton("Start");
		add(startGameButton);
		startGameButton.addActionListener(interact);
		
		loadGameButton = new JButton("Load");
		add(loadGameButton);
		loadGameButton.addActionListener(interact);
		
		
		exitButton = new JButton("Exit");
		add(exitButton);
		exitButton.addActionListener(interact);
		
		
	}
	
	private class Interact implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object srcButton =  e.getSource();
			if (srcButton == startGameButton) {
				controller.startButtonClicked();
			} else if (srcButton == loadGameButton) {
				controller.loadButtonClicked();
			} else if (srcButton == exitButton) {
				controller.exitButtonClicked();
			}
		}
		
		
	}
}
