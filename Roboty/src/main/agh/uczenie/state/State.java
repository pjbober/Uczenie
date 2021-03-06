package agh.uczenie.state;

import agh.uczenie.strategy_select.PiqleStrategySelect;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IEnvironment;
import piqle.environment.IState;

import java.util.HashMap;
import java.util.Map;

public class State implements IState {
	public SpecialState specialState = SpecialState.NORMAL;
	public Energy selfEnergy = Energy.HIGH;
	public Map<Distance, Integer> enemiesCount = new HashMap<>();
	public Map<Distance, Energy> enemiesEnergyAvg = new HashMap<>();

	private void init() {
		for (Distance distance : Distance.values()) {
			enemiesCount.put(distance, 0);
			enemiesEnergyAvg.put(distance, Energy.ZERO);
		}
	}

	public State() {
		this.specialState = SpecialState.NORMAL;
		init();
	}

	public State(SpecialState specialState) {
		this.specialState = specialState;
		init();
	}

	/// Values: [0,1,2,3]+[10,11,12,13]+[20,21,22,23] -> 0..39
	public int enemiesEnergyHash() {
		int enemiesEnergyHash = 0;
		for (Map.Entry<Distance, Energy> entry : enemiesEnergyAvg.entrySet()) {
			enemiesEnergyHash += entry.getKey().getNumber()*entry.getValue().getScore();
		}
		return enemiesEnergyHash;
	}

	/// Values: [0..9]+[10..19]+[20..29] -> 0..57
	/// Max 10 enemy robots in game
	public int enemiesCountHash() {
		int enemiesCountHash = 0;
		for (Map.Entry<Distance, Integer> entry : enemiesCount.entrySet()) {
			enemiesCountHash += entry.getKey().getNumber()*entry.getValue();
		}

		return enemiesCountHash;
	}

	@Override
	public String toString() {
		switch (specialState) {
			default:
			case NORMAL:
				Map<Distance, Integer> ec = enemiesCount;
				Map<Distance, Energy> ee = enemiesEnergyAvg;
				return String.format("%d [Energy: %s, Enemies: {%d: %s| %d: %s| %d: %s}]", hashCode(), selfEnergy,
						ec.get(Distance.SHORT), ee.get(Distance.SHORT),
						ec.get(Distance.MEDIUM), ee.get(Distance.MEDIUM),
						ec.get(Distance.LONG), ee.get(Distance.LONG));
			case WIN:
				return "[WIN]";
			case LOSE:
				return "[LOSE]";
		}


	}

	// -- IState implementation --

	@Override
	public ActionList getActionList() {
		ActionList actionList = new ActionList(this);
		for (IAction action : PiqleStrategySelect.allActionsList) {
			actionList.add(action);
		}
		return actionList;
	}

	@Override
	public int hashCode() {
		switch (specialState) {
			default:
			case NORMAL:
				// self energy is assumed to be 0-999
				return selfEnergy.getScore() + 1000*enemiesCountHash() + 10000*enemiesEnergyHash();
			case WIN:
				return -1;
			case LOSE:
				return -2;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return obj.getClass().equals(State.class) && this.hashCode() == obj.hashCode();
	}

	@Override
	public boolean isFinal() {
		return specialState == SpecialState.WIN || specialState == SpecialState.LOSE;
	}

	@Override
	public void setEnvironment(IEnvironment c) {
		throw new RuntimeException();
	}

	@Override
	public IState modify(IAction a) {
		throw new RuntimeException("Should be used only in WinnerSelector and agents");
	}

	@Override
	public IEnvironment getEnvironment() {
		throw new RuntimeException();
	}

	@Override
	public double getReward(IState old, IAction a) {
		throw new RuntimeException("Should be used only in agents");
	}

	@Override
	public IState copy() {
		throw new RuntimeException("Should be used only in WinnerSelector and agents");
	}

	@Override
	public int nnCodingSize() {
		throw new RuntimeException("Neural networks not supported");
	}

	@Override
	public double[] nnCoding() {
		throw new RuntimeException("Neural networks not supported");
	}
}
