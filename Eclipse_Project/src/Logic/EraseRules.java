package Logic;

public interface EraseRules {
	public final int MINIMUM_MATCH = 3;
	//Means that board was not changed
	public final int PERSISTENT = 4;
	
	//Means that board was changed
	public final int TEMPORARY = 5;
	
	public boolean shouldErased(GamePlay gp, MatchingScaleInformer match, Position position);
	public boolean shouldErased(int option, GamePlay gp, MatchingScaleInformer match, Position position);
	public void taskCompleted();
	
}
