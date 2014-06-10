package agh.uczenie.strategy_select;

import agh.uczenie.state.SpecialState;
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
import robocode.*;

import java.io.*;


public class PiqleStrategySelect extends BaseStrategySelect {
	private static double totalReinforcement = 0;

	private static final String FILE_PATH = "piqle_memory.data";
	QLearningSelector piqleSelector;

	public static final IAction[] allActionsList;


	State prevState = null;
	StrategySelectAction stateTransitionAction = null;

	private double reinforcementMemory = 0;
	private double lastEnergy = 0;

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
		if (file.exists() && !file.isDirectory() && file.length() > 0) {
			try {
				System.out.println("Trying to load memory from file, length: " + file.length());
				piqleSelector = (QLearningSelector) loadPiqleSelector(FILE_PATH);
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("Memory file does not exists - creating new memory");
			piqleSelector = new QLearningSelector();
		}

		lastEnergy = getRobot().getEnergy();
//		System.out.println("Piqle strategy select with memory: " + piqleSelector.toString());
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
			learnAndPushState(state);
			stateTransitionAction = (StrategySelectAction) piqleSelector.getChoice(createActionList((IState) state));

			return strategyManager.get(stateTransitionAction.getStrategyType());
		}
	}

	public void learnAndPushState(State state) {
		double reinforcement = computeReinforcement(prevState, state);
		resetReinforcementMemory();
		totalReinforcement += reinforcement;

		System.out.println(String.format("Learn: %s --[%s]--> %s (%f)",
			prevState.toString(), stateTransitionAction, state.toString(), reinforcement));

		piqleSelector.learn((IState) prevState, (IState) state, stateTransitionAction, reinforcement);

		prevState = state;
	}

	private double computeReinforcement(State prevState, State currentState) {
		double energyReinforcement = energyReinforcement();
		double stateReinforcement = prevState.computeScoreTo(currentState);

		System.out.println(String.format("Reinforcement: M: %.2f, E: %.2f, S: %.2f",
				reinforcementMemory, energyReinforcement, stateReinforcement));

		return reinforcementMemory + energyReinforcement + stateReinforcement;
	}

	private double energyReinforcement() {
		return getRobot().getEnergy() - lastEnergy;
	}

	private void resetReinforcementMemory() {
		reinforcementMemory = 0;
		lastEnergy = getRobot().getEnergy();
	}

	@Override
	public void onDeath(DeathEvent deathEvent) {
		learnAndPushState(new State(SpecialState.LOSE));
	}

	@Override
	public void onWin(WinEvent winEvent) {
		learnAndPushState(new State(SpecialState.WIN));
	}

	@Override
	public void onBulletHit(BulletHitEvent bulletHitEvent) {
//		reinforcementMemory += bulletHitEvent.getBullet().getPower();
	}

	@Override
	public void onBulletMissed(BulletMissedEvent bulletMissedEvent) {
//		reinforcementMemory -= bulletMissedEvent.getBullet().getPower();
	}

	@Override
	public void onHitByBullet(HitByBulletEvent hitByBulletEvent) {
//		reinforcementMemory -= hitByBulletEvent.getBullet().getPower();
	}

	@Override
	public void onHitWall(HitWallEvent hitWallEvent) {
//		double penalty = (Math.abs(hitWallEvent.getBearing())/180)*10.0;
//		System.out.println("Penalty for hit wall: " + penalty);
//		reinforcementMemory -= (Math.abs(hitWallEvent.getBearing())/180)*10.0;
	}

	@Override
	public void onRoundEnded(RoundEndedEvent roundEndedEvent) {
		super.onRoundEnded(roundEndedEvent);
		try {
			writeMemory();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onBattleEnded(BattleEndedEvent battleEndedEvent) {
		super.onBattleEnded(battleEndedEvent);
		logToFile("piqle_rewards_log", Double.toString(totalReinforcement));
		totalReinforcement = 0;
	}

	public static void logToFile(String fileName, String message) {
		try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))) {
			out.println(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static ISelector loadPiqleSelector(String path) throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(path);
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
