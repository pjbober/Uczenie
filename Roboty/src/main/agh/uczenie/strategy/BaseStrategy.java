package agh.uczenie.strategy;

import robocode.*;
import robocode.robotinterfaces.IBasicEvents;

public class BaseStrategy implements IBasicEvents {
	protected final AdvancedRobot robot;
	private static final int PROBE_DELAY = 100;
	private int probeCount = 0;

	public BaseStrategy(AdvancedRobot robot) {
		assert robot != null;
		this.robot = robot;
	}

	public final void _loopAction() {
//		if (probeCount++ >= PROBE_DELAY) {
//			System.out.println("Radar");
//			robot.turnRadarLeft(360);
//			probeCount = 0;
//		}
		loopAction();
		robot.execute();
	}

	public void loopAction() {}

	public void setup() {}

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
