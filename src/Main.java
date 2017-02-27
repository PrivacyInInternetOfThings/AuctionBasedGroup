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
		Vehicle v1 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.NOEMERGENCY, MALFUNCTIONTYPE.NOMALFUNCTION, 1, 1);
		Vehicle v2 = new Vehicle(VEHICLETYPE.EMERGENCY, EMERGENCYTYPE.PATIENT, MALFUNCTIONTYPE.NOMALFUNCTION, 4, 2);
		Vehicle v3 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.NOEMERGENCY, MALFUNCTIONTYPE.WHEEL, 3, 3);
		Vehicle v4 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.LATEFORWORK, MALFUNCTIONTYPE.NOMALFUNCTION, 1, 4);
		Vehicle v5 = new Vehicle(VEHICLETYPE.ORDINARY, EMERGENCYTYPE.LATEFORSCHOOL, MALFUNCTIONTYPE.NOMALFUNCTION, 14,
				5);

		v1.setPrivacy(0.0445, 0.115, 0.01575, 0.1755);
		v2.setPrivacy(0.1875, 0.243, 0.029, 0.174);
		v3.setPrivacy(0.25, 0.25, 0.25, 0.25);
		v4.setPrivacy(0.094, 0.19, 0.18, 0.17);
		v5.setPrivacy(0.171, 0.066, 0.22, 0.174);
		// v5.isTurn = true;

		ArrayList<Vehicle> vehicles = new ArrayList<>();
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v3);
		vehicles.add(v4);
		vehicles.add(v5);

		Group g1 = new Group(5);
		g1.addVehicle(v1);
		g1.addVehicle(v2);
		g1.addVehicle(v3);

		Group g2 = new Group(2);
		g2.addVehicle(v4);
		g2.addVehicle(v5);

		ArrayList<Group> groups = new ArrayList<>();
		groups.add(g1);
		groups.add(g2);

		displayGroups(g1, g2);

		for (int i = 0; i < groups.size(); i++) {
			for (int j = i + 1; j < groups.size(); j++) {
				makeNegotiation(groups.get(i), groups.get(j));
			}
		}
		System.out.println();
	}

	public static void makeNegotiation(Group g1, Group g2) {
		// Auction
		double oldUtility1 = 0, oldUtility2 = 0;
		double utility1 = 0, utility2 = 0;
		System.out.println("\n-----------" + "Turn for Group " + g1.id + "-----------\n");
		System.out.println("Group Leader: Vehicle " + g1.sortedVehicles.get(0).id);
		System.out.println("Offer:");
		utility1 += g1.makeOffer(0);
		System.out.println("\ng"+g1.id+" utility: " + utility1 + "\ng"+g2.id+" utility: " + utility2);
		int turn = 2, count = 0;

		while (!g1.vehicles.isEmpty() && !g2.vehicles.isEmpty()) {
			while (true) {
				System.out.println("\n-----------" + "Turn for Group " + (turn == 1 ? g1.id : g2.id) + "-----------\n");
				if (turn == 1) {
					oldUtility1 = utility1;
					System.out.println("Group Leader: Vehicle " + g1.sortedVehicles.get(0).id);
					System.out.println("Offer:");
					utility1 += g1.makeOffer(utility2);
					System.out.println("\ng"+g1.id+" utility: " + utility1 + "\ng"+g2.id+" utility: " + utility2);
					turn = 2;
					if (utility1 - oldUtility1 < 0.0000001) {
						break;
					}

				} else {
					oldUtility2 = utility2;
					System.out.println("Group Leader: Vehicle " + g2.sortedVehicles.get(0).id);
					System.out.println("Offer:");
					utility2 += g2.makeOffer(utility1);
					System.out.println("\ng"+g1.id+" utility: " + utility1 + "\ng"+g2.id+" utility: " + utility2);
					turn = 1;
					if (utility2 - oldUtility2 < 0.0000001) {
						break;
					}

				}
			}
			if (utility1 >= utility2) {
				System.out.println();
				System.out.println("Group " + g1.id + " Won!");
				System.out.println("Updating Group " + g1.id);
				g1.updateGroup(g1.sortedVehicles.get(0).groupOrder);
				displayGroups(g1, g2);

				utility1 = 0;
				oldUtility1 = 0;
			} else {
				System.out.println();
				System.out.println("Group " + g2.id + " Won!");
				System.out.println("Updating Group " + g2.id);
				g2.updateGroup(g2.sortedVehicles.get(0).groupOrder);

				displayGroups(g1, g2);
				utility2 = 0;
				oldUtility2 = 0;
			}

		}

		if (!g1.vehicles.isEmpty()) {
			g1.updateGroup(g1.vehicles.size() - 1);
		} else if (!g2.vehicles.isEmpty()) {
			g2.updateGroup(g2.vehicles.size() - 1);
		}

	}

	public static void displayGroups(Group g1, Group g2) {
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------");
		System.out.println("Positions of Groups");
		System.out.println("Group " + g1.id + "\t\t\t\t\t|\tGroup " + g2.id);

		for (int i = 0; i < Math.max(g1.vehicles.size(), g2.vehicles.size()); i++) {
			if (i < g1.vehicles.size()) {
				System.out.print(g1.vehicles.get(i));
				if (g1.vehicles.get(i).equals(g1.sortedVehicles.get(0))) {
					System.out.print("   <-");
				}
			} else {
				System.out.print("\t\t\t\t");
			}

			System.out.print("\t|\t");

			if (i < g2.vehicles.size()) {
				System.out.print(g2.vehicles.get(i));
				if (g2.vehicles.get(i).equals(g2.sortedVehicles.get(0))) {
					System.out.print(" <-");
				}
			}
			System.out.println();
		}

		System.out.println("-----------------------------------------------------------------------------------------");

	}

}
