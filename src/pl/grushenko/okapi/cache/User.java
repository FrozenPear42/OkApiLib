package pl.grushenko.okapi.cache;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

public class User {
	private String uuid;
	private String username;
	private URL profileURL;
	private long cachesFound;
	private long cachesNotFound;
	private long cachesHidden;
	private long recomendationsGiven;
	
	public User(JSONObject obj){
		uuid = (String) obj.get("uuid");
		username = (String) obj.get("username");
		try {
			profileURL = new URL((String) obj.get("profile_url"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(obj.containsKey("caches_found"))
			cachesFound = (Long) obj.get("caches_found");
		if(obj.containsKey("caches_notfound"))
			cachesNotFound = (Long) obj.get("caches_notfound");
		if(obj.containsKey("caches_hidden"))
			cachesHidden = (Long) obj.get("caches_hidden");
		if(obj.containsKey("rcmds_given"))
			recomendationsGiven = (Long) obj.get("rcmds_given");
		
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
