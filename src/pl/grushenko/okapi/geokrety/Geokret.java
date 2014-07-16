package pl.grushenko.okapi.geokrety;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

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

	private int id;
	private String name;
	private int distance;
	private Location location;
	private String waypoint;
	private int ownerId;
	private GeokretState state;
	private GeokretType type;
	private BufferedImage image;


	public Geokret(int id, String name, int distance, Location location, String waypoint, int ownerId, GeokretState state, GeokretType type, String image) {
		this.id = id;
		this.name = name;
		this.distance = distance;
		this.location = location;
		this.waypoint = waypoint;
		this.ownerId = ownerId;
		this.state = state;
		this.type = type;
		if(image != null) {
			try {
				this.image = ImageIO.read(new URL("http://geokrety.org/" + image));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getDistance() {
		return distance;
	}

	public Location getLocation() {
		return location;
	}

	public String getWaypoint() {
		return waypoint;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public GeokretState getState() {
		return state;
	}

	public GeokretType getType() {
		return type;
	}

	public BufferedImage getImage() {
		return image;
	}
	
}
