package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.StrategyType;
import agh.uczenie.strategy_select.piqle.StrategySelectAction;
import piqle.algorithms.AbstractMemorySelector;
import piqle.algorithms.QLearningSelector;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;


public class PiqleStrategySelect extends BaseStrategySelect {

	AbstractMemorySelector piqleSelector = new QLearningSelector();

	public static final IAction[] allActionsList;


	State prevState = null;
	StrategySelectAction stateTransitionAction = null;

	private double reinforcementMemory = 0;

	static {
		allActionsList = new IAction[StrategyType.uniqueValues().length];
		StrategyType[] strategyTypes = StrategyType.uniqueValues();
		for (int i=0; i<allActionsList.length; ++i) {
			allActionsList[i] = new StrategySelectAction(strategyTypes[i]);
		}
	}

	public PiqleStrategySelect(StrategyManager strategyManager) {
		super(strategyManager);
	}

	public ActionList createActionList(IState state) {
		ActionList actionList = new ActionList(state);
		for (IAction action : allActionsList) {
			actionList.add(action);
		}
		return actionList;
	}

	@Override
	public BaseStrategy basedOnState(State state) {

		if (prevState == null) {
			// first strategy choice - default action and no learning
			stateTransitionAction = new StrategySelectAction(StrategyType.DEFAULT);
			prevState = state;
			resetReinforcementMemory();
			return strategyManager.get(stateTransitionAction.getStrategyType());
		} else {
			double reinforcement = computeReinforcement(prevState, state);
			resetReinforcementMemory();

			System.out.println(String.format("Learn: %s -> %s [%f]",
					prevState.toString(), state.toString(), reinforcement));

			piqleSelector.learn((IState) prevState, (IState) state, stateTransitionAction, reinforcement);

			prevState = state;
			stateTransitionAction = (StrategySelectAction) piqleSelector.getChoice(createActionList((IState) state));

			return strategyManager.get((stateTransitionAction).getStrategyType());
		}
	}

	private double computeReinforcement(State prevState, State currentState) {
		return reinforcementMemory + prevState.computeScoreTo(currentState);
	}

	private void resetReinforcementMemory() {
		reinforcementMemory = 0;
	}

}
