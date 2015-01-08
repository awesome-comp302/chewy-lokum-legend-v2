package Logic;


public interface ScoringRules {
	public int getMatchingScore(MatchingScaleInformer msi);
	public int getCreationScore(ChewyObject co);
	int getSwapScore(Move m, Board b);
}
