package agh.uczenie.strategy;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Created by kliput on 14.04.14.
 */
public class TraceStrategy extends AbstractStrategy {
	public TraceStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void run() {

	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		robot.setTurnGunRight(robot.getHeading() - robot.getGunHeading() + event.getBearing());
		robot.fire(1);
	}

}