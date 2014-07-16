package pl.grushenko.okapi.util;

public class Location {

	private float lat;
	private float lon;

	public Location(String string) {
		String[] res = string.split("|");
		lat = Float.parseFloat(res[0]);
		lon = Float.parseFloat(res[1]);
	}

	public Location(float lat, float lon) {
		this.lat = lat;
		this.lon = lon;
		
	}
	
	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}
	


}
