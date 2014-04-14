package agh.uczenie.strategy;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public abstract class AbstractStrategy {
	protected final AdvancedRobot robot;

	public AbstractStrategy(AdvancedRobot robot) {
		assert robot != null;
		this.robot = robot;
	}

	public abstract void run();

	public abstract void onScannedRobot(ScannedRobotEvent event);
}
