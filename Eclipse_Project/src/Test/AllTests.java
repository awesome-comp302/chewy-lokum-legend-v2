package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoardTest.class, BoardUpdaterTest.class, GamePlayTest.class,
		LevelTest.class, ReadXMLFileTest.class, //RuleEngineTest.class,
		TestsV1.class, WriteXMLFileTest.class })
public class AllTests {

}
