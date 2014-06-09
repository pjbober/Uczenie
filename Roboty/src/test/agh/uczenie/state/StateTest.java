package agh.uczenie.state;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class StateTest {

	@Test
	public void enemiesCountHashDistancesTest() {
		State state1 = new State();
		state1.enemiesCount.put(Distance.SHORT, 1);

		State state2 = new State();
		state2.enemiesCount.put(Distance.MEDIUM, 1);

		State state3 = new State();
		state3.enemiesCount.put(Distance.LONG, 1);

		assertNotEquals(state1.enemiesCountHash(), state2.enemiesCountHash());
		assertNotEquals(state2.enemiesCountHash(), state3.enemiesCountHash());
		assertNotEquals(state1.enemiesCountHash(), state3.enemiesCountHash());
	}

	@Test
	public void enemiesCountHashCountTest() {
		State state1 = new State();
		state1.enemiesCount.put(Distance.SHORT, 1);

		State state2 = new State();
		state2.enemiesCount.put(Distance.SHORT, 2);

		assertNotEquals(state1.enemiesCountHash(), state2.enemiesCountHash());
	}

	@Test
	public void enemiesEnergyHashEnergyTest() {
		State state1 = new State();
		state1.enemiesEnergyAvg.put(Distance.SHORT, Energy.ZERO);

		State state2 = new State();
		state2.enemiesEnergyAvg.put(Distance.SHORT, Energy.LOW);

		State state3 = new State();
		state3.enemiesEnergyAvg.put(Distance.SHORT, Energy.MEDIUM);

		State state4 = new State();
		state4.enemiesEnergyAvg.put(Distance.SHORT, Energy.HIGH);

		assertNotEquals(state1.enemiesEnergyHash(), state2.enemiesEnergyHash());
		assertNotEquals(state1.enemiesEnergyHash(), state3.enemiesEnergyHash());
		assertNotEquals(state1.enemiesEnergyHash(), state4.enemiesEnergyHash());
		assertNotEquals(state2.enemiesEnergyHash(), state3.enemiesEnergyHash());
		assertNotEquals(state2.enemiesEnergyHash(), state4.enemiesEnergyHash());
		assertNotEquals(state3.enemiesEnergyHash(), state4.enemiesEnergyHash());
	}

	@Test
	public void enemiesEnergyHashDistanceTest() {
		State state1 = new State();
		state1.enemiesEnergyAvg.put(Distance.SHORT, Energy.MEDIUM);

		State state2 = new State();
		state2.enemiesEnergyAvg.put(Distance.MEDIUM, Energy.MEDIUM);

		State state3 = new State();
		state3.enemiesEnergyAvg.put(Distance.LONG, Energy.MEDIUM);

		assertNotEquals(state1.enemiesEnergyHash(), state2.enemiesEnergyHash());
		assertNotEquals(state1.enemiesEnergyHash(), state3.enemiesEnergyHash());
		assertNotEquals(state2.enemiesEnergyHash(), state3.enemiesEnergyHash());
	}

}