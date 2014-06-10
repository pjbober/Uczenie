package agh.uczenie.utils;

import agh.uczenie.strategy_select.PiqleStrategySelect;
import piqle.algorithms.QLearningSelector;

import java.io.IOException;

public class KnowledgePrinter {

	public static final String FILE_PATH = "/home/kliput/Programowanie/uczenie/projekt/Uczenie/Roboty/piqle_memory.data";

	public static void main(String[] args) {
		QLearningSelector piqleSelector = null;

		String filePath = args.length > 0 ? args[0] : FILE_PATH;

		try {
			piqleSelector = (QLearningSelector) PiqleStrategySelect.loadPiqleSelector(filePath);
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		System.out.println(piqleSelector.toString());
	}
}
