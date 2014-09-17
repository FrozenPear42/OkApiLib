package pl.grushenko.okapi.cache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eclipsesource.json.JsonObject;

public class Log {
	
	public enum LogType {
		FOUND_IT("Found it"),
		DIDNT_FOUND_IT("Didn't find it"),
		COMMENT("Comment"),
		NEEDS_MAINTENANCE("Needs maintenance"),
		MAINTENANCE_PERFORMED("Maintenance performed"),
		TEMPORARILY_UNAVAILABLE("Temporarily unavailable"),
		READY_TO_SEARCH("Ready to search"),
		ARCHIVED("Archived"),
		LOCKED("Locked"),
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

	public Log(JsonObject obj) {
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
