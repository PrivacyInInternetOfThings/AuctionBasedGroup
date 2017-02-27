public enum EMERGENCYTYPE {
	PATIENT(1), FIRE(0.9), LATEFORWORK(0.5), LATEFORSCHOOL(0.5), NOEMERGENCY(0.1);

	double utilityValue;

	private EMERGENCYTYPE(double val) {
		this.utilityValue = val;
	}

	public double getValue() {
		return this.utilityValue;
	}

	public static String abbreviation(EMERGENCYTYPE e) {
		switch (e) {
		case PATIENT:
			return "PAT";
		case FIRE:
			return "FIR";
		case LATEFORWORK:
			return "LFW";
		case LATEFORSCHOOL:
			return "LFS";
		case NOEMERGENCY:
			return "NON";
		default:
			return null;
		}
	}
}