package agh.uczenie.state;

public enum Distance {
	SHORT(1),
	MEDIUM(2),
	LONG(3);

	private final int number;

	Distance(int number) {
		this.number = number;
	}

	public static Distance fromDouble(double value) {
		return value < 240 ? SHORT : (value < 460 ? MEDIUM : LONG);
	}

	public int getNumber() {
		return number;
	}
}
