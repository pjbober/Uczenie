import robocode.control.*;
import robocode.control.events.*;

public class BattleRunner {

	public static void main(String[] args) {
		RobocodeEngine.setLogMessagesEnabled(false);

		RobocodeEngine engine = new RobocodeEngine(new java.io.File("/home/kliput/Programy/robocode"));

		engine.addBattleListener(new BattleObserver());

		// Show the Robocode battle view
//		engine.setVisible(true);

		int numberOfRounds = 10;
		String enemiesList = "sample.Crazy";

		String robotsList = "agh.uczenie.robot.PiqleStrategicRobot*," + enemiesList;


		BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
		RobotSpecification[] selectedRobots =
				engine.getLocalRepository(robotsList);

		BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

		for (int i=0; i<100; ++i) {
			// Run our specified battle and let it run till it is over
			engine.runBattle(battleSpec, true); // waits till the battle finishes
		}

		// Cleanup our RobocodeEngine
		engine.close();

		// Make sure that the Java VM is shut down properly
		System.exit(0);
	}
}

class BattleObserver extends BattleAdaptor {

	public void onBattleCompleted(BattleCompletedEvent e) {
//		System.out.println("-- Battle has completed --");

		// Print out the sorted results with the robot names
//		System.out.println("Battle results:");

		robocode.BattleResults piqleResult = e.getIndexedResults()[0];

		System.out.println(piqleResult.getScore());
	}

//	public void onBattleMessage(BattleMessageEvent e) {
//		System.out.println("Msg> " + e.getMessage());
//	}
//
//	public void onBattleError(BattleErrorEvent e) {
//		System.out.println("Err> " + e.getError());
//	}
}