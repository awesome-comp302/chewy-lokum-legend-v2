package GUI;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.MainGameWindowController;
import Logic.Board;
import Logic.Cell;
import Logic.GamePlay;


@SuppressWarnings("serial")
public class MainGameWindow extends JFrame {
	private JLabel lgoal;
	private JLabel lmoves;
	private JLabel lscore;
	private JLabel llgoal;
	private JLabel llmoves;
	private JLabel llscore;
	private JButton saveExitButton;
	private JPanel 	boardHolder;
	private JPanel	buttonHolder;
	private JPanel 	boardPanel;
	private Interact interact;
	
	private GamePlay gp;
	private int score;
	private int remMove;
	private MainGameWindowController controller;
	
	public MainGameWindow(GamePlay gap){
		super("Game");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		controller = new MainGameWindowController(this);
		
		setResizable(false);
		
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(2);
		
		getContentPane().setLayout(new GridBagLayout());
		setSize(800, 600);
		setLocationRelativeTo(null);
		interact = new Interact();
		GridBagConstraints c = new GridBagConstraints();
		
		buttonHolder = new JPanel();
		buttonHolder.setBackground(Color.RED);
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
		
		saveExitButton = new JButton("Save and Exit");
		c.gridheight = 1;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		buttonHolder.add(saveExitButton);
		//saveExitButton.addActionListener(interact);	
		
		boardHolder = new JPanel();
		boardHolder.setBackground(Color.blue);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		add(boardHolder,c);
		
		boardPanel = new JPanel();
		updateBoard(gap);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 0.9;
		boardHolder.add(boardPanel,c);
		boardHolder.setVisible(true);
		
		
		//pack();
		setVisible(true);
	}
	
	private class Interact implements MouseListener {


		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Object srcButton =  e.getSource();
			if (srcButton == saveExitButton) {
				controller.saveExitButtonClicked(gp);
			} else if (srcButton.getClass() == CellButton.class){
				controller.cellClicked((CellButton)srcButton);
				
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
	
	public MainGameWindowController getController(){
		return controller;
	}
	
	public void updateBoard(GamePlay gp){
		score = gp.getScore();
		remMove = gp.getMovementsLeft();

		boardPanel.removeAll();
		llscore.setText(String.valueOf(score));
		llmoves.setText(String.valueOf(remMove));
		
		if (score >= gp.getLevel().getPassingScore()) {
			JLabel win = new JLabel("You WIN \n:D");
			boardPanel.add(win);
		} else if(remMove == 0){
			JLabel lost = new JLabel("GAME OVER \n:'(");
			boardPanel.add(lost);
		} else {
			this.gp = gp;
			Board b = gp.getLevel().getBoard();
			
			boardPanel.setLayout(new GridLayout(b.getHeight(),b.getWidth()));
			
			llscore.setText(String.valueOf(score));
			llmoves.setText(String.valueOf(remMove));
			
			for(int i = 0; i < b.getWidth(); i++){
				for(int j = 0; j < b.getHeight(); j++){
					Cell curr = b.cellAt(j, i);
					CellButton cb = new CellButton(curr,j,i);
					boardPanel.add(cb);
					cb.addMouseListener(interact);
				}
			}
		}
		boardPanel.updateUI();
		buttonHolder.updateUI();
	}

	public static void playTheGame(GamePlay gp2) {
		// TODO Auto-generated method stub
		
	}
}