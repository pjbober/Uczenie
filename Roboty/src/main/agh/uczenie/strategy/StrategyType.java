package agh.uczenie.strategy;

import java.util.Arrays;

public enum StrategyType {
	DEFAULT,
	BATTERING_RAM,
	AVOIDING,
	ANTI_GRAVITY;

	private static final StrategyType[] _uniqueValues;
	public static final int length;
	static {
		length = StrategyType.values().length;
		_uniqueValues = Arrays.copyOfRange(StrategyType.values(), 1, length);
	}


	public static StrategyType[] uniqueValues() {
		return _uniqueValues;
	}
}
