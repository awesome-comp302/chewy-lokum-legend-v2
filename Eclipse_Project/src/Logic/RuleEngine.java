package Logic;
public class RuleEngine {

	private static RuleEngine instance;

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
	}
	

}