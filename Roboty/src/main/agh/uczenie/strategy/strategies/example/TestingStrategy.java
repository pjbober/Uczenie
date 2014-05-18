package agh.uczenie.strategy.strategies.example;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;

public class TestingStrategy extends BaseStrategy {
	private int probe = 0;
	private boolean calibrated = false;

	public TestingStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void setup() {
		robot.setTurnGunRight(30);
		robot.setTurnRadarRight(70);
	}

	@Override
	public void loopAction() {
		super.loopAction();
		if (!calibrated && probe++ > 50) {
			calibrate();
			calibrated = true;
		}
	}
}
