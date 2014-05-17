package agh.uczenie.strategy;

import agh.uczenie.robot.AbstractStrategicRobot;
import agh.uczenie.strategy.strategies.atomic.AvoidingStrategy;
import agh.uczenie.strategy.strategies.atomic.BatteringRamStrategy;

public class StrategyManager {
	private static final StrategyType DEFAULT_TYPE = StrategyType.AVOIDING;

	private final AbstractStrategicRobot robot;

	private StrategyType currentType;
	private BaseStrategy currentStrategy;

	public StrategyManager(AbstractStrategicRobot robot) {
		this.robot = robot;
		currentType = StrategyType.DEFAULT;
		currentStrategy = create(currentType);
	}

	public BaseStrategy getCurrentStrategy() {
		return currentStrategy;
	}

	public BaseStrategy get(StrategyType type) {
		if (type != currentType) {
			currentStrategy = create(type);
			currentType = type;

			System.out.println("Strategy changed to: " + currentStrategy.getClass().getSimpleName());
		}
		return currentStrategy;
	}

	private BaseStrategy create(StrategyType type) {
		if (type == StrategyType.DEFAULT) {
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

	public AbstractStrategicRobot getRobot() {
		return robot;
	}
}
