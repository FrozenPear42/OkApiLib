package pl.grushenko.okapi.geokrety;

import pl.grushenko.okapi.util.Location;

public class Geokret {

	public enum GeokretState {
		DROPPED(0),
		GRABBED(1),
		COMMENT(2),
		SEEN(3),
		ARCHIVED(4),
		VISITING(5);
		
		private int id;
		
		GeokretState(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	
	public enum GeokretType {
		TRADITIONAL(0),
		MEDIA(1),
		HUMAN(2),
		COIN(3),
		KRETYPOST(4);
		
		private int id;
		
		GeokretType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}
	
	public Geokret(int id, String name, int distance, Location location, String waypoint, int ownerId, int state, int type, String image) {
		System.out.println(id);
		System.out.println(name);
		System.out.println(distance);
		System.out.println(waypoint);
		System.out.println(ownerId);
		System.out.println(state);
		System.out.println(type);
		System.out.println(image);
	}

}
