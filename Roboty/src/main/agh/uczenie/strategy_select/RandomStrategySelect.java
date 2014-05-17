package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.StrategyType;

import java.util.Random;

public class RandomStrategySelect extends BaseStrategySelect {
	private int invocationCount = 0;
	private StrategyType currentType = StrategyType.DEFAULT;
	private Random random = new Random();

	public RandomStrategySelect(StrategyManager factory) {
		super(factory);
	}

	@Override
	public BaseStrategy basedOnState(State state) {
		return strategyManager.get(StrategyType.uniqueValues()[random.nextInt(StrategyType.length-1)]);
	}
}
