package pl.grushenko.okapi.cache;

import java.net.MalformedURLException;
import java.net.URL;

import com.eclipsesource.json.JsonObject;

public class User {
	private String uuid;
	private String username;
	private URL profileURL;
	private long cachesFound;
	private long cachesNotFound;
	private long cachesHidden;
	private long recomendationsGiven;
	
	public User(JsonObject obj){
		uuid = obj.get("uuid").asString();
		username = obj.get("username").asString();
		try {
			profileURL = new URL(obj.get("profile_url").asString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(obj.get("caches_found").isNull())
			cachesFound = obj.get("caches_found").asLong();
		if(obj.get("caches_notfound").isNull())
			cachesNotFound = obj.get("caches_notfound").asLong();
		if(obj.get("caches_hidden").isNull())
			cachesHidden =  obj.get("caches_hidden").asLong();
		if(obj.get("rcmds_given").isNull())
			recomendationsGiven = obj.get("rcmds_given").asLong();
		
	}

	public String getUuid() {
		return uuid;
	}

	public String getUsername() {
		return username;
	}

	public URL getProfileURL() {
		return profileURL;
	}

	public long getCachesFound() {
		return cachesFound;
	}

	public long getCachesNotFound() {
		return cachesNotFound;
	}

	public long getCachesHidden() {
		return cachesHidden;
	}

	public long getRecomendationsGiven() {
		return recomendationsGiven;
	}
	
	
	
	
}
