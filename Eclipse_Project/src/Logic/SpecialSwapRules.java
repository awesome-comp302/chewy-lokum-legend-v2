package Logic;

public class SpecialSwapRules implements SwapRules {

	private static SpecialSwapRules instance;
	
	public static SpecialSwapRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new SpecialSwapRules();
		}
		return instance;
	}
	
	private SpecialSwapRules() {}
	
	@Override
	public boolean isValid(GamePlay gp, Move move) {
		
		if (gp.getSpecialMovementsLeft() <= 0) {
			return false;
		}
		
		int x1 = move.getPosition1().getX();
		int y1 = move.getPosition1().getY();
		int x2 = move.getPosition2().getX();
		int y2 = move.getPosition2().getY();
		
		Board board = gp.getLevel().getBoard();
		if (!(board.inBoard(x1, y1) && board.inBoard(x2, y2))) {
			return false;
		}
		
		MatchingScaleInformerFactory f = MatchingScaleInformerFactory.getInstance();
		MatchingScaleInformer match1 = f.getMatchingScaleInformer(board, x1, y1, board.cellAt(x2, y2).getCurrentObject());
		MatchingScaleInformer match2 = f.getMatchingScaleInformer(board, x2, y2, board.cellAt(x1, y1).getCurrentObject() );
		
		if (match1.horizontalMatchTotalScale() >= 3 || 
				match1.verticalMatchTotalScale() >= 3 ||
				match2.horizontalMatchTotalScale() >= 3 ||
				match2.verticalMatchTotalScale() >= 3) {
			return true;
		}
		
		return false;
		
	}

	

}
