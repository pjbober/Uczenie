package agh.uczenie.strategy;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class TraceAnyoneStrategy extends BaseStrategy {
	public TraceAnyoneStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		robot.setTurnGunRight(robot.getHeading() - robot.getGunHeading() + event.getBearing());
		robot.fire(1);
	}

}
