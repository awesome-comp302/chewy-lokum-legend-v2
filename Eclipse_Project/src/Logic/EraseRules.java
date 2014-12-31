package Logic;

public interface EraseRules {
	public final int MINIMUM_MATCH = 3;
	public boolean shouldErased(MatchingScaleInformer msi, GamePlay gp);
	public boolean shouldErased(MatchingScaleInformer msi);
	public boolean shouldErased(int x, int y, GamePlay gp);
}
