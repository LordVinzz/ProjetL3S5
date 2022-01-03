package fr.projetl3s5.groups;

import java.util.ArrayList;
import java.util.List;

public enum Group {

	ELEVES(1), PROFS(2), ENTRETIENS(4), ADMIN(8);

	private int id;
	
	Group(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public static Group getGroupByID(int int1) {
		for(Group g : Group.values()) {
			if(int1 == g.id) return g;
		}
		return null;
	}
	
}
