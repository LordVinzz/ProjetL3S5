package fr.project.l3s5.groups;

public enum Group {

	ELEVE(1),;

	private int id;
	
	Group(int id){
		this.id = id;
	}
	
	public static Group getGroupByID(int int1) {
		for(Group g : Group.values()) {
			if(int1 == g.id)return g;
		}
		return null;
	} 
	
}
