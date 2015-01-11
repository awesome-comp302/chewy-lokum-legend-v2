package Logic;



public class SpecialEraseRules implements EraseRules {

	private static SpecialEraseRules instance;
	private boolean erasePool[][];

	
	@Override
	public boolean shouldErased(GamePlay gp, MatchingScaleInformer match,
			Position position) {
		
		return shouldErased(TEMPORARY, gp, match, position);
	}

	@Override
	public boolean shouldErased(int option, GamePlay gp,
			MatchingScaleInformer match, Position position) {
		
		int i = position.getY();
		int j = position.getX();

		
		if (option == PERSISTENT) {
			if (erasePool == null) {
				createErasePool(gp);
			}
		} else { //recreate the erasePool
			createErasePool(gp);
		}
		return erasePool[i][j];
		
		
	}

	private void createErasePool(GamePlay gp) {
		Board board = gp.getLevel().getBoard();
		erasePool = new boolean[board.getHeight()][board.getWidth()];
		
		markForWrappeds(gp);
		markForStripes(gp);
		
		
	}

	private void markForStripes(GamePlay gp) {
		Board b = gp.getLevel().getBoard();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				ChewyObject co = b.cellAt(i, j).getCurrentObject();
				if (co instanceof Lokum) {
					Lokum l = (Lokum)co;
					if (l.getSpecialType().endsWith("Striped")) {
						MatchingScaleInformer msi = MatchingScaleInformerFactory.getInstance().getMatchingScaleInformer(b, i, j);
						if (msi.horizontalMatchTotalScale() >= 3 || msi.verticalMatchTotalScale() >= 3) {
							if (l.getSpecialType().startsWith("V")) {
								markVer(b, i);
							} else {
								markHor(b, j);
							}
						}
					}
				}
			}
		}
	}

	private void markForWrappeds(GamePlay gp) {
		Board b = gp.getLevel().getBoard();
		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				ChewyObject co = b.cellAt(i, j).getCurrentObject();
				if (co instanceof Lokum) {
					Lokum l = (Lokum)co;
					if (l.getSpecialType().equals("Wrapped")) {
						MatchingScaleInformer msi = MatchingScaleInformerFactory.getInstance().getMatchingScaleInformer(b, i, j);
						if (msi.horizontalMatchTotalScale() >= 3 || msi.verticalMatchTotalScale() >= 3) {
							markSquare(b, i, j);
						}
					}
				}
			}
		}	
	}

	private void markSquare(Board b, int x, int y) {
		for (int i = y-1; i <= y + 1; i++) {
			for (int j = x-1; j <= x + 1; j++) {
				if (b.inBoard(j, i)) {
					erasePool[i][j] = true;
				}
			}
		}
		
		
	}

	private void markHor(Board b, int y) {
		if (b.inBoard(0, y)) { //secure programming
			for (int i = 0; i < b.getWidth(); i++) {
				erasePool[y][i] = true;
			}
		}
	}

	private void markVer(Board b,int x) {
		if (b.inBoard(x, 0)) {
			for (int i = 0; i < b.getWidth(); i++) {
				erasePool[i][x] = true;
			}
		}
	}
	
	
	
	
	

	@Override
	public void taskCompleted() {
		erasePool = null;
	}

	public static SpecialEraseRules getInstance() {
		if (instance == null) {
			instance = new SpecialEraseRules();
		}
		return instance;
	}
	
	private SpecialEraseRules(){}


	
	

}
