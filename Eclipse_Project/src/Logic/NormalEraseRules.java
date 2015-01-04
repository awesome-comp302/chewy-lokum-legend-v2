package Logic;

public class NormalEraseRules implements EraseRules{

	private static NormalEraseRules instance;

	@Override
	public boolean shouldErased(GamePlay gp, MatchingScaleInformer match,
			Position position) {
		if (match.horizontalMatchTotalScale() >= MINIMUM_MATCH) {
			return true;
		}
		if (match.verticalMatchTotalScale() >= MINIMUM_MATCH) {
			return true;
		}
		
		return false;
		
	}

	@Override
	public boolean shouldErased(int option, GamePlay gp,
			MatchingScaleInformer match, Position position) {
		return shouldErased(gp, match, position);
	}

	@Override
	public void taskCompleted() {
		//do nothing
		
	}

	public static NormalEraseRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new NormalEraseRules();
		}
		return instance;
	}

	public NormalEraseRules() {}
	
	
}
