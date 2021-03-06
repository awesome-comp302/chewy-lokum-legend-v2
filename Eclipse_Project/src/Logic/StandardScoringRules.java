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
			//6x striped score
			return 6 * 120;
		}
		if(s1.equals( "wrapped") && s2.equals( "wrapped")){
			//3600 (number is given in document)
			return 3600;
		}
		if((s1.equals( "bomb") && s2.equals( "striped")) || (s2.equals( "bomb") && s1.equals( "striped"))){
			// # lokums matched by color bomb * striped score
			int count = 0 ;
			if(m.getSpecialType1() == "Vertical Striped" || m.getSpecialType2() == "Vertical Striped") count = b.getHeight();
			else count = b.getWidth();
			
			return count * 120;
		}
		if((s1.equals( "bomb") && s2.equals( "wrapped")) || (s2.equals( "bomb") && s1.equals( "wrapped"))){
			// Another color bomb explodes
			return -1;
		}
		if(s1.equals( "bomb") && s2.equals( "bomb")){
			// # cells on board ^ 2 x 100
			return (int)(Math.pow(b.getHeight() * b.getWidth(),2) * 100);
		}
		return 0;
	}

	public static StandardScoringRules getInstance() {
		if (instance == null) 
			instance = new StandardScoringRules();
		return instance;
	}
	
	private StandardScoringRules() {}

}
