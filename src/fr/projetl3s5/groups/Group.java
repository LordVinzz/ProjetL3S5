package fr.projetl3s5.groups;

import fr.projetl3s5.client.ui.User;

public enum Group {

	INVALIDE(0), ELEVES(1), PROFS(2), ENTRETIENS(4), ADMIN(8);

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
	
	public static Group[] values(User user) {
		Group[] list = new Group[2];
		
		if (user.getGroup() == Group.PROFS || user.getGroup() == Group.ELEVES) {
			list[0]=ENTRETIENS;
			list[1]=ADMIN;
		}
		else {
			list[0]=PROFS;
			list[1]=ELEVES;	
		}
		
		return list;
			
	}
	
}
