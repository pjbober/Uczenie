package agh.uczenie.strategy_select;

import agh.uczenie.state.State;
import agh.uczenie.strategy.BaseStrategy;
import agh.uczenie.strategy.StrategyManager;
import agh.uczenie.strategy.StrategyType;
import robocode.DeathEvent;
import robocode.RobocodeFileOutputStream;
import robocode.WinEvent;

import java.io.*;

public class ConditionalStrategySelect extends BaseStrategySelect {
	private static final String FILE_PATH = "memory_file.dat";

	static class Memory implements Serializable {
		public int memory = 0;

		@Override
		public String toString() {
			return String.format("%d", memory);
		}
	}

	private final Memory memory;

	public ConditionalStrategySelect(StrategyManager strategyManager) {
		super(strategyManager);
		File file = getRobot().getDataFile(FILE_PATH);
		if (file.exists() && !file.isDirectory() && file.length() > 0) {
			try {
				System.out.println("Trying to load memory from file, length: " + file.length());
				memory = loadMemory();
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("Memory file does not exists - creating new memory");
			memory = new Memory();
		}
		System.out.println("Conditional strategy select with memory: " + memory);
	}

	private Memory loadMemory() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(getRobot().getDataFile(FILE_PATH));
		ObjectInputStream in = new ObjectInputStream(file);
		Memory memory = (Memory) in.readObject();
		file.close();
		return memory;
	}

	private void writeMemory() throws IOException {
		RobocodeFileOutputStream file = new RobocodeFileOutputStream(getRobot().getDataFile(FILE_PATH));
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(this.memory);
		file.close();
	}

	@Override
	public BaseStrategy basedOnState(State state) {
		StrategyType strategyType;
		if (state.others > 2) {
			strategyType = StrategyType.BATTERING_RAM;
		} else {
			strategyType = StrategyType.AVOIDING;
		}
		return strategyManager.get(strategyType);
	}

	@Override
	public void onDeath(DeathEvent deathEvent) {
		memory.memory += 1;
		System.out.println("Death");
		onEnd();
	}

	@Override
	public void onWin(WinEvent winEvent) {
		memory.memory += 100;
		System.out.println("Win");
		onEnd();
	}

	public void onEnd() {
		try {
			writeMemory();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
