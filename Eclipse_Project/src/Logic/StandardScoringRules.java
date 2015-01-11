package Logic;

public class StandardScoringRules implements ScoringRules {

	private static StandardScoringRules instance;

	@Override
	public int getMatchingScore(MatchingScaleInformer msi) {
		int score = 0;
		
		if (msi.getRightScale() >= 2) {
			score += 60;
		}
		else if (msi.getDownScale() >= 2) {
			score += 60;
		}
		return score;
	}

	@Override
	public int getCreationScore(ChewyObject co) {
		// TODO Auto-generated method stub
		if (co instanceof Lokum) {
			Lokum l = (Lokum) co;
			String type = l.getSpecialType();
			if (type.endsWith("Striped")) {
				return 120;
			}
			if (type.equals("Wrapped")) {
				return 200;
			}
			if (type.equals("Color Bomb")) {
				return 200;
			}
		}
		return 0;
	}

	@Override
	public int getSwapScore(Move m, Board b) {
		//do something with move
		return 0;
	}

	public static StandardScoringRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) 
			instance = new StandardScoringRules();
		return instance;
	}
	
	private StandardScoringRules() {}

}
