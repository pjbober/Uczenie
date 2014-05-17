package agh.uczenie.strategy_select;

import agh.uczenie.robot.AbstractStrategicRobot;
import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import robocode.*;
import robocode.robotinterfaces.IBasicEvents;

public abstract class BaseStrategySelect implements IBasicEvents {
	protected StrategyManager strategyManager;

	public BaseStrategySelect(StrategyManager strategyManager) {
		this.strategyManager = strategyManager;
	}

	public abstract BaseStrategy basedOnState(State state);

	public AbstractStrategicRobot getRobot() {
		return strategyManager.getRobot();
	}

	// Event delegates

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
