package GUI;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import XML.ReadXMLFile;




@SuppressWarnings("serial")
public class MainMenuWindow extends JFrame {
	private UIButton startGameButton;
	private UIButton loadGameButton;
	private UIButton exitButton;
	private UIButton logo;
	private JPanel buttonPanel;
	private JPanel logoPanel;
	private GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	private static int sWidth;
	private static int sHeight;
	
	
	private Color uiColor = new Color(100,180,150);
	
	private static MainMenuWindow instance;
	
	public static MainMenuWindow  getInstance(){
		if(instance == null) instance = new MainMenuWindow();
		return instance;
	}
	
	private MainMenuWindow() {
		super("Game");
		
		sWidth = device.getDisplayMode().getWidth();
		sHeight = device.getDisplayMode().getHeight();
		
		prepareFrame();
		
		Interact interact = new Interact();
		
		addButtons(interact);
		
		
	}
	
	public void setFullScreen(boolean cond){
		if (cond){
			setAlwaysOnTop(true);
			setVisible(true);
		}else{
			setAlwaysOnTop(false);
			setVisible(false);
		}
	}
	
	private class Interact implements MouseListener {


		@Override
		public void mouseClicked(MouseEvent e) {
			Object srcButton =  e.getSource();
			if (srcButton == startGameButton) {
				SelectLevelWindow.getInstance().setFullScreen(true);
				setFullScreen(false);
			}  else if (srcButton == loadGameButton) {
				loadButtonClicked();

			} else if (srcButton == exitButton) {
				System.exit(0);
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
		
		
		
		startGameButton = new UIButton("menu", "Start", uiColor);
		buttonPanel.add(startGameButton);
		startGameButton.addMouseListener(interact);
		
		
		loadGameButton = new UIButton("menu", "Load", uiColor);
		buttonPanel.add(loadGameButton);
		loadGameButton.addMouseListener(interact);
		
		
		exitButton = new UIButton("menu", "Exit", uiColor);
		buttonPanel.add(exitButton);
		exitButton.addMouseListener(interact);
		
		buttonPanel.updateUI();
	}
	
	private void prepareFrame(){
		setResizable(false);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(0);
		
		setFullScreen(true);
		
		setSize(sWidth, sHeight);
		setLocationRelativeTo(null);
		
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
		buttonPanel.setLayout(new GridLayout(1, 3));
		buttonPanel.setBackground(uiColor);
		
		logo = new UIButton("logo", "", uiColor);
		logoPanel.add(logo);
		logoPanel.setLocation(300, 300);
		

	}
	
	private void loadButtonClicked(){
		setAlwaysOnTop(false);
		
		FileDialog fd = new FileDialog(this, "Load Game", FileDialog.LOAD);
		fd.setFile("*.xml");
		fd.setVisible(true);
		fd.setAlwaysOnTop(true);
		String filename = fd.getFile();
		
		setAlwaysOnTop(true);
		
		if (filename != null){
			ReadXMLFile.getInstance().read(filename);
			MainGameWindow gameScreen = new MainGameWindow(ReadXMLFile.getInstance().loadGame());
			gameScreen.playTheGame();
			setFullScreen(false);
		}

	}

}
