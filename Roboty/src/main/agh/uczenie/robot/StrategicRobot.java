package agh.uczenie.robot;

import agh.uczenie.strategy.AbstractStrategy;
import agh.uczenie.strategy.TraceStrategy;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class StrategicRobot extends AdvancedRobot {

	private AbstractStrategy strategy;

	public StrategicRobot() {
		strategy = new TraceStrategy(this);
	}

	@Override
	public void run() {
		while (true) {
			strategy.run();
		}
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		strategy.onScannedRobot(event);
	}

}
