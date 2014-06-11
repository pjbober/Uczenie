package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.StrategyType;

public class DefaultStrategySelect extends BaseStrategySelect {
	public DefaultStrategySelect(StrategyManager strategyManager) {
		super(strategyManager);
	}

	@Override
	public BaseStrategy basedOnState(State state) {
		return strategyManager.get(StrategyType.HEAD_ON_TARGETING);
	}
}
