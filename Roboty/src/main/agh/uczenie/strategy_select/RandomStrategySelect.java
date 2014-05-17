package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;

import java.util.Random;

public class RandomStrategySelect extends BaseStrategySelect {
	private final int MAX_INVOCATIONS = 50;
	private int invocationCount = 0;
	private StrategyManager.Type currentType = StrategyManager.Type.DEFAULT;
	private Random random = new Random();

	public RandomStrategySelect(StrategyManager factory) {
		super(factory);
	}

	@Override
	public BaseStrategy basedOnState(State state) {
		if (invocationCount++ > MAX_INVOCATIONS) {
			currentType = StrategyManager.Type.values()[random.nextInt(StrategyManager.Type.length)];
			invocationCount = 0;
		}
		return factory.get(currentType);
	}
}
