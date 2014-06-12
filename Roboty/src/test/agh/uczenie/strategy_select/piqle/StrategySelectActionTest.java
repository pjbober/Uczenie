package agh.uczenie.strategy_select.piqle;

import agh.uczenie.strategy.StrategyType;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StrategySelectActionTest {

	@Test
	public void testEquals() throws Exception {
		StrategySelectAction a11 = new StrategySelectAction(StrategyType.ANTI_GRAVITY);
		StrategySelectAction a12 = new StrategySelectAction(StrategyType.ANTI_GRAVITY);
		StrategySelectAction a2 = new StrategySelectAction(StrategyType.AVOIDING);

		assertEquals(a11, a12);
		assertNotEquals(a11, a2);
	}
}