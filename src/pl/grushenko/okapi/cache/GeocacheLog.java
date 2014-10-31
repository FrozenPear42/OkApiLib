package pl.grushenko.okapi.cache;

import java.util.Date;

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
		
		public static LogType fromString(String s) {
			for(LogType v : values())
				if(v.data.equals(s))
					return v;
			return COMMENT;
		}
	}

	private String uuid;
	private Date date;
	private LogType type;
	private User user;
	private Boolean isRecommended;
	private String comment;

	public GeocacheLog(String uuid, Date date, User user, LogType type, String comment, boolean isRecommended) {
		this.uuid = uuid;
		this.date = date;
		this.user = user;
		this.type = type;
		this.isRecommended = isRecommended;
		this.comment = comment;
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
