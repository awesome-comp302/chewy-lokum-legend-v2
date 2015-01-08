package Logic;

public class SpecialEraseRules implements EraseRules {

	private static SpecialEraseRules instance;
	private boolean erasePool[][];
	
	@Override
	public boolean shouldErased(GamePlay gp, MatchingScaleInformer match,
			Position position) {
		
		return shouldErased(PERSISTENT, gp, match, position);
	}

	@Override
	public boolean shouldErased(int option, GamePlay gp,
			MatchingScaleInformer match, Position position) {
		int i = position.getY();
		int j = position.getX();
		if (option == PERSISTENT) {
			if (erasePool == null) {
				createErasePool(gp);
			}
		} else { //recreate the erasePool
			createErasePool(gp);
		}
		return erasePool[i][j];
		
		
	}

	private void createErasePool(GamePlay gp) {
		// TODO Auto-generated method stub
		Board board = gp.getLevel().getBoard();
		erasePool = new boolean[board.getHeight()][board.getWidth()];
		/*
		 * Fill here to add erasing behaviour
		 */
		
	}

	@Override
	public void taskCompleted() {
		erasePool = null;
		
	}

	public static SpecialEraseRules getInstance() {
		// TODO Auto-generated method stub
		if (instance == null) {
			instance = new SpecialEraseRules();
		}
		return instance;
	}
	
	private SpecialEraseRules(){}

}
