package pl.grushenko.okapi.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eclipsesource.json.JsonObject;

public class GeocacheLog {
	
	public enum LogType {
		FOUND_IT("Found it", 0),
		DIDNT_FOUND_IT("Didn't find it", 1),
		COMMENT("Comment", 2),
		NEEDS_MAINTENANCE("Needs maintenance", 3),
		MAINTENANCE_PERFORMED("Maintenance performed", 4),
		TEMPORARILY_UNAVAILABLE("Temporarily unavailable", 5),
		READY_TO_SEARCH("Ready to search", 6),
		ARCHIVED("Archived", 7),
		LOCKED("Locked", 8),
		MOVED("Moved", 9);
		
		private final String data;
		private final int id;
		LogType(String s, int id) {
			this.data = s;
			this.id = id;
		}
		
		public String getData(){
			return this.data;
		}
		
		public int getId() {
			return this.id;
		}
	}

	private String uuid;
	private Date date;
	private LogType type;
	private User user;
	private Boolean isRecommended;
	private String comment;

	public GeocacheLog(JsonObject obj) {
		this.uuid = obj.get("uuid").asString();
		
		try {
			this.date = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz").parse(obj.get("date").asString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.user = new User((obj.get("user").asObject()));
		this.type = LogType.valueOf(obj.get("type").asString());
		this.isRecommended = obj.get("was_recommended").asBoolean();
		this.comment = obj.get("comment").asString();
		//images
	}
	
	public String getUuid() {
		return uuid;
	}

	public Date getDate() {
		return date;
	}

	public LogType getType() {
		return type;
	}

	public User getUser() {
		return user;
	}

	public Boolean getIsRecommended() {
		return isRecommended;
	}

	public String getComment() {
		return comment;
	}

	
}
