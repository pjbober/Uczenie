package agh.uczenie.robot;

import agh.uczenie.strategy_select.DefaultStrategySelect;

public class DefaultStrategicRobot extends AbstractStrategicRobot {

	public DefaultStrategicRobot() {
		super(DefaultStrategySelect.class);
	}
}
