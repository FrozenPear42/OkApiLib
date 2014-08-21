package pl.grushenko.okapi.cache;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;

import pl.grushenko.okapi.util.CaptionedImage;
import pl.grushenko.okapi.util.ISO8601DateParser;
import pl.grushenko.okapi.util.Location;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Geocache {
	private String code;
	private String name;
	private Location location;
	private GeocacheType type;
	private GeocacheStatus status;
	private URL url;
	private User owner;
	private float distance;
	private long founds;
	private long notfounds;
	private String size;
	private float difficulty;
	private float terrain;
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
	private ArrayList<CaptionedImage> images;
	
	public Geocache(String code, JsonObject obj, boolean minimal)
	{
		this.code = code;
		this.name =  obj.get("name").asString();
		this.type =  GeocacheType.fromString(obj.get("type").asString());
		this.shortDescription =  obj.get("short_description").asString();
		this.status =  GeocacheStatus.fromString(obj.get("status").asString());
		
		if(minimal) return;
		
		this.description =  obj.get("description").asString();
		this.location = new Location( obj.get("location").asString());
		try {
			this.url = new URL( obj.get("url").asString() );
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.founds = (Long) obj.get("founds").asLong();
		this.notfounds = (Long) obj.get("notfounds").asLong();
		this.size =  obj.get("size2").asString();

		this.difficulty = obj.get("difficulty").asFloat();
		this.terrain = obj.get("terrain").asFloat();
		
		this.rating = obj.get("rating").asInt();
		this.recommendations = obj.get("recommendations").asInt();
		this.reqPasswd = (Boolean) obj.get("req_passwd").asBoolean();
		try {
			this.lastFound = ISO8601DateParser.parse( obj.get("last_found").asString());
			this.date_hidden = ISO8601DateParser.parse( obj.get("date_hidden").asString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.owner = new User((obj.get("owner").asObject()));
		this.isFound = obj.get("is_found").asBoolean();
		this.isNotFound = obj.get("is_not_found").asBoolean();
		this.isWatched =  obj.get("is_watched").asBoolean();
		this.isIgnored =  obj.get("is_ignored").asBoolean();
		
		
		this.myNotes =  obj.get("my_notes").asString();
		
		this.images = new ArrayList<CaptionedImage>();
		
		JsonArray imgs = obj.get("images").asArray();
		
		Iterator<JsonValue> it = imgs.iterator();
		
		while(it.hasNext()) {
			JsonObject img = it.next().asObject();
			try {
				this.images.add(new CaptionedImage(ImageIO.read(new URL(img.get("url").asString())),  img.get("caption").asString(), img.get("is_spoiler").asBoolean()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
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

	public GeocacheType getType() {
		return type;
	}

	public GeocacheStatus getStatus() {
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

	public float getTerrain() {
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

	public ArrayList<CaptionedImage> getImages() {
		return images;
	}

	public enum GeocacheType {
		TRADITIONAL("Traditional"),
		MULTI("Multi"),
		QUIZ("Quiz"),
		VIRTUAL("Virtual"),
		EVENT("Event"),
		OTHER("Other"),
		OWN("Own"),
		WEBCAM("Webcam"),
		MOVING("Moving");
		
		private final String data;
		private GeocacheType(String data) {
			this.data = data;
		}
		public String getData(){
			return data;
		}
		public static GeocacheType fromString(String s) {
			try {
				return valueOf(s.toUpperCase());
			} catch (Exception e) {
				return OTHER;
			}
		}
		
	}
	
	public enum GeocacheStatus {
		AVAILABLE("Available"),
		TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
		ARCHIVED("Archived"),
		UNKNOWN("Unknown");
		
		private final String data;
		
		private GeocacheStatus(String data) {
			this.data = data;
		}
		
		public String getData() {
			return data;
		}
		
		public static GeocacheStatus fromString(String s) {
			for(GeocacheStatus v : values())
				if(v.data.equals(s))
					return v;
			return UNKNOWN;
		}
	}
}

