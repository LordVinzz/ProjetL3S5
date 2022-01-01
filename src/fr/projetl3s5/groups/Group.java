package fr.projetl3s5.groups;

import java.util.ArrayList;
import java.util.List;

public enum Group {

	ELEVES(1), PROFS(2), TDA1(4), TD2(8), TD3(16), SERVICE_INFO(32), SERVICE_ADMIN(64), SERVICE_TECHN(128), LES_MEUFS_QUI_FONT_LE_MENAGE(256), SPORTS(512), ASSOCIATIONS(1024);

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
