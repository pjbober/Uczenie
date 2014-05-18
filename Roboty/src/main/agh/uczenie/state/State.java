package agh.uczenie.state;

import agh.uczenie.strategy_select.PiqleStrategySelect;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IEnvironment;
import piqle.environment.IState;

import java.util.HashMap;
import java.util.Map;

public class State implements IState {
	public Energy selfEnergy = Energy.HIGH;
	public Map<Distance, Integer> enemiesCount = new HashMap<>();
	public Map<Distance, Energy> enemiesEnergyAvg = new HashMap<>();

	public State() {
		for (Distance distance : Distance.values()) {
			enemiesCount.put(distance, 0);
			enemiesEnergyAvg.put(distance, Energy.ZERO);
		}
	}

	public double computeScoreTo(State currentState) {
		return currentState.selfEnergy.getScore() - selfEnergy.getScore();
	}

	@Override
	public int hashCode() {
		// self energy is assumed to be 0-999
		return selfEnergy.getScore() + 1000*enemiesCountHash() + 10000*enemiesEnergyHash();
	}

	/// Values: [0,1,2,3]+[10,11,12,13]+[20,21,22,23] -> 0..39
	public int enemiesEnergyHash() {
		int enemiesEnergyHash = 0;
		for (Map.Entry<Distance, Energy> entry : enemiesEnergyAvg.entrySet()) {
			enemiesEnergyHash += 10*entry.getKey().getNumber() + entry.getValue().getScore();
		}
		return enemiesEnergyHash;
	}

	/// Values: [0..9]+[10..19]+[20..29] -> 0..57
	/// Max 10 enemy robots in game
	public int enemiesCountHash() {
		int enemiesCountHash = 0;
		for (Map.Entry<Distance, Integer> entry : enemiesCount.entrySet()) {
			enemiesCountHash += 10*entry.getKey().getNumber() + entry.getValue();
		}

		return enemiesCountHash;
	}

	@Override
	public String toString() {
		Map<Distance, Integer> ec = enemiesCount;
		Map<Distance, Energy> ee = enemiesEnergyAvg;
		return String.format("[Energy: %s, Enemies: {%d: %s| %d: %s| %d: %s}]", selfEnergy,
				ec.get(Distance.SHORT), ee.get(Distance.SHORT),
				ec.get(Distance.MEDIUM), ee.get(Distance.MEDIUM),
				ec.get(Distance.LONG), ee.get(Distance.LONG));
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
	public boolean isFinal() {
		return false;
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
