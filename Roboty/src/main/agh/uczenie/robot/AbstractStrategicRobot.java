package agh.uczenie.robot;

import agh.uczenie.state.StateMonitor;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy_select.BaseStrategySelect;
import robocode.*;

public abstract class AbstractStrategicRobot extends AdvancedRobot {

	private BaseStrategySelect strategySelect;
	private BaseStrategy strategy;
	private final StateMonitor stateMonitor;

	public AbstractStrategicRobot(Class<? extends BaseStrategySelect> strategySelectClass) {
		System.out.println("Constructing ASR");
		try {
			StrategyManager strategyManager = new StrategyManager(this);
			strategySelect = strategySelectClass.getConstructor(StrategyManager.class).newInstance(strategyManager);
			strategy = strategyManager.getCurrentStrategy();
			stateMonitor = new StateMonitor(this);
		} catch (Exception e) {
			System.out.println("AbstractStrategicRobot construction failed: " + e.toString());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		strategy.setup();
		while (true) {
			strategy._loopAction();
			strategy = strategySelect.basedOnState(stateMonitor.getState());
		}
	}

	// -- Asynchronous events delegation --

	@Override
	public void onStatus(StatusEvent statusEvent) {
		strategy.onStatus(statusEvent);
		stateMonitor.onStatus(statusEvent);
	}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {
		strategy.onBulletHit(bulletHitEvent);
		stateMonitor.onBulletHit(bulletHitEvent);
	}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
		strategy.onBulletHitBullet(bulletHitBulletEvent);
		stateMonitor.onBulletHitBullet(bulletHitBulletEvent);
	}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
		strategy.onBulletMissed(bulletMissedEvent);
		stateMonitor.onBulletMissed(bulletMissedEvent);
	}

	@Override
	public void onDeath(DeathEvent deathEvent) {
		strategy.onDeath(deathEvent);
		stateMonitor.onDeath(deathEvent);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		strategy.onHitByBullet(hitByBulletEvent);
		stateMonitor.onHitByBullet(hitByBulletEvent);
	}

	@Override
	public void onHitRobot(HitRobotEvent hitRobotEvent) {
		strategy.onHitRobot(hitRobotEvent);
		stateMonitor.onHitRobot(hitRobotEvent);
	}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {
		strategy.onHitWall(hitWallEvent);
		stateMonitor.onHitWall(hitWallEvent);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		strategy.onScannedRobot(event);
		stateMonitor.onScannedRobot(event);
	}

	@Override
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
		strategy.onRobotDeath(robotDeathEvent);
		stateMonitor.onRobotDeath(robotDeathEvent);
	}

	@Override
	public void onWin(WinEvent winEvent) {
		strategy.onWin(winEvent);
		stateMonitor.onWin(winEvent);
	}
}
