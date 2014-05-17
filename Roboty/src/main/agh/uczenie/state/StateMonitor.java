package agh.uczenie.state;

import agh.uczenie.robot.AbstractStrategicRobot;
import robocode.*;
import robocode.robotinterfaces.IBasicEvents;

public class StateMonitor implements IBasicEvents {
	private final AbstractStrategicRobot robot;

	public StateMonitor(AbstractStrategicRobot abstractStrategicRobot) {
		this.robot = abstractStrategicRobot;
	}

	public State getState() {
		State state = new State();
		state.others = robot.getOthers();
		state.energy = robot.getEnergy();

		return state;
	}

	// -- Other --

	public void getRobotCount() {

	}

	// -- Asynchronous events notification --

	@Override
	public void onStatus(StatusEvent statusEvent) {

	}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {

	}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {

	}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {

	}

	@Override
	public void onDeath(DeathEvent deathEvent) {

	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {

	}

	@Override
	public void onHitRobot(HitRobotEvent hitRobotEvent) {

	}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {

	}

	@Override
	public void onScannedRobot(ScannedRobotEvent scannedRobotEvent) {

	}

	@Override
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {

	}

	@Override
	public void onWin(WinEvent winEvent) {

	}
}
