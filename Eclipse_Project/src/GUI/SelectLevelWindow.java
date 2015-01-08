package GUI;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Logic.GamePlay;
import Logic.LevelSelector;


@SuppressWarnings("serial")
public class SelectLevelWindow extends JFrame{

	private UIButton level1, level2, level3, level4 ,level5, cancel;
	private JPanel buttonPanel;
	private JPanel logoPanel; 
	private UIButton logo;
	private int buttonId;

	
	private GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	private Color uiColor = new Color(100,180,150);
	
	public SelectLevelWindow() {
		super("Game");
		
		prepareFrame();
		
		Interact interact = new Interact();
		
		addButtons(interact);
		
		
	}
	
	private class Interact implements MouseListener {


		@SuppressWarnings("unused")
		@Override
		public void mouseClicked(MouseEvent e) {
			Object srcButton =  e.getSource();
			if(srcButton == cancel){
				dispose();
				MainMenuWindow menu = new MainMenuWindow();
			}else{
				if(srcButton == level1){
					buttonId = 1;
				}else if(srcButton == level2){
					buttonId = 2;
				}else if(srcButton == level3){
					buttonId = 3;
				}else if(srcButton == level4){
					buttonId = 4;
				}else if(srcButton == level5){
					buttonId = 5;
				}
				GamePlay gp = LevelSelector.createGamePlay(buttonId);
				
				MainGameWindow gs = new MainGameWindow(gp);
				
				gs.playTheGame(gp);
			}
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	
	private void addButtons(Interact interact){
		level1 = new UIButton("menu", "Level 1" , uiColor);
		buttonPanel.add(level1);
		level1.addMouseListener(interact);
		
		level2 =  new UIButton("menu", "Level 2" , uiColor);
		buttonPanel.add(level2);
		level2.addMouseListener(interact);
		
		level3 = new UIButton("menu", "Level 3" , uiColor);
		buttonPanel.add(level3);
		level3.addMouseListener(interact);
		
		
		level4 = new UIButton("menu", "Level 4" , uiColor);
		buttonPanel.add(level4);
		level4.addMouseListener(interact);
		
		level5 = new UIButton("menu", "Level 5" , uiColor);
		buttonPanel.add(level5);
		level5.addMouseListener(interact);
		
		cancel = new UIButton("menu", "Cancel" , uiColor);
		buttonPanel.add(cancel);
		cancel.addMouseListener(interact);
	}
	
	private void prepareFrame(){
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(0);
		
		setVisible(true);
		
		setLocationRelativeTo(null);
		device.setFullScreenWindow(this);
		
		setLayout(new GridLayout(2,1));
		
		logoPanel = new JPanel();
		logoPanel.setLayout(new GridLayout(3,1));
		add(logoPanel);
		logoPanel.setSize(300, 600);
		logoPanel.setBackground(uiColor);
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(uiColor);
		
		logoPanel.add(emptyPanel);
		logoPanel.add(emptyPanel);

		
		buttonPanel = new JPanel();
		add(buttonPanel);
		buttonPanel.setLayout(new GridLayout(2, 3));
		buttonPanel.setBackground(uiColor);
		
		logo = new UIButton("logo", "", uiColor);
		logoPanel.add(logo);
		logoPanel.setLocation(300, 300);

	
	}
}

