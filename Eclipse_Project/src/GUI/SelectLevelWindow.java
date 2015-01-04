package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Logic.LevelSelector;


@SuppressWarnings("serial")
public class SelectLevelWindow extends JFrame{

	private JButton level1, level2, level3, level4 ,level5, cancel;
	private JLabel title = new JLabel("Select a Level");
	private JLabel empty = new JLabel("");
	private int buttonId;
	
	public SelectLevelWindow() {
		super("Game");
		
		prepareFrame();
		
		Interact interact = new Interact();
		
		addButtons(interact);
		
		
	}
	
	private class Interact implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object srcButton =  e.getSource();
			if (srcButton == level1) {
				buttonId = 1;
			} else if (srcButton == level2) {
				buttonId = 2;
				
			} else if (srcButton == level3) {
				buttonId = 3;
				
			} else if (srcButton == level4) {
				buttonId = 4;
				
			} else if (srcButton == level5) {
				buttonId = 5;
				
			} else if (srcButton == cancel) {
				buttonId = -1;
				
			}
			
			
			if(buttonId>0){
				
				MainGameWindow.playTheGame(LevelSelector.createGamePlay(buttonId));
				dispose();
				
			}else{
				
				dispose();
				
				@SuppressWarnings("unused")
				MainMenuWindow lg = new MainMenuWindow(); 
			}
			
			
		}
		
		
	}
	
	private void addButtons(Interact interact){
		level1 = new JButton("1");
		add(level1);
		level1.addActionListener(interact);
		
		level2 = new JButton("2");
		add(level2);
		level2.addActionListener(interact);
		
		level3 = new JButton("3");
		add(level3);
		level3.addActionListener(interact);
		
		
		level4 = new JButton("4");
		add(level4);
		level4.addActionListener(interact);
		
		level5 = new JButton("5");
		add(level5);
		level5.addActionListener(interact);
		
		cancel = new JButton("Cancel");
		add(cancel);
		cancel.addActionListener(interact);
	}
	
	private void prepareFrame(){
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(3);
		setVisible(true);
		setLayout(new GridLayout(4, 2));
		setSize(250, 200);
		
		add(title);
		add(empty);
	}
}

