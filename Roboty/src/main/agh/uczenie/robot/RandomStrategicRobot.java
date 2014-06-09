package agh.uczenie.robot;

import agh.uczenie.strategy_select.RandomStrategySelect;

public class RandomStrategicRobot extends AbstractStrategicRobot {

	public RandomStrategicRobot() {
		super(RandomStrategySelect.class);
	}
}
