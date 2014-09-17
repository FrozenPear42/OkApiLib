package pl.grushenko.okapi.cache;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class Geocache implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8469063162031684283L;
	private String code;
	private JsonObject data;
	
	public Geocache(String code, JsonObject obj)
	{
		this.code = code;
		this.data = obj;
		
		//imgs
		//logs
		//alt_wpts
		//trackables
		
		
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return data.get("name").asString();
	}
	
	public String getHint() {
		return data.get("hint2").asString();
	}
	

	public String getLocation() {
		return data.get("location").asString().replace('|', ' ');
	}

	public GeocacheType getType() {
		return GeocacheType.fromString(data.get("type").asString());
	}

	public GeocacheStatus getStatus() {
		return GeocacheStatus.fromString(data.get("status").asString());
	}

	public String getUrl() {
			return data.get("url").asString();
	}

	public User getOwner() {
		return new User((data.get("owner").asObject()));
	}

	public float getDistance() {
		return data.get("distance").asFloat();
	}

	public long getFounds() {
		return data.get("founds").asInt();
	}

	public long getNotfounds() {
		return data.get("notfounds").asInt();
	}

	public String getSize() {
		return data.get("size2").asString();
	}

	public float getDifficulty() {
		return data.get("difficulty").asFloat();
	}

	public float getTerrain() {
		return data.get("terrain").asFloat();
	}

	public int getRating() {
		return data.get("rating").asInt();
	}
	
	public int getRated() {
		return data.get("rating_votes").asInt();
	}

	public long getRecommendations() {
		return data.get("recommendations").asInt();
	}

	public boolean isReqPasswd() {
		return data.get("req_passwd").asBoolean();
	}

	public Date getLastFound() {
		 SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz");
		try {
			return df.parse(data.get("last_found").asString());
		} catch (ParseException e) {
			return null;
		}
	}

	public Date getDate_hidden() {
		 SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz");
		try {
			return df.parse(data.get("date_hidden").asString());
		} catch (ParseException e) {
			return null;
		}
	}

	public boolean isFound() {
		return data.get("is_found").asBoolean();
	}

	public boolean isNotFound() {
		return data.get("is_not_found").asBoolean();
	}

	public boolean isWatched() {
		return data.get("is_watched").asBoolean();
	}

	public boolean isIgnored() {
		return data.get("is_ignored").asBoolean();
	}

	public String getShortDescription() {
		return data.get("short_description").asString();
	}

	public String getDescription() {
		return data.get("description").asString();
	}

	public String getMyNotes() {
		return data.get("my_notes").asString();
	}

	public ArrayList<Image> getImages() {
		ArrayList<Image> images = new ArrayList<Image>();
		Iterator<JsonValue> it = data.get("images").asArray().iterator();
		while(it.hasNext())
			images.add(new Image(it.next().asObject()));
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

