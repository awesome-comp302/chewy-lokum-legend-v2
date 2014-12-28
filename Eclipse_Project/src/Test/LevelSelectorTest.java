package Test;


import Logic.GamePlay;
import Logic.Level;
import Logic.LevelData;
import Logic.LevelSelector;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Berk
 *
 */


public class LevelSelectorTest {

	private GamePlay gp;
	private Level level;

	@Before
	public void before(){
		System.out.println("Before testing...");
		level = LevelData.getInstance().getLevelById(1);
		gp = new GamePlay(level);

	}

	@After
	public void after(){
		System.out.println("After testing...");
		level = null;
		gp = null;
	}

	@Test
	public void createGamePlayTest(){
		assertEquals(gp, LevelSelector.createGamePlay(1));
	}

}



