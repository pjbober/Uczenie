package agh.uczenie.robot;

import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.TraceAnyoneStrategy;
import robocode.*;

public class StrategicRobot extends AdvancedRobot {

	private BaseStrategy strategy;

	public StrategicRobot() {
		setStrategy(new TraceAnyoneStrategy(this));
	}

	@Override
	public void run() {
		while (true) {
			getStrategy().loopAction();
		}
	}

	@Override
	public void onStatus(StatusEvent statusEvent) {
		getStrategy().onStatus(statusEvent);
	}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {
		getStrategy().onBulletHit(bulletHitEvent);
	}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
		getStrategy().onBulletHitBullet(bulletHitBulletEvent);
	}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
		getStrategy().onBulletMissed(bulletMissedEvent);
	}

	@Override
	public void onDeath(DeathEvent deathEvent) {
		getStrategy().onDeath(deathEvent);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		getStrategy().onHitByBullet(hitByBulletEvent);
	}

	@Override
	public void onHitRobot(HitRobotEvent hitRobotEvent) {
		getStrategy().onHitRobot(hitRobotEvent);
	}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {
		getStrategy().onHitWall(hitWallEvent);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		getStrategy().onScannedRobot(event);
	}

	@Override
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
		getStrategy().onRobotDeath(robotDeathEvent);
	}

	@Override
	public void onWin(WinEvent winEvent) {
		getStrategy().onWin(winEvent);
	}

	public BaseStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(BaseStrategy strategy) {
		this.strategy = strategy;
	}
}
