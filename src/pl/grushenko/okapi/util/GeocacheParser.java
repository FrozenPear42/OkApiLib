package pl.grushenko.okapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eclipsesource.json.JsonObject;

import pl.grushenko.okapi.cache.GeocacheLog;
import pl.grushenko.okapi.cache.User;
import pl.grushenko.okapi.cache.GeocacheLog.LogType;

public class GeocacheParser {
	
	public static GeocacheLog parseLog(JsonObject obj) {
		
		String uuid = obj.get("uuid").asString();
		Date date;
		User user;
		LogType type;
		boolean isRecommended;
		String comment;
		
		try {
			date = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz").parse(obj.get("date").asString());
		} catch (ParseException e) {
			date = new Date();
			e.printStackTrace();
		}
		user = new User((obj.get("user").asObject()));
		type = LogType.fromString(obj.get("type").asString());
		isRecommended = false;//obj.get("was_recommended").asBoolean();
		comment = obj.get("comment").asString();
		
		return new GeocacheLog(uuid, date, user, type, comment, isRecommended);
	}
	
	
	
}
