package agh.uczenie.state;

import agh.uczenie.strategy_select.PiqleStrategySelect;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IEnvironment;
import piqle.environment.IState;

public class State implements IState {
	public int others = 0;
	public double energy = 0;

	public double computeScoreTo(State currentState) {
		return currentState.energy - energy;
	}

	@Override
	public int hashCode() {
		// TODO: use other fields
		return others;
	}

	@Override
	public String toString() {
		return String.format("tanks: %d, energy: %f", others, energy);
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
