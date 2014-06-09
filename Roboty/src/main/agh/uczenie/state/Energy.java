package agh.uczenie.state;

public enum Energy {
	ZERO(1),
	LOW(2),
	MEDIUM(3),
	HIGH(4);

	private final int score;

	Energy(int score) {
		this.score = score;
	}

	public static Energy fromDouble(double value) {
		return value == 0 ? ZERO : (value < 33 ? LOW : (value < 66 ? MEDIUM : HIGH));
	}

	public int getScore() {
		return score;
	}
}
