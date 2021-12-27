package fr.projetl3s5.groups;

import java.util.ArrayList;
import java.util.List;

public enum Group {

	ELEVE(1);

	private int id;
	
	Group(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static List<Group> getGroupsByID(int int1) {
		List<Group> list = new ArrayList<>();
		
		for(Group g : Group.values()) {
			if((int1 & g.id) != 0) list.add(g);
		}
		return list;
	}
	
	public static Group getGroupByID(int int1) {
		for(Group g : Group.values()) {
			if(int1 == g.id) return g;
		}
		return null;
	}
	
}
