package Logic;

/**
 * @author Berk
 *
 */
public class LevelSelector {
	private static GamePlay gp;
	
	

	public static GamePlay createGamePlay(int levelID){
		
		gp = new GamePlay(LevelData.getInstance().getLevelById(levelID));

		return gp;
	}


}
