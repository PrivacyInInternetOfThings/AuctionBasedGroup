import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

	public static double proportionVehicleType = 0.3;
	public static double proportionEmergencyType = 0.45;
	public static double proportionMalfunctionType = 0.1;
	public static double proportionNumberPeople = 0.15;
	public static NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
	public static DecimalFormat df = (DecimalFormat) nf;
	public static NumberFormat formatter = df;

	public static void main(String[] args) {
		Vehicle v1 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.NOEMERGENCY, MALFUNCTIONTYPE.NOMALFUNCTION, 1);
		Vehicle v2 = new Vehicle(VEHICLETYPE.EMERGENCY, EMERGENCYTYPE.PATIENT, MALFUNCTIONTYPE.NOMALFUNCTION, 4);
		Vehicle v3 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.NOEMERGENCY, MALFUNCTIONTYPE.WHEEL, 3);
		Vehicle v4 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.LATEFORWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 1);
		Vehicle v5 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.LATEFORSCHOOL, MALFUNCTIONTYPE.NOMALFUNCTION, 14);

		v1.setPrivacy(0.0445, 0.115, 0.01575, 0.1755);
		v2.setPrivacy(0.1875, 0.243, 0.029, 0.174);
		v3.setPrivacy(0.25, 0.25, 0.25, 0.25);
		v4.setPrivacy(0.094, 0.19, 0.18, 0.17);
		v5.setPrivacy(0.171, 0.066, 0.22, 0.174);
		//v5.isTurn = true;

		ArrayList<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v3);
		vehicles.add(v4);
		vehicles.add(v5);
		
		Group g1 = new Group();
		g1.addVehicle(v1);
		g1.addVehicle(v2);
		g1.addVehicle(v3);
		
		Group g2 = new Group();
		g2.addVehicle(v4);
		g2.addVehicle(v5);
		
		ArrayList<Group> groups = new ArrayList<>();
		groups.add(g1);
		groups.add(g2);
		
		
		for (int i = 0; i < groups.size(); i++) {
			for (int j = i + 1; j < groups.size(); j++) {
				System.out.println("Groups");
				System.out.println("v1 = Vehicle " + (i + 1) + " v2 = Vehicle " + (j + 1));
				makeNegotiation(groups.get(i), groups.get(j));
/*				System.out.println("---------------------------------");
				if (result == 1) {
					System.out.println("Vehicle " + (i + 1) + " gets priority");
				} else {
					System.out.println("Vehicle " + (j + 1) + " gets priority");
				}
				System.out.println("v1 lostPrivacy: " + formatter.format(vehicles.get(i).lostPrivacy) + "/"
						+ formatter.format(vehicles.get(i).totalPrivacy) + " "
						+ formatter.format(100*vehicles.get(i).lostPrivacy / vehicles.get(i).totalPrivacy) + "%"
						+ " v2 lostPrivacy: " + formatter.format(vehicles.get(j).lostPrivacy) + "/"
						+ formatter.format(vehicles.get(j).totalPrivacy) + " "
						+ formatter.format(100*vehicles.get(j).lostPrivacy / vehicles.get(j).totalPrivacy) + "%");
				vehicles.get(i).clear();
				vehicles.get(j).clear();
				System.out.println();
				System.out.println(); */
			}
		}
		System.out.println();
	}

	public static void makeNegotiation(Group g1, Group g2) {
		// Auction
		double oldUtility1 = 0, oldUtility2 = 0;
		double utility1 = 0, utility2 = 0;
		System.out.println("\n-----------" + "Turn for v" + 1 + "-----------\n");
		utility1 += g1.makeOffer(0);
		System.out.println("\nv1 utility: " + utility1 + " v2 utility: " + utility2);
		int turn = 2, count = 0;
		
		while(!g1.vehicles.isEmpty() && !g2.vehicles.isEmpty()) {
			while (++count < 15) {
				System.out.println("\n-----------" + "Turn for v" + turn + "-----------\n");
				if (turn == 1) {
					oldUtility1 = utility1;
					utility1 += g1.makeOffer(utility2);
					System.out.println("\nv1 utility: " + utility1 + " v2 utility: " + utility2);
	
					if (utility1 - oldUtility1 < 0.00001) {
						break;
					}
					turn = 2;
				} else {
					oldUtility2 = utility2;
					utility2 += g2.makeOffer(utility1);
					System.out.println("\nv1 utility: " + utility1 + " v2 utility: " + utility2);
					if (utility2 - oldUtility2 < 0.00001) {
						break;
					}
	
					turn = 1;
				}
			}
			if (utility1 >= utility2) {
				g1.updateGroup(g1.sortedVehicles.get(0).groupOrder);
				utility1 = 0;
				oldUtility1 = 0;
			} else {
				g2.updateGroup(g2.sortedVehicles.get(0).groupOrder);
				utility2 = 0;
				oldUtility2 = 0;
			}
		
		}
		
		
	}
	
	
}
