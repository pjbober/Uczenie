package agh.uczenie.robot;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.strategies.DiscoveryStrategy;
import agh.uczenie.strategy.strategies.NullStrategy;
import agh.uczenie.strategy_select.BaseStrategySelect;
import agh.uczenie.strategy_select.DefaultStrategySelect;
import robocode.*;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractStrategicRobot extends AdvancedRobot {
	private static final int STRATEGY_CHANGE_INTERVAL = 10;
	private final StrategyManager strategyManager;
	private int strategyChangeCounter = 0;

	private BaseStrategySelect strategySelect;
	private BaseStrategy strategy;
	private Class<? extends BaseStrategySelect> strategySelectClass;

	public AbstractStrategicRobot(Class<? extends BaseStrategySelect> strategySelectClass) {
		System.out.println("Constructing robot: " + this.getClass().getSimpleName());
		this.strategySelectClass = strategySelectClass;
		try {
			strategyManager = new StrategyManager(this);

			// real StrategySelect will be create on run() invocation
			strategySelect = new DefaultStrategySelect(strategyManager);

			strategy = new NullStrategy(this);
		} catch (Exception e) {
			System.out.println("AbstractStrategicRobot construction failed: " + e.toString());
			throw new RuntimeException(e);
		}
	}

	private void runInit() {
		try {
			strategySelect = strategySelectClass.getConstructor(StrategyManager.class).newInstance(strategyManager);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			System.out.println("AbstractStrategicRobot run init failed: " + e.toString());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		runInit();

		while (true) {
			if (strategyChangeCounter++ > STRATEGY_CHANGE_INTERVAL) {
				strategy = new DiscoveryStrategy(this);
				strategy.setup(); // should be blocking!

				State state = ((DiscoveryStrategy) strategy).getState();
				System.out.println("State: " + state.toString());
				strategy = strategySelect.basedOnState(state);
				strategyChangeCounter = 0;
			}
			strategy._loopAction();
		}
	}

	// -- Asynchronous events delegation --

	@Override
	public void onStatus(StatusEvent statusEvent) {
		strategy.onStatus(statusEvent);
		strategySelect.onStatus(statusEvent);
	}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {
		strategy.onBulletHit(bulletHitEvent);
		strategySelect.onBulletHit(bulletHitEvent);
	}

	@Override
	public void onBulletHitBullet(BulletHitBulletEvent bulletHitBulletEvent) {
		strategy.onBulletHitBullet(bulletHitBulletEvent);
		strategySelect.onBulletHitBullet(bulletHitBulletEvent);
	}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
		strategy.onBulletMissed(bulletMissedEvent);
		strategySelect.onBulletMissed(bulletMissedEvent);
	}

	@Override
	public void onDeath(DeathEvent deathEvent) {
		strategy.onDeath(deathEvent);
		strategySelect.onDeath(deathEvent);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
		strategy.onHitByBullet(hitByBulletEvent);
		strategySelect.onHitByBullet(hitByBulletEvent);
	}

	@Override
	public void onHitRobot(HitRobotEvent hitRobotEvent) {
		strategy.onHitRobot(hitRobotEvent);
		strategySelect.onHitRobot(hitRobotEvent);
	}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {
		strategy.onHitWall(hitWallEvent);
		strategySelect.onHitWall(hitWallEvent);
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		strategy.onScannedRobot(event);
		strategySelect.onScannedRobot(event);
	}

	@Override
	public void onRobotDeath(RobotDeathEvent robotDeathEvent) {
		strategy.onRobotDeath(robotDeathEvent);
		strategySelect.onRobotDeath(robotDeathEvent);
	}

	@Override
	public void onWin(WinEvent winEvent) {
		strategy.onWin(winEvent);
		strategySelect.onWin(winEvent);
	}
}
