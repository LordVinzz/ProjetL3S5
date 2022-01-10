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
	
	public static Group[] getGroupForJTree(User user) {
		Group[] list = new Group[3];
		
		switch(user.getGroup()) {
		case PROFS:
			list[0] = PROFS;
			list[1] = ADMIN;
			list[2] = ENTRETIENS;
			break;
		case ELEVES:
			list[0] = ELEVES;
			list[1] = ADMIN;
			list[2] = ENTRETIENS;
			break;
		case ADMIN:
			list[0] = ADMIN;
			list[1] = PROFS;
			list[2] = ELEVES;
			break;
		case ENTRETIENS:
			list[0] = ENTRETIENS;
			list[1] = PROFS;
			list[2] = ELEVES;
			break;
		default:
			break;
		}
		return list;
			
	}

	public static Group[] getGroupForComboBox(User user) {
		Group[] list = new Group[2];
		
		switch(user.getGroup()) {
		case PROFS:
			list[0] = ADMIN;
			list[1] = ENTRETIENS;
			break;
		case ELEVES:
			list[0] = ADMIN;
			list[1] = ENTRETIENS;
			break;
		case ADMIN:
			list[0] = PROFS;
			list[1] = ELEVES;
			break;
		case ENTRETIENS:
			list[0] = PROFS;
			list[1] = ELEVES;
			break;
		default:
			break;
		}
		return list;
	}
	
}
