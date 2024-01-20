package MoesGames.DominionsServerSetup.Nations;

public class Nation {
	int age;
	String name;
	int id;
	public Nation(int age, String name, int id) {
		this.age = age;
		this.name = name;
		this.id = id;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("age: ");
		sb.append(age);
		sb.append("\n");
		sb.append("name: ");
		sb.append(name);
		sb.append("\n");
		sb.append("id: ");
		sb.append(id);
		sb.append("\n");
		return sb.toString();
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getShortName() {
		int indexOfHyphen = name.indexOf('-');
	    return name.substring(0, indexOfHyphen-1);
	}
	 

	
	

}
