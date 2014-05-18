package agh.uczenie.state;

public enum Distance {
	SHORT(0),
	MEDIUM(1),
	LONG(2);

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
