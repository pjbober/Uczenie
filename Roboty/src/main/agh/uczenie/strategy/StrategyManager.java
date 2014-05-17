package agh.uczenie.strategy;

import agh.uczenie.robot.AbstractStrategicRobot;
import agh.uczenie.strategy.strategies.atomic.AvoidingStrategy;
import agh.uczenie.strategy.strategies.atomic.BatteringRamStrategy;

public class StrategyManager {
	private static final Type DEFAULT_TYPE = Type.AVOIDING;

	private final AbstractStrategicRobot robot;

	private Type currentType;
	private BaseStrategy currentStrategy;

	public StrategyManager(AbstractStrategicRobot robot) {
		this.robot = robot;
		currentType = Type.DEFAULT;
		currentStrategy = create(currentType);
	}

	public BaseStrategy getCurrentStrategy() {
		return currentStrategy;
	}

	public static enum Type {
		DEFAULT,
		BATTERING_RAM,
		AVOIDING;

		public static final int length;
		static {
			length = Type.values().length;
		}
	}

	public BaseStrategy get(Type type) {
		if (type != currentType) {
			currentStrategy = create(type);
			currentType = type;

			System.out.println("Strategy changed to: " + currentStrategy.getClass().getSimpleName());
		}
		return currentStrategy;
	}

	private BaseStrategy create(Type type) {
		if (type == Type.DEFAULT) {
			type = DEFAULT_TYPE;
		}

		BaseStrategy strategy = null;

		switch (type) {
			case BATTERING_RAM:
				strategy = new BatteringRamStrategy(robot);
				break;
			case AVOIDING:
				strategy = new AvoidingStrategy(robot);
				break;
			default:
			case DEFAULT:
				throw new RuntimeException();
		}

		return strategy;
	}
}
