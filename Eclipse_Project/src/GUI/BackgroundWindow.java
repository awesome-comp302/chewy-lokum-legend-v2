package GUI;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class BackgroundWindow extends JFrame{
	
	private GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	
	private static int sWidth;
	private static int sHeight;
	
	
	private Color uiColor = new Color(100,180,150);
	
	private static BackgroundWindow instance;
	
	public static BackgroundWindow  getInstance(){
		if(instance == null) instance = new BackgroundWindow();
		return instance;
	}
	
	private BackgroundWindow() {
		super("Game");
		
		sWidth = device.getDisplayMode().getWidth();
		sHeight = device.getDisplayMode().getHeight();
		setSize(sWidth, sHeight);
		
		JPanel bg = new JPanel();
		bg.setBackground(uiColor);
		add(bg);
		setUndecorated(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
	}

}
