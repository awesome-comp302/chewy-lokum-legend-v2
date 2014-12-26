package Logic;

public class BoardUpdater {
	
	private int score;
	
	public BoardUpdater(GamePlay gp, RuleEngine rules){
		
		setScore(gp.getScore());
	}	

	private int getScore() {
		return score;
	}

	private void setScore(int score) {
		this.score = score;
	}
	
	public void dropAll(){
		
	}
	
	public void shuffle(){
		
	}
	
	public void eraseAll(){
		
	}
	
	public void fillEmptyCells(){
		
	}
	
	public boolean stillToDo(){
		
		boolean std = true;
		
		return std;
	}
	
	public boolean repOk(){
		
		return true;
	}

}
