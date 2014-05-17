package agh.uczenie.strategy;

import agh.uczenie.robot.AbstractStrategicRobot;
import agh.uczenie.strategy.strategies.atomic.BatteringRamStrategy;
import robocode.AdvancedRobot;

public class StrategyManager {
	private static final Class<? extends BaseStrategy> DEFAULT_STRATEGY_CLASS = BatteringRamStrategy.class;

	private final AbstractStrategicRobot robot;

	private Type currentType;
	private BaseStrategy currentStrategy;

	public BaseStrategy getCurrentStrategy() {
		return currentStrategy;
	}

	public static enum Type {
		DEFAULT,
		BATTERING_RAM;
//		HEAD_ON_TARGETING,
//		TRACE_ANYONE,
//		RUN_FROM_BULLETS;

		public static final int length;
		static {
			length = Type.values().length;
		}
	}

	public StrategyManager(AbstractStrategicRobot robot) {
		this.robot = robot;
		currentType = Type.DEFAULT;
		currentStrategy = create(currentType);
	}

	public BaseStrategy get(Type type) {
		if (type != currentType) {
			currentStrategy = create(type);
			currentType = type;
			System.out.println("Strategy changed to: " + currentType);
		}
		return currentStrategy;
	}

	private BaseStrategy create(Type type) {
		switch (type) {
//			case HEAD_ON_TARGETING:
//				return new HeadOnTargetingStrategy(robot);
//			case TRACE_ANYONE:
//				return new TraceAnyoneStrategy(robot);
//			case RUN_FROM_BULLETS:
//				return new RunFromBulletsStrategy(robot);
			case BATTERING_RAM:
				return new BatteringRamStrategy(robot);
			case DEFAULT:
			default:
				try {
					return DEFAULT_STRATEGY_CLASS.getConstructor(AdvancedRobot.class).newInstance(robot);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
		}
	}
}