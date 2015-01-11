package Logic;

import java.util.Random;


public class SpecialEraseRules implements EraseRules {

	private static SpecialEraseRules instance;
	private boolean erasePool[][];
	private boolean lastMoveUsed = false;
	
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
		// TODO Auto-generated method stub
		Board board = gp.getLevel().getBoard();
		erasePool = new boolean[board.getHeight()][board.getWidth()];
		if (gp.getLastMove() != null && !lastMoveUsed) {
			markForCombos(gp);
			markForColorBomb(gp);
			
			System.out.println("After color bomb marking");
			for (int i = 0; i < erasePool.length; i++) {
				for (int j = 0; j < erasePool[0].length; j++) {
					System.out.print(erasePool[i][j] + " ");
				}
				System.out.println();
			}
			
			lastMoveUsed = true;
		}
		markForWrappeds(gp);
		markForStripes(gp);
		
		/*
		 * Fill here to add erasing behaviour
		 */
		
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

	private void markForColorBomb(GamePlay gp) {
		Move move = gp.getLastMove();
		String otherType = "";
		Position cbp = null;
		if (move.getSpecialType1().equals("Color Bomb")) {
			if (move.getSpecialType2().equals("Regular")) {
				otherType = move.getType2();
				//cbp = move.getPosition2();
			}
		} else if (move.getSpecialType2().equals("Color Bomb")) {
			if (move.getSpecialType1().equals("Regular")) {
				otherType = move.getType1();
				//cbp = move.getPosition1();
			}
		}
		
		/*if (cbp != null) {
			erasePool[cbp.getY()][cbp.getX()] = true;
		}*/
		if (!otherType.isEmpty()) {
			markSame(gp.getBoard(), otherType);
		}
	}

	private void markForCombos(GamePlay gp) {
		Move lm = gp.getLastMove();
		String st1 = lm.getSpecialType1();
		String st2 = lm.getSpecialType2();
		Position p1 = lm.getPosition1();
		Position p2 = lm.getPosition2();
		Board b = gp.getBoard();
		
		if (st1.equals("Color Bomb") && st2.equals("Color Bomb")) {
			for (int i = 0; i < erasePool.length; i++) {
				for (int j = 0; j < erasePool[0].length; j++) {
					erasePool[i][j] = true;
				}
			}
		}
		
		//Striped Striped
		else if (st1.endsWith("Striped") && st2.endsWith("Striped")) {
			if (st1.startsWith("Horizontal")) {
				markHor(b, p2.getY());
				markVer(b, p1.getX());
			}
		}
		
		//Striped - Color Bomb
		else if (st1.endsWith("Striped") && st2.equals("Color Comb")
				|| st1.equals("Color Comb") && st2.endsWith("Striped")) {
			
			if (st1.endsWith("Striped")) {
				markSame(b, lm.getType1());
			}
			
			else if (st2.endsWith("Striped")) {
				markSame(b, lm.getType2());
			}
		}
		
		//Wrapped-Color Bomb
		else if (st1.equals("Wrapped") && st2.equals("Color Comb")
				|| st1.equals("Color Comb") && st2.equals("Wrapped")) {
			
			
			if (st1.equals("Wrapped"))
				markSame(b, lm.getType1());
			else 
				markSame(b, lm.getType2());
			
			String randomType = Lokum.possibleTypes[new Random().nextInt(Lokum.possibleTypes.length)];
			markSame(b, randomType);
		}
		
		else if (st1.endsWith("Striped") && st2.equals("Wrapped")
				|| st1.equals("Wrapped") && st2.endsWith("Striped")) {
			markHor(b, p1.getY());
			markHor(b, p1.getY() - 1);
			markHor(b, p1.getY() + 1);
			
			markVer(b, p1.getX());
			markVer(b, p1.getX() + 1);
			markVer(b, p1.getX() - 1);
		}
		
		
	}
	
	
	private void markSame(Board b, String type1) {
		for (int x = 0; x < b.getWidth(); x++) {
			for (int y = 0; y < b.getHeight(); y++) {
				ChewyObject co = b.cellAt(x, y).getCurrentObject();
				if (co instanceof Lokum) {
					Lokum l = (Lokum)co;
					if (l.getSpecialType().equals("Regular")) {
						if (l.getType().equals(type1)) {
							erasePool[y][x] = true;
						}
					}
				}
			}
		}
		
	}

	private void markHor(Board b, int y) {
		if (b.inBoard(0, y)) { //secure programming
			for (int i = 0; i < 6; i++) {
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
		lastMoveUsed = false;
	}

	public static SpecialEraseRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new SpecialEraseRules();
		}
		return instance;
	}
	
	private SpecialEraseRules(){}

}
