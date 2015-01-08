package GUI;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.MainGameWindowController;
import Logic.Board;
import Logic.Cell;
import Logic.GamePlay;
import Logic.GameUpdateListener;
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
	private JButton saveButton;
	private JButton quitButton;
	private JPanel 	boardHolder;
	private JPanel	buttonHolder;
	private JPanel 	boardPanel;
	private Interact interact;
	private Color uiColor = new Color(154,173,180);
	private Color gameColor = new Color(100,180,150);
	
	private GamePlay gp;
	private int score;
	private int remMove;
	private CellButton buttons[][];
	private MainGameWindowController controller;
	

	
	public MainGameWindow(GamePlay gap){
		super("Game");
		gp = gap;
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		controller = new MainGameWindowController(this);
		
		setResizable(false);
		
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(2);
		
		getContentPane().setLayout(new GridBagLayout());
		setSize(600, 600);
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
		
		lspeMove = new JLabel("Special Moves");
		buttonHolder.add(lspeMove);
		
		llspMove = new JLabel(String.valueOf(gap.getSpecialMovementsLeft()));
		buttonHolder.add(llspMove);
		
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
		
		ltime = new JLabel("Time");
		buttonHolder.add(ltime);
		
		lltime = new JLabel(String.valueOf("0"));
		buttonHolder.add(lltime);
		
		SpeMoveCB = new JCheckBox("Activate SM");
		SpeMoveCB.setBackground(gameColor);
		SpeMoveCB.setBorder(BorderFactory.createLineBorder(Color.black));
		SpeMoveCB.setBorderPainted(true);
		
		buttonHolder.add(SpeMoveCB);
		
		saveButton = new JButton("Save");
		c.gridheight = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		buttonHolder.add(saveButton);
		saveButton.addMouseListener(interact);	
		
		quitButton = new JButton("Quit");
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
		
		controller.setGP(gap);
		onGameUpdate(gap, UpdateType.all);

		setVisible(true);
	}
	
	private class Interact implements MouseListener {


		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1){
				
				Object srcButton =  e.getSource();
				if (srcButton == saveButton) {
					controller.saveButtonClicked(gp);
				} else if (srcButton.getClass() == CellButton.class){
					controller.cellClicked((CellButton)srcButton);
				} else if (srcButton == quitButton){
					controller.exitButtonClicked(gp);
				}
				
			}else if(e.getButton() == MouseEvent.BUTTON3){
				controller.releaseCells();
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
			score = gp.getScore();
			remMove = gp.getMovementsLeft();

			boardPanel.removeAll();

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

			boardPanel.removeAll();

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

			boardPanel.updateUI();
			
			boardPanel.paint(boardPanel.getGraphics());
			
			waitGame(400);
			
		}else if(type == UpdateType.timeLabel){
			lltime.setText(String.valueOf(gp.getLevelId()));
			buttonHolder.updateUI();

		}else if(type == UpdateType.specialMovementLeftLabel){
			llspMove.setText(String.valueOf(gp.getSpecialMovementsLeft()));
			buttonHolder.updateUI();

		}else if(type == UpdateType.showEndGame){

			boardPanel.removeAll();

			if (score >= gp.getLevel().getPassingScore()) {
				JLabel win = new JLabel("You WIN \n:D");
				boardPanel.add(win);
			} else if(remMove == 0){
				JLabel lost = new JLabel("GAME OVER \n:'(");
				boardPanel.add(lost);
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
	
	public void playTheGame(GamePlay gp) {
		
		gp.addListener(this);
		gp.initBoard();
		
	}

}