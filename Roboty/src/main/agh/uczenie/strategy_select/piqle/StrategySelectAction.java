package agh.uczenie.strategy_select.piqle;

import agh.uczenie.strategy.StrategyType;
import piqle.environment.IAction;

public class StrategySelectAction implements IAction {

	private StrategyType type;

	public StrategySelectAction(StrategyType type) {
		this.type = type;
	}

	@Override
	public Object copy() {
		return new StrategySelectAction(getStrategyType());
	}

	@Override
	public int nnCodingSize() {
		throw new RuntimeException();
	}

	@Override
	public double[] nnCoding() {
		throw new RuntimeException();
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(StrategySelectAction.class) && this.hashCode() == obj.hashCode();
	}

	public StrategyType getStrategyType() {
		return type;
	}

	@Override
	public String toString() {
		return type.toString();
	}
}
