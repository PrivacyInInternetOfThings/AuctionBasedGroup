import java.util.ArrayList;
import java.util.Iterator;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Group {

	ArrayList<Vehicle> vehicles = new ArrayList<>();
	ArrayList<Vehicle> sortedVehicles = new ArrayList<>();
	
	public Group() {

	}

	public void addVehicle(Vehicle v) {
		v.groupOrder = vehicles.size();
		vehicles.add(v);
		sortedVehicles = sortVehicles();
	}

	public ArrayList<Vehicle> sortVehicles() {
		ArrayList<Double> points = new ArrayList<>();
		ArrayList<Integer> indexes = new ArrayList<>();
		ArrayList<Vehicle> res = new ArrayList<>();
		for (int i = 0; i < vehicles.size(); ++i) {
			points.add(vehicles.get(i).calculateUtilityPoints());
		}
		
		
		int maxIndex = 0;
		double maxVal = 0;
		for (int i =0;i<vehicles.size();++i) {
			for (int j=0;j<vehicles.size();++j) {
				if(points.get(j) > maxVal ){
					maxVal = points.get(j);
					maxIndex = j;
				}
			}
			
			indexes.add(maxIndex);
			points.set(maxIndex, (double) 0);
		}
		
		for(int i=0;i<vehicles.size();++i){
			res.add(vehicles.get(indexes.get(i)));
		}
		
		
		return res;
		
	}
	
	
	public double makeOffer(double opponentOffer){
		return sortedVehicles.get(0).makeOffer(opponentOffer,sortedVehicles.get(0).isTurn);
	}
	
	public void updateGroup(int outCar) {
		for (int i = 0; i < outCar; i++) {
			vehicles.remove(0);	
		}
		for (int i = 0; i < vehicles.size(); i++) {
			vehicles.get(i).groupOrder = i;
		}
		this.sortedVehicles = this.sortVehicles();
	}
}
