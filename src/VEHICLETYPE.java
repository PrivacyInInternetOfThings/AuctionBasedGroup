public enum VEHICLETYPE {
	EMERGENCY(1), ORDINARY(0.1);

	double utilityValue;

	private VEHICLETYPE(double val) {
		this.utilityValue = val;
	}

	public double getValue() {
		return this.utilityValue;
	}

	public static String abbreviation(VEHICLETYPE v) {
		if (v == VEHICLETYPE.EMERGENCY) {
			return "EMR";
		} else if (v == VEHICLETYPE.ORDINARY) {
			return "ORD";
		}
		return null;
	}
}