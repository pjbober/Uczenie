package agh.uczenie.strategy;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

/**
 * Based on: http://robowiki.net/wiki/Head-On_Targeting
 */
public class HeadOnTargetingStrategy extends BaseStrategy {

	private double lastTurnGunRadiansDiff = 0;
	private int aimed = 0;

	public HeadOnTargetingStrategy(AdvancedRobot robot) {
		super(robot);
		System.out.println("Head on targeting strategy constructed");
	}

	@Override
	public void loopAction() {}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		double absoluteBearing = robot.getHeadingRadians() + event.getBearingRadians();
		lastTurnGunRadiansDiff = robocode.util.Utils.normalRelativeAngle(absoluteBearing-robot.getGunHeadingRadians());
		robot.setTurnGunRightRadians(lastTurnGunRadiansDiff);

		System.out.println(String.format("aimed: %d, dist/50: %d", aimed, (int)event.getDistance()/50));

		if (lastTurnGunRadiansDiff < 0.01) {
			if (++aimed > (int)event.getDistance() / 50) {
				robot.fire(1);
			}
		} else {
			aimed = 0;
		}
	}
}
