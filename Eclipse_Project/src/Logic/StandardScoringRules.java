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
		String sa1[] = m.getSpecialType1().toLowerCase().split(" ");
		String s1 = sa1[sa1.length - 1];
		String sa2[] = m.getSpecialType2().toLowerCase().split(" ");
		String s2 = sa2[sa2.length - 1];
		
		if(s1.equals( "striped") && s2.equals( "striped")){
			//2x striped score
			return 2 * 120;
		}
		if((s1.equals( "striped") && s1.equals( "wrapped")) ||
				(s1.equals( "wrapped") && s2.equals( "striped"))){
			//3x striped score
			return 3 * 120;
		}
		if(s1.equals( "wrapped") && s2.equals( "wrapped")){
			//3600 (number is given in document)
			return 3600;
		}
		if((s1.equals( "bomb") && s2.equals( "striped")) || (s2.equals( "bomb") && s1.equals( "striped"))){
			// # lokums matched by color bomb * striped score
			String cb_type = (s1.equals( "bomb")) ? m.getType2() : m.getType1();
			int count = 0;
			for(int i = 0; i < b.getHeight(); i++){
				for(int j = 0; j < b.getWidth(); j++){
					if(b.cellAt(i, j).getCurrentObject().getType().equals( cb_type)) count++;
				}
			}
		}
		if((s1.equals( "bomb") && s2.equals( "wrapped")) || (s2.equals( "bomb") && s1.equals( "wrapped"))){
			// ??
		}
		if(s1.equals( "bomb") && s2.equals( "bomb")){
			// # cells on board ^ 2 x 100
			return (int)(Math.pow(b.getHeight() * b.getWidth(),2) * 100);
		}
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
