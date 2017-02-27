public enum MALFUNCTIONTYPE {
	WHEEL(0.2), MOTOR(0.3), LIGHTSANDSENSORS(0.5), NOMALFUNCTION(1);

	double utilityValue;

	private MALFUNCTIONTYPE(double val) {
		this.utilityValue = val;
	}

	public double getValue() {
		return this.utilityValue;
	}

	public static String abbreviation(MALFUNCTIONTYPE m) {
		if (m == MALFUNCTIONTYPE.WHEEL) {
			return "WHL";
		} else if (m == MALFUNCTIONTYPE.MOTOR) {
			return "MTR";
		} else if (m == MALFUNCTIONTYPE.LIGHTSANDSENSORS) {
			return "LGT";
		} else if (m == MALFUNCTIONTYPE.NOMALFUNCTION) {
			return "NON";
		}
		return null;
	}
}