package agh.uczenie.strategy.strategies.example;

import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;

import java.util.Random;

public class RunFromBulletsStrategy extends BaseStrategy {
	Random random = new Random();

	public RunFromBulletsStrategy(AdvancedRobot robot) {
		super(robot);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		super.onHitByBullet(hitByBulletEvent);
		robot.ahead((random.nextBoolean() ? 1 : -1) * 40);
	}
}
