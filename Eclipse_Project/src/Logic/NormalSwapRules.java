package Logic;

public class NormalSwapRules implements SwapRules {
	
	public static NormalSwapRules instance;

	public static SwapRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new NormalSwapRules();	
		}
		return instance;
	}
	
	private NormalSwapRules() {}
	
	@Override
	public boolean isValid(GamePlay gp, Move move) {
		
		if (gp.isGameOver()) {
			return false;
		}
		
		int x1 = move.getPosition1().getX();
		int y1 = move.getPosition1().getY();
		int x2 = move.getPosition2().getX();
		int y2 = move.getPosition2().getY();
		
		Board board = gp.getLevel().getBoard();

		if (!(board.inBoard(x1, y1) && board.inBoard(x2, y2))) {
			System.err.println("inBoard");
			return false;
		}
						
		if (!isConsecutive(x1, y1, x2, y2)) {
			System.err.println("consecutive");
			return false;
		}
		
		ChewyObject o1 = gp.getLevel().getBoard().cellAt(x1, y1).getCurrentObject();
		ChewyObject o2 = gp.getLevel().getBoard().cellAt(x2, y2).getCurrentObject();
		
		if (!areLokums( o1, o2) ) {
			System.err.println("areLokums");
			return false;
		}
		
		if(isOneColorBomb(o1, o2)) {
			
			return true;
		}
		
		MatchingScaleInformerFactory f = MatchingScaleInformerFactory.getInstance();
				
		MatchingScaleInformer msi1 = f.getMatchingScaleInformer(board, x1, y1, 
				o2);		
		MatchingScaleInformer msi2 = f.getMatchingScaleInformer(board, x2, y2, 
				o1);
		
		if (msi1.horizontalMatchTotalScale() >= 3) {
			
			return true;
		}
		System.err.println("hm");
		
		if (msi1.verticalMatchTotalScale() >= 3) {
			System.err.println("vm");
			return true;
		}
		
		if (msi2.horizontalMatchTotalScale() >= 3) {
			System.err.println("hm2");
			return true;
		}
		
		if (msi2.verticalMatchTotalScale() >= 3) {
			System.err.println("vm2");
			return true;
		}
		
		return false;
	}
	
	/**@requires: o1 and o2 are lokums*/
	private boolean isOneColorBomb(ChewyObject o1, ChewyObject o2) {
		// TODO Auto-generated method stub
		Lokum l1 = (Lokum)o1;
		if (l1.getSpecialType().equalsIgnoreCase("Color Bomb")) {
			return true;
		}
		
		Lokum l2 = (Lokum)o2;
		if (l2.getSpecialType().equalsIgnoreCase("Color Bomb")) {
			return true;
		}
		
		return false;
	}

	private boolean areLokums(ChewyObject o1, ChewyObject o2) {
		// TODO Auto-generated method stub
		return (o1 instanceof Lokum) && (o2 instanceof Lokum);
	}

	private boolean isConsecutive(int x1, int y1, int x2, int y2) {
		int xdif = Math.abs(x1 - x2);
		int ydif = Math.abs(y2 - y1);
		int totalDif = xdif + ydif;
		return totalDif >= 0 && totalDif <= 2;
	}


}
