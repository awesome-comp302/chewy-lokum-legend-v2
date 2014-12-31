package Logic;


public interface ScoringRules {
	public int getMatchingScore(Object o);
	public int getCreationScore(ChewyObject co);
	int getSwapScore(Move m);
}
