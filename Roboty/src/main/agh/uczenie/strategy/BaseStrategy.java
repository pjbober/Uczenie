package agh.uczenie.strategy;

import robocode.*;
import robocode.robotinterfaces.IBasicEvents;

public class BaseStrategy implements IBasicEvents {
	protected final AdvancedRobot robot;

	public BaseStrategy(AdvancedRobot robot) {
		assert robot != null;
		this.robot = robot;
	}

	public void run() {}

	public void loopAction() {}

	@Override
	public void onStatus(StatusEvent statusEvent) {}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {}

	@Override
	public void onDeath(DeathEvent deathEvent) {}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {}

	@Override
	public void onHitRobot(HitRobotEvent hitRobotEvent) {}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {}

	@Override
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {}

	@Override
	public void onWin(WinEvent winEvent) {}
}
