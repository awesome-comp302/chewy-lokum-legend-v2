/**
 * 
 */
package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Logic.GamePlay;
import Logic.LevelData;

/**
 * @author Berk
 *
 */
public class LevelDataTest {
	
	@Test
	public void constructorTest(){
		assertTrue(LevelData.getInstance().repOk());
	}

}
