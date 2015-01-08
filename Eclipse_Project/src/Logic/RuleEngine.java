package Logic;
public class RuleEngine {

	private static RuleEngine instance;
	// public static final int NO_MATCH = 0, VERTICAL_MATCH = 1,
	// HORIZONTAL_MATCH = 2, VH_MATCH = VERTICAL_MATCH + HORIZONTAL_MATCH;
	// CROSS_MATCH = 3,

	// HC_MATCH = HORIZONTAL_MATCH + CROSS_MATCH,//HORIZONTAL-CROSS
	// VH_MATCH = VERTICAL_MATCH + HORIZONTAL_MATCH,//VERTICAL-HORIZONTAL
	// CV_MATCH = CROSS_MATCH + VERTICAL_MATCH,//CROSS-VERTICAL MARCH
	// HCV_MATCH = HORIZONTAL_MATCH + CV_MATCH;

	public static final int REGULAR = 7;
	public static final int VSTRIPED = 4;
	public static final int HSTRIPED = 8;
	public static final int WRAPPED = 5;
	public static final int COLOR_BOMB = 6;
	
	public static final int SPECIAL_ERASE = 1;
	public static final int NORMAL_ERASE = 2;

	public static final int MINIMUM_MATCH_REQUIRED = 3;

	private RuleEngine() {

	}

	public static RuleEngine getInstance() {
		if (instance == null) {
			instance = new RuleEngine();
		}
		return instance;
	}

	/**
	 * @requires board and cellAt is non null and NO_MATCH should be 0
	 * @ensures returns the value of the correct constant
	 * @param board
	 * @param x1
	 * @param y1
	 * 
	 */
	
	public SwapRules getSwapRules(Move move) {
		if (move.isSpecial()) {
			return SpecialSwapRules.getInstance();
		}
		
		return NormalSwapRules.getInstance();
	}

	public ScoringRules getScoringRules(GamePlay gp) {
		return StandardScoringRules.getInstance();
	}

	public EraseRules getEraseRules(int sel) {
		// TODO Auto-generated method stub
		
		switch (sel) {
		case NORMAL_ERASE:
			return NormalEraseRules.getInstance();
		case SPECIAL_ERASE:
			return SpecialEraseRules.getInstance();
		default:
			return null;
		}
	}

	public GenerationRules getGenerationRules() {
		return StandardGenerationRules.getInstance();
		// TODO Auto-generated method stub
	}

	
	
	
	
	/*public MatchingScaleInformer getMatchingScaleInformer(Board board, int x1,
			int y1, ChewyObject object) {

		MatchingScaleInformer info = new MatchingScaleInformer();
		if (!(object instanceof Matchable)) {
			return info;
		}

		Matchable m = (Matchable) object;

		int up = countTop(board, x1, y1, m);
		int down = countBottom(board, x1, y1, m);
		int right = countRigth(board, x1, y1, m);
		int left = countLeft(board, x1, y1, m);


		info.setUpScale(up);
		info.setDownScale(down);
		info.setRightScale(right);
		info.setLeftScale(left);

		return info;
	}

	public boolean isSwappable(Board board, int x1, int y1, int x2, int y2) {

		if (!(board.inBoard(x1, y1) && board.inBoard(x2, y2))) {
			return false;
		}

		if (!(isConsecutive(x1, y1, x2, y2))) {
			return false;
		}

		ChewyObject o1 = board.cellAt(x1, y1).getCurrentObject();
		ChewyObject o2 = board.cellAt(x2, y2).getCurrentObject();
		if (o1 instanceof Lokum
				&& o2 instanceof Lokum) {
			if (((Lokum) o1).isSpecial() && ((Lokum)o2).isSpecial()){
				return true;
			}
		}
		
		MatchingScaleInformer msi1 = getMatchingScaleInformer(board, x1, y1,
				board.cellAt(x2, y2).getCurrentObject());
		MatchingScaleInformer msi2 = getMatchingScaleInformer(board, x2, y2,
				board.cellAt(x1, y1).getCurrentObject());

		if (!shouldErased(msi1) && !shouldErased(msi2)) {
			return false;
		}

		return true;

	}

	private boolean isConsecutive(int x1, int y1, int x2, int y2) {
		int xdif = Math.abs(x1 - x2);
		int ydif = Math.abs(y2 - y1);
		int totalDif = xdif + ydif;
		return totalDif >= 0 && totalDif <= 2;
	}

	private int countRigth(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;

		int x_count = x1 + 1;
		while (board.inBoard(x_count, y1)) {
			ChewyObject current = board.cellAt(x_count, y1).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				x_count++;
				sum++;
			} else {
				break;
			}
		}

		return sum;
	}

	private int countLeft(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;
		int x_count = x1 - 1;
		while (board.inBoard(x_count, y1)) {
			ChewyObject current = board.cellAt(x_count, y1).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				x_count--;
				sum++;
			} else {
				break;
			}
		}
		return sum;
	}

	
	 * private int countVert(Board board, int x1, int y1, Matchable candidate) {
	 * return 1 + countTop(board, x1, y1, candidate) + countBottom(board, x1,
	 * y1, candidate); }
	 

	private int countBottom(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;

		int y_count = y1 + 1;
		while (board.inBoard(x1, y_count)) {
			ChewyObject current = board.cellAt(x1, y_count).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				y_count++;
				sum++;
			} else {
				break;
			}
		}
		return sum;

	}

	private int countTop(Board board, int x1, int y1, Matchable candidate) {
		int sum = 0;
		int y_count = y1 - 1;
		while (board.inBoard(x1, y_count)) {
			ChewyObject current = board.cellAt(x1, y_count).getCurrentObject();
			if ((current instanceof Matchable)
					&& (candidate.isMatched((Matchable) current))) {
				y_count--;
				sum++;
			} else {
				break;
			}
		}
		return sum;
	}

	public boolean shouldErased(MatchingScaleInformer msi) {
		if (msi.horizontalMatchTotalScale() >= MINIMUM_MATCH_REQUIRED
				|| msi.verticalMatchTotalScale() >= MINIMUM_MATCH_REQUIRED)
			return true;
		return false;
	}

	public boolean gameEndedByMovements(int movementsLeft) {
		if (movementsLeft > 0) {
			return false;
		}
		return true;
	}

	
	 * Left and up scale should only stand for checking whether the cell was
	 * matched previously check always occurs from left to right and up to down
	 
	public int getStandardScore(int eraseCount, MatchingScaleInformer msi) {
		if (msi.getLeftScale() > 0) {
			return 0;
		}
		if (msi.getUpScale() > 0) {
			return 0;
		}
		
		int score = 0;
		int right = msi.getRightScale();
		
		if (right >= MINIMUM_MATCH_REQUIRED-1) {
			switch (right) {
			case 2:
				score += eraseCount * 60;
				break;
			case 3:
				score += eraseCount * 240;
				break;
			case 4:
				score += eraseCount * 600;
				break;
			default:
				break;
			}
		}
		

		int down = msi.getDownScale();
		if (down >= MINIMUM_MATCH_REQUIRED-1) {
			switch (down) {
			case 2:
				score += eraseCount * 60;
				break;
			case 3:
				score += eraseCount * 240;
				break;
			case 4:
				score += eraseCount * 600;
				break;
			default:
				break;
			}
			
		}


		return score;
	}


	public int getSpecialityCode(MatchingScaleInformer msi) {

		int hms = msi.horizontalMatchTotalScale();
		int vms = msi.verticalMatchTotalScale();

		if (hms == 5 || vms == 5) {
			return COLOR_BOMB;
		}

		if (hms == 3 && vms == 3) {
			return WRAPPED;
		}

		if (hms == 4) {
			return HSTRIPED;
		}
		
		if (vms == 4) {
			return VSTRIPED;
		}

		return REGULAR;

	}

	public boolean isSpecialCase(int specialityCode) {
		// TODO Auto-generated method stub
		return specialityCode == VSTRIPED || specialityCode == HSTRIPED
				|| specialityCode == WRAPPED || specialityCode == COLOR_BOMB;
	}

	*//**
	 * @requires specialityCode should reference to a special case
	 * @param specialityCode
	 *//*
	public Lokum getRelevantSpecialObject(String initialType, int specialityCode) {
		Lokum sl = null;

		switch (specialityCode) {
		case VSTRIPED:
			sl = new Lokum(initialType, "Horizontal Stripe");
			break;
		case HSTRIPED:
			sl = new Lokum(initialType, "Vertical Striped");
			break;
		case WRAPPED:
			sl = new Lokum(initialType, "Wrapped");
			break;

		case COLOR_BOMB:
			sl = new Lokum(initialType, "Color Bomb");
			break;
		}

		return sl;

	}

	public int getRelevantCreationScore(int specialityCode) {
		switch (specialityCode) {
		case VSTRIPED:
			return 120;
		case HSTRIPED:
			return 120;
		case WRAPPED:
			return 200;
		case COLOR_BOMB:
			return 200;
		default:
			return 0;
		}
	}

	public int getSpecialMoveScore(int x1, int y1, int x2, int y2, Board board,
			Lokum l1, Lokum l2) {
		
		if (isStriped(l1) && isStriped(l2)) {
			return 2 * getRelevantCreationScore(VSTRIPED);
		}
		
		if (isStriped(l1) && isWrapped(l2) || isWrapped(l1) && isStriped(l2)) {
			return 3 * getRelevantCreationScore(VSTRIPED);
		}
		
		if (isStriped(l1) && isCB(l2) || isCB(l1) && isStriped(l2)) {
			int n = isStriped(l1) ? countAllLokumsInRow(board, y1) 
					: countAllLokumsInCol(board, y2);
			return n * getRelevantCreationScore(HSTRIPED);
		}
		
		if (isWrapped(l1) && isWrapped(l2)) {
			return 3600;
		}
		
		if (isWrapped(l1) && isCB(l2) || isCB(l1) && isWrapped(l2)) {
			return 2 * getRelevantCreationScore(COLOR_BOMB);
		}
		
		if (isCB(l1) && isCB(l2)) {
			int n = countAllLokumsInBoard(board);
			return n * n * 100;
		}
		
		return 0;
	}
	
	private boolean isStriped(Lokum l1) {
		return l1.getSpecialType().equalsIgnoreCase("striped");
	}

	private boolean isCB(Lokum l1) {
		// TODO Auto-generated method stub
		return l1.getSpecialType().equalsIgnoreCase("color bomb");
	}

	private boolean isWrapped(Lokum l1) {
		// TODO Auto-generated method stub
		return l1.getSpecialType().equalsIgnoreCase("wrapped");
	}

	//get the using score
	public int getUsingScore(int x1, int y1, Board board, Lokum swappedObject) {
		if (swappedObject.isSpecial()) {
			String spec = swappedObject.getSpecialType();
			if (spec.equals("Vertical Striped")) {
				int n = countAllLokumsInCol(board, x1);
				return n * 60;
			}
			if (spec.equals("Horizontal Striped")) {
				int n = countAllLokumsInRow(board, y1);
				return n * 60;
			}

			if (spec.equals("Wrapped")) {
				return 1080;
			}
			
			if (spec.equals("Color Bomb")) {
				int n = countAllLokumsMatchedInTheBoard(board, swappedObject);
				return n * n * 60;
			}
		}
		
		//if not special
		return 0;
	}

	private int countAllLokumsInBoard(Board board) {
		int n = 0;
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject() instanceof Lokum) {
						n++;
				}
			}
		}
		return n;
	}
	
	private int countAllLokumsMatchedInTheBoard(Board board, Lokum l) {
		int n = 0;
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				if (board.cellAt(i, j).getCurrentObject() instanceof Lokum) {
					Lokum l2 = (Lokum)board.cellAt(i, j).getCurrentObject();
					if (l.isMatched(l2)) {
						n++;
					}
				}
			}
		}
		return n;
	}
	
	private int countAllLokumsInRow(Board board, int r) {
		int n = 0;
		for (int x = 0; x < board.getWidth(); x++) {
			if (board.cellAt(x, r).getCurrentObject() instanceof Lokum) {
				n++;
			}
		}
		return n;
	}
	
	
	private int countAllLokumsInCol(Board board, int c) {
		int n = 0;
		for (int y = 0; y < board.getHeight(); y++) {
			if (board.cellAt(c, y).getCurrentObject() instanceof Lokum) {
				n++;
			}
		}
		return n;
	}*/
	

}