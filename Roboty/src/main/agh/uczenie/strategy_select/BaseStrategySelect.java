package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;

public abstract class BaseStrategySelect {
	protected StrategyManager factory;

	public BaseStrategySelect(StrategyManager strategyManager) {
		factory = strategyManager;
	}

	public abstract BaseStrategy basedOnState(State state);
}
