package MoesGames.DominionsServerSetup.Nations;

import java.awt.Component;
import java.util.ArrayList;

public class NationLibrary {
	
	ArrayList<Nation> nations = new ArrayList<Nation>();
	
	ArrayList<Nation> nationsEA = new ArrayList<>();
	ArrayList<Nation> nationsMA = new ArrayList<>();
	ArrayList<Nation> nationsLA = new ArrayList<>();

	public NationLibrary() {
		
	}

	public void printNations() {
		for (int i = 0; i < nations.size(); i++) {
			System.out.println(nations.get(i).toString());
		}
	}

	public void setNations(ArrayList<Nation> n) {
		nations = n;
		for (int i = 0; i < n.size(); i++) {
			switch (n.get(i).age) {
			case 1:
				nationsEA.add(n.get(i));
				break;
			case 2:
				nationsMA.add(n.get(i));
				break;
			case 3:
				nationsLA.add(n.get(i));
				break;
			default:
				break;
			}
		}
	}

	public ArrayList<Nation> getEANations() {
		return nationsEA;
	}
	public ArrayList<Nation> getMANations() {
		return nationsMA;
	}
	public ArrayList<Nation> getLANations() {
		return nationsLA;
	}
	
	

}
