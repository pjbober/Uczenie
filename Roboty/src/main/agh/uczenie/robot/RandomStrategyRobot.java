package agh.uczenie.robot;

import agh.uczenie.strategy_select.RandomStrategySelect;

public class RandomStrategyRobot extends AbstractStrategicRobot {

	public RandomStrategyRobot() {
		super(RandomStrategySelect.class);
	}
}
