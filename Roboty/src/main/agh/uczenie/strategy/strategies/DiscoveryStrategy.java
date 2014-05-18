package agh.uczenie.strategy.strategies;

import agh.uczenie.state.Distance;
import agh.uczenie.state.EnemyInfo;
import agh.uczenie.state.Energy;
import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscoveryStrategy extends BaseStrategy {
	private Map<String, EnemyInfo> robotsMap = new HashMap<>();

	public DiscoveryStrategy(AdvancedRobot robot) {
		super(robot);
	}

	public State getState() {
		State state = new State();
		state.selfEnergy = Energy.fromDouble(robot.getEnergy());

		// for each discrete distance, compute sum of enemies and their average energy

		Map<Distance, List<Double>> energyValues = new HashMap<>();
		Map<Distance, Energy> energyAvg = new HashMap<>();
		for (Distance distance : Distance.values()) {
			energyValues.put(distance, new ArrayList<Double>());
			energyAvg.put(distance, Energy.ZERO);
		}

		for (EnemyInfo enemyInfo : robotsMap.values()) {
			Distance distance = Distance.fromDouble(enemyInfo.distance);
			state.enemiesCount.put(distance, state.enemiesCount.get(distance)+1);

			energyValues.get(distance).add(enemyInfo.energy);
		}

		for (Map.Entry<Distance, List<Double>> distanceEnergies : energyValues.entrySet()) {
			Distance distance = distanceEnergies.getKey();
			List<Double> energies = distanceEnergies.getValue();
			double sum = 0;
			for (double energy : energies) {
				sum += energy;
			}
			energyAvg.put(distance, Energy.fromDouble((energies.isEmpty() ? 0 : sum/energies.size())));
		}

		state.enemiesEnergyAvg = energyAvg;

		return state;
	}

	@Override
	public void setup() {
		System.out.println("Discovery start");
		// blocking - do not allow to change this strategy when discovering
		robot.turnRadarRight(360);
		System.out.println("Discovery end");
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		super.onScannedRobot(event);
		System.out.println("Scanned: " + Distance.fromDouble(event.getDistance()));

		EnemyInfo enemy = new EnemyInfo(event.getDistance(), event.getEnergy());
		robotsMap.put(event.getName(), enemy);
	}


}
