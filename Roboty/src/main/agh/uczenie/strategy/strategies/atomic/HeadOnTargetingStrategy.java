package agh.uczenie.strategy.strategies.atomic;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;

import java.util.Random;

/**
 * Based on: http://robowiki.net/wiki/Head-On_Targeting
 */
public class HeadOnTargetingStrategy extends BaseStrategy {

	private double lastTurnGunRadiansDiff = 0;
	private int aimed = 0;
	Random random = new Random();

	public HeadOnTargetingStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void setup() {
		robot.setTurnGunRight(360);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		double absoluteBearing = robot.getHeadingRadians() + event.getBearingRadians();
		lastTurnGunRadiansDiff = robocode.util.Utils.normalRelativeAngle(absoluteBearing-robot.getGunHeadingRadians());
		robot.setTurnGunRightRadians(lastTurnGunRadiansDiff);

		double MAX_DIST = 1000;
		double dist = Math.min(MAX_DIST, event.getDistance());
		double multi = ((MAX_DIST-dist)/MAX_DIST);
		double firePower = multi*Rules.MAX_BULLET_POWER;
		firePower = Math.max(Rules.MIN_BULLET_POWER, firePower);

//		System.out.println(String.format("dist: %f, multi: %f, fire: %f", dist, multi, firePower));

		robot.setFire(firePower);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		super.onHitByBullet(hitByBulletEvent);
		robot.setAhead((random.nextBoolean() ? 1 : -1) * 40);
	}
}
