package GUI;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import XML.WriteXMLFile;
import Logic.Board;
import Logic.Cell;
import Logic.GamePlay;
import Logic.GameUpdateListener;
import Logic.LevelSelector;
import Logic.UpdateType;


@SuppressWarnings("serial")
public class MainGameWindow extends JFrame implements GameUpdateListener {
	private JLabel lgoal;
	private JLabel lmoves;
	private JLabel lscore;
	private JLabel lspeMove;
	private JLabel ltime;
	private JLabel llgoal;
	private JLabel llmoves;
	private JLabel llscore;
	private JLabel llspMove;
	private JLabel lltime;
	private JCheckBox SpeMoveCB;
	private UIButton saveButton;
	private UIButton quitButton;
	private UIButton retryButton;
	private UIButton nextLevelButton;
	private UIButton mainMenuButton;
	private JPanel 	boardHolder;
	private JPanel	buttonHolder;
	private JPanel 	boardPanel;
	private JPanel endGamePanel;
	private Interact interact;
	private Color uiColor = new Color(154,173,180);
	private Color gameColor = new Color(100,180,150);
	
	
	private GamePlay gp;
	private int score;
	private int remMove;
	private CellButton buttons[][];
	
	private static int sWidth;
	private static int sHeight;

	
	private GraphicsDevice device = GraphicsEnvironment
	        .getLocalGraphicsEnvironment().getScreenDevices()[0];
	private CellButton click1;
	private CellButton click2;

	
	public MainGameWindow(GamePlay gap){
		super("Game");
		
		sWidth = device.getDisplayMode().getWidth();
		sHeight = device.getDisplayMode().getHeight();

		gp = gap;
		
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(sWidth, sHeight);
		setLocationRelativeTo(null);
		
		
		setResizable(false);
		
		

		getContentPane().setLayout(new GridBagLayout());
		setLocationRelativeTo(null);
		interact = new Interact();
		GridBagConstraints c = new GridBagConstraints();
		
		buttonHolder = new JPanel();
		buttonHolder.setBackground(uiColor);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		add(buttonHolder,c);
		buttonHolder.setBorder(BorderFactory.createLineBorder(Color.black));
		
		lgoal = new JLabel("Goal");
		c.gridheight = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		buttonHolder.add(lgoal);

		llgoal = new JLabel(String.valueOf(gap.getLevel().getPassingScore()));
		buttonHolder.add(llgoal);
		
		if(gap.getMovementsLeft() > 0){
			lmoves = new JLabel("Moves");
			c.gridheight = 1;
			c.weightx = 0.5;
			c.anchor = GridBagConstraints.PAGE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 1;
			c.gridy = 0;
			buttonHolder.add(lmoves);
	
			llmoves = new JLabel(String.valueOf(gap.getMovementsLeft()));
			buttonHolder.add(llmoves);
		}
		
		lscore = new JLabel("Score");
		c.gridheight = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		buttonHolder.add(lscore);

		llscore = new JLabel(String.valueOf(gap.getScore()));
		buttonHolder.add(llscore);

		lltime = new JLabel(String.valueOf(gap.getTimeLeft()));
		ltime = new JLabel("Time");
		if(gap.getTimeLeft() > 0){
			buttonHolder.add(ltime);
			buttonHolder.add(lltime);
		}

		lspeMove = new JLabel("Special Moves");
		llspMove = new JLabel(String.valueOf(gap.getSpecialMovementsLeft()));
		SpeMoveCB = new JCheckBox("Activate SM");
		if(gap.getSpecialMovementsLeft() > 0){
			SpeMoveCB.setBackground(gameColor);
			SpeMoveCB.setBorder(BorderFactory.createLineBorder(Color.black));
			SpeMoveCB.setBorderPainted(true);
			
			buttonHolder.add(lspeMove);
			buttonHolder.add(llspMove);
			buttonHolder.add(SpeMoveCB);
		}
		
		saveButton = new UIButton("game", "Save", uiColor);
		c.gridheight = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		buttonHolder.add(saveButton);
		saveButton.addMouseListener(interact);	
		
		quitButton = new UIButton("game", "End Game", uiColor);
		c.gridheight = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		buttonHolder.add(quitButton);
		quitButton.addMouseListener(interact);
		
		boardHolder = new JPanel();
		boardHolder.setBackground(gameColor);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		add(boardHolder,c);
		
		boardPanel = new JPanel();
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 0.9;

		boardPanel.setBackground(gameColor);
		
		boardHolder.add(boardPanel,c);
		boardHolder.setVisible(true);
		
		


		
		Board b = gp.getBoard();
		
		boardPanel.setLayout(new GridLayout(b.getHeight(),b.getWidth()));
		
		buttons = new CellButton[b.getHeight()][b.getWidth()];
		
		for(int i = 0; i < b.getWidth(); i++){
			for(int j = 0; j < b.getHeight(); j++){
				Cell curr = b.cellAt(j, i);
				buttons[j][i] = new CellButton(curr,j,i);
				buttons[j][i].setBorder(BorderFactory.createLineBorder(gameColor));
				boardPanel.add(buttons[j][i]);
				buttons[j][i].addMouseListener(interact);
			}
		}

		setVisible(true);
		
	}
	
	private class Interact implements MouseListener {


		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				
				Object srcButton =  e.getSource();
				if (srcButton == saveButton) {
					saveButtonClicked();
				} else if (srcButton.getClass() == CellButton.class){
					cellClicked((CellButton)srcButton);
				} else if (srcButton == quitButton){
					onGameUpdate(gp,UpdateType.showEndGame);
				} else if(srcButton == nextLevelButton){
					int levelID = gp.getLevelId()+1;

					
					GamePlay gap = LevelSelector.createGamePlay(levelID);
					
					dispose();
					
					MainGameWindow gs = new MainGameWindow(gap);
					
					gs.playTheGame();
					System.out.println("Game is done");
					
				} else if(srcButton == mainMenuButton){
					exitButtonClicked();
				} else if(srcButton == retryButton){
					int levelID = gp.getLevelId();

					
					GamePlay gp = LevelSelector.createGamePlay(levelID);
					
					dispose();
					
					MainGameWindow gs = new MainGameWindow(gp);
					
					gs.playTheGame();
				}
				
			}else if(e.getButton() == MouseEvent.BUTTON3){
				releaseCells();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}	
	}
	
	
	private void waitGame(int milliseconds){
		
		int refTime = (int) System.currentTimeMillis();
		int curr = (int) System.currentTimeMillis();
		
		while((curr - refTime) < milliseconds){
			curr = (int) System.currentTimeMillis();
		}
		

	}

	@Override
	public void onGameUpdate(final GamePlay gp, final UpdateType type) {

		setFocusable(false);


		if(type == UpdateType.all){
			
			System.out.println("All");
			score = gp.getScore();
			remMove = gp.getMovementsLeft();

			Board b = gp.getBoard();

			boardPanel.setLayout(new GridLayout(b.getHeight(),b.getWidth()));

			llscore.setText(String.valueOf(score));
			llmoves.setText(String.valueOf(remMove));
			
			

			for(int i = 0; i < b.getWidth(); i++){
				for(int j = 0; j < b.getHeight(); j++){
					Cell curr = b.cellAt(j, i);
					buttons[j][i].updateButton(curr);
					boardPanel.add(buttons[j][i]);
				}
			}

			boardPanel.updateUI();
			buttonHolder.updateUI();

		}else if (type == UpdateType.boardPanel){
			Board b = gp.getBoard();

			boardPanel.setLayout(new GridLayout(b.getHeight(),b.getWidth()));

			for(int i = 0; i < b.getWidth(); i++){
				for(int j = 0; j < b.getHeight(); j++){
					Cell curr = b.cellAt(j, i);
					buttons[j][i].updateButton(curr);
					boardPanel.add(buttons[j][i]);
					buttons[j][i].setBorder(BorderFactory.createLineBorder(gameColor));
				}
			}			
			
			boardPanel.paint(boardPanel.getGraphics());
			
			waitGame(400);
			
		}else if(type == UpdateType.timeLabel){
			lltime.setText(String.valueOf(gp.getTimeLeft()));
			buttonHolder.updateUI();

		}else if(type == UpdateType.specialMovementLeftLabel){
			llspMove.setText(String.valueOf(gp.getSpecialMovementsLeft()));
			buttonHolder.updateUI();

		}else if(type == UpdateType.showEndGame){
			setFocusable(true);
			boardPanel.removeAll();
			
			endGamePanel = new JPanel();
			endGamePanel.setSize(400,400);
			quitButton.setVisible(false);
			JPanel empty = new JPanel();
			empty.setSize(500,500);

			if (score >= gp.getLevel().getPassingScore()) {
				
				JLabel lb = new JLabel("Level Succesful");
				lb.setForeground(Color.white);
				nextLevelButton = new UIButton("game", "Next Level", gameColor);
				nextLevelButton.addMouseListener(interact);
				mainMenuButton = new UIButton("game", "Main Menu", gameColor);
				mainMenuButton.addMouseListener(interact);
				endGamePanel.setLayout(new GridLayout(3,1));	
				endGamePanel.add(lb);
				endGamePanel.add(nextLevelButton);
				endGamePanel.add(mainMenuButton);
				endGamePanel.setBackground(gameColor);
				
				
				boardPanel.add(endGamePanel);
				
			} else {
				JLabel lb = new JLabel("Level Failed!");
				lb.setForeground(Color.red);
				retryButton = new UIButton("game", "Retry", gameColor);
				retryButton.addMouseListener(interact);
				mainMenuButton = new UIButton("game", "Main Menu", gameColor);
				mainMenuButton.addMouseListener(interact);
				endGamePanel.setLayout(new GridLayout(3,1));
				
				endGamePanel.add(lb);
				endGamePanel.add(retryButton);
				endGamePanel.add(mainMenuButton);
				endGamePanel.setBackground(gameColor);
				
				boardPanel.add(endGamePanel);
			}

		}else if(type == UpdateType.scoreLabel){
			score = gp.getScore();
			llscore.setText(String.valueOf(score));
			buttonHolder.updateUI();

		}else if(type == UpdateType.movementLeftLabel){
			remMove = gp.getMovementsLeft();
			llmoves.setText(String.valueOf(remMove));

			buttonHolder.updateUI();

		}

		setFocusable(true);

	}
	
	public void playTheGame(){
		gp.addListener(this);
		gp.initBoard();
	}
	
	
	public void cellClicked(CellButton cb){
		if(click1 != null && (cb.coordX != click1.coordX || cb.coordY != click1.coordY)){
			click2 = cb;
			if(click1 == null || click2 == null){ System.out.println("ouch"); }
			click1.setBorder(BorderFactory.createEmptyBorder());
			click2.setBorder(BorderFactory.createEmptyBorder());
			System.out.println("both clicked");
			sendSwap();
		}else {
			click1 = cb;
			cb.setBorder(BorderFactory.createLineBorder(Color.red));
		}
	}
	
	private void sendSwap(){
		
		boolean swapped;
		if (SpeMoveCB.isSelected()) {
			swapped = gp.specialSwap(click1.coordX, click1.coordY, click2.coordX, click2.coordY);
		} else {
			System.out.println("swapEnter");
			swapped = gp.swap(click1.coordX, click1.coordY, click2.coordX, click2.coordY);
			System.out.println("swapEnd");
		}
		System.out.println(swapped);
		if (swapped) {
			System.out.println("swapped");
			gp.updateBoard();
		}
		
		releaseCells();
	}
	
	public void saveButtonClicked() {
		if(gp.getLevel().hasTimer()){
			gp.stopTimer();
		}
		
		FileDialog fd = new FileDialog(this, "Save Game", FileDialog.SAVE);
		fd.setFile("*.xml");
		fd.setVisible(true);
		fd.setAlwaysOnTop(true);
		String filename = fd.getFile();
		
		if (filename != null){
			WriteXMLFile.getInstance().saveGame(gp);
			WriteXMLFile.getInstance().write(filename);
			setAlwaysOnTop(false);
			JOptionPane.showMessageDialog(null, "Game Saved");
			if(gp.getLevel().hasTimer()){
				gp.startTimer();
			}

		}
		
	}

	public void exitButtonClicked() {
		dispose();
		MainMenuWindow.getInstance().setFullScreen(true);
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

}