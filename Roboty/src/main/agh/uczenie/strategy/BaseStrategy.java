package agh.uczenie.strategy;

import robocode.*;
import robocode.robotinterfaces.IBasicEvents;
import robocode.robotinterfaces.IBasicEvents2;
import robocode.robotinterfaces.IBasicEvents3;

import java.awt.*;

public class BaseStrategy implements IBasicEvents, IBasicEvents2, IBasicEvents3 {
	protected final AdvancedRobot robot;
	private static final int PROBE_DELAY = 100;
	private int probeCount = 0;
	private boolean init = true;

	public BaseStrategy(AdvancedRobot robot) {
		assert robot != null;
		this.robot = robot;
		System.out.println("Strategy constructed: " + this.getClass().getSimpleName());
	}

	public final void _loopAction() {
		prepare();
		loopAction();
		robot.execute();
	}

	private void prepare() {
		if (init) {
			setupColors();
			setup();
			init = false;
		}
	}

	private void setupColors() {
		robot.setBodyColor(bodyColor());
		robot.setGunColor(gunColor());
		robot.setRadarColor(radarColor());
	}

	// Default colors - should be override with strategy-specific colors

	public java.awt.Color bodyColor() {
		return Color.red;
	}

	public java.awt.Color gunColor() {
		return Color.white;
	}

	public Color radarColor() {
		return Color.red;
	}

	// Methods to implement strategy actions

	public void setup() {}

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

	// Utils

	protected void calibrateRadarToGun() {
		robot.turnRadarRight(robot.getGunHeading()-robot.getRadarHeading());
	}

	protected void calibrateAll() {
		robot.turnGunRight(robot.getHeading()-robot.getGunHeading());
		calibrateRadarToGun();
	}

	@Override
	public void onRoundEnded(RoundEndedEvent roundEndedEvent) {}

	@Override
	public void onBattleEnded(BattleEndedEvent battleEndedEvent) {}
}
