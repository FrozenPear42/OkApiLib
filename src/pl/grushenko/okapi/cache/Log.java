package pl.grushenko.okapi.cache;

import java.text.ParseException;
import java.util.Date;

import org.json.simple.JSONObject;

import pl.grushenko.okapi.util.ISO8601DateParser;

public class Log {
	
	public enum LogType {
		FOUND_IT("Found it"),
		DIDNT_FOUND_IT("Didn't find it"),
		COMMENT("Comment"),
		TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
		READY_TO_SEARCH("Ready to search"),
		ARCHIVED("Archived"),
		LOCKED("Locked"),
		NEEDS_MAINTENANCE("Needs maintenance"),
		MAINTENANCE_PERFORMED("Maintenance performed"),
		MOVED("Moved");
		
		private final String data;
		LogType(String s) {
			this.data = s;
		}
		
		public String getData(){
			return this.data;
		}
	}

	private String uuid;
	private Date date;
	private LogType type;
	private User user;
	private Boolean isRecommended;
	private String comment;

	public Log(JSONObject obj) {
		this.uuid = (String) obj.get("uuid");
		
		try {
			this.date = ISO8601DateParser.parse((String) obj.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.user = new User((JSONObject)obj.get("user"));
		this.type = LogType.valueOf((String) obj.get("type"));
		this.isRecommended = (Boolean) obj.get("was_recommended");
		this.comment = (String) obj.get("comment");
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
