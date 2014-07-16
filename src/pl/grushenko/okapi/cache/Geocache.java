package pl.grushenko.okapi.cache;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.print.attribute.standard.MediaSize.ISO;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import pl.grushenko.okapi.util.ISO8601DateParser;

public class Geocache {
	private String code;
	private String name;
	private Location location;
	private String type;
	private String status;
	private URL url;
	private User owner;
	private float distance;
	private long founds;
	private long notfounds;
	private String size;
	private float difficulty;
	private double terrain;
	private long rating;
	private long recommendations;
	private boolean reqPasswd;
	private Date lastFound;
	private Date date_hidden;
	private boolean isFound;
	private boolean isNotFound;
	private boolean isWatched;
	private boolean isIgnored;
	private String shortDescription;
	private String description;
	private String myNotes;
	private ArrayList<Image> images;
	
	public Geocache(String code, JSONObject obj)
	{
		this.code = code;
		this.name = (String) obj.get("name");
		this.type = (String) obj.get("type");
		this.location = new Location((String) obj.get("location"));
		this.status = (String) obj.get("status");
		try {
			this.url = new URL((String) obj.get("url"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.founds = (Long) obj.get("founds");
		this.notfounds = (Long) obj.get("notfounds");
		this.size = (String) obj.get("size2");
		this.difficulty = (Long) obj.get("difficulty");
		this.terrain = (Double) obj.get("terrain");
		this.rating = (Long) obj.get("rating");
		this.recommendations = (Long) obj.get("recommendations");
		this.reqPasswd = (Boolean) obj.get("req_passwd");
		try {
			this.lastFound = ISO8601DateParser.parse((String) obj.get("last_found"));
			this.date_hidden = ISO8601DateParser.parse((String) obj.get("date_hidden"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.owner = new User((JSONObject) obj.get("owner"));
		this.isFound = (Boolean) obj.get("is_found");
		this.isNotFound = (Boolean) obj.get("is_not_found");
		this.isWatched = (Boolean) obj.get("is_watched");
		this.isIgnored = (Boolean) obj.get("is_ignored");
		
		this.shortDescription = (String) obj.get("short_description");
		this.description = (String) obj.get("description");
		
		this.myNotes = (String) obj.get("my_notes");
		
		this.images = new ArrayList<Image>();
		
		JSONArray imgs = (JSONArray) obj.get("images");
		//imgs
		//logs
		//alt_wpts
		//trackables
		
		
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Location getLocation() {
		return location;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public URL getUrl() {
		return url;
	}

	public User getOwner() {
		return owner;
	}

	public float getDistance() {
		return distance;
	}

	public long getFounds() {
		return founds;
	}

	public long getNotfounds() {
		return notfounds;
	}

	public String getSize() {
		return size;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public double getTerrain() {
		return terrain;
	}

	public long getRating() {
		return rating;
	}

	public long getRecommendations() {
		return recommendations;
	}

	public boolean isReqPasswd() {
		return reqPasswd;
	}

	public Date getLastFound() {
		return lastFound;
	}

	public Date getDate_hidden() {
		return date_hidden;
	}

	public boolean isFound() {
		return isFound;
	}

	public boolean isNotFound() {
		return isNotFound;
	}

	public boolean isWatched() {
		return isWatched;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public String getMyNotes() {
		return myNotes;
	}

	public ArrayList<Image> getImages() {
		return images;
	}
}
