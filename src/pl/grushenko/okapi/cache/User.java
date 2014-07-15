package pl.grushenko.okapi.cache;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONObject;

public class User {
	private int uuid;
	private String username;
	private URL profileURL;
	private int cachesFound;
	private int cachesNotFound;
	private int cachesHidden;
	private int recomendationsGiven;
	
	public User(JSONObject obj){
		uuid = (Integer) obj.get("uuid");
		username = (String) obj.get("username");
		try {
			profileURL = new URL((String) obj.get("profile_url"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cachesFound = (Integer) obj.get("caches_found");
		cachesNotFound = (Integer) obj.get("caches_notfound");
		cachesHidden = (Integer) obj.get("caches_hidden");
		recomendationsGiven = (Integer) obj.get("rcmds_given");
		
	}

	public int getUuid() {
		return uuid;
	}

	public String getUsername() {
		return username;
	}

	public URL getProfileURL() {
		return profileURL;
	}

	public int getCachesFound() {
		return cachesFound;
	}

	public int getCachesNotFound() {
		return cachesNotFound;
	}

	public int getCachesHidden() {
		return cachesHidden;
	}

	public int getRecomendationsGiven() {
		return recomendationsGiven;
	}
	
	
	
	
}
