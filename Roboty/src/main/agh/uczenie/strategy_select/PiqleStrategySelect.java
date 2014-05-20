package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.StrategyType;
import agh.uczenie.strategy_select.piqle.StrategySelectAction;
import piqle.algorithms.ISelector;
import piqle.algorithms.QLearningSelector;
import piqle.environment.ActionList;
import piqle.environment.IAction;
import piqle.environment.IState;
import robocode.DeathEvent;
import robocode.WinEvent;

import java.io.*;


public class PiqleStrategySelect extends BaseStrategySelect {

	private static final String FILE_PATH = "piqle_memory.data";
	QLearningSelector piqleSelector;

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

		File file = new File(FILE_PATH);
//		File file = getRobot().getDataFile(FILE_PATH);
		if (file.exists() && !file.isDirectory() && file.length() > 0) {
			try {
				System.out.println("Trying to load memory from file, length: " + file.length());
				piqleSelector = (QLearningSelector) loadPiqleSelector();
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("Memory file does not exists - creating new memory");
			piqleSelector = new QLearningSelector();
		}
		System.out.println("Piqle strategy select with memory: " + piqleSelector.toString());
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

			System.out.println(String.format("Learn: %s --[%s]--> %s (%f)",
					prevState.toString(), stateTransitionAction, state.toString(), reinforcement));

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

	@Override
	public void onDeath(DeathEvent deathEvent) {
		onEnd();
	}

	@Override
	public void onWin(WinEvent winEvent) {
		onEnd();
	}

	public void onEnd() {
		try {
			writeMemory();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ISelector loadPiqleSelector() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(FILE_PATH);
		ObjectInputStream in = new ObjectInputStream(file);
		ISelector selector = (ISelector) in.readObject();
		file.close();
		return selector;
	}

	private void writeMemory() throws IOException {
		FileOutputStream file = new FileOutputStream(FILE_PATH);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(this.piqleSelector);
		file.close();
	}

}
