package agh.uczenie.robot;

import agh.uczenie.strategy_select.ConditionalStrategySelect;

public class ConditionalStrategicRobot extends AbstractStrategicRobot {
	public ConditionalStrategicRobot() {
		super(ConditionalStrategySelect.class);
	}
}
