package pl.grushenko.okapi;

import java.awt.Desktop;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pl.grushenko.okapi.cache.User;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;

public class OkApi {
	
	public class NoSuchUserException extends Exception {
		private static final long serialVersionUID = 2611430294497736365L;
	}


	private OAuth auth;
	private OAuthToken accessToken;
	private String host;
	
	public OkApi(OAuthToken consumerToken, String lang) {
		auth = new OAuth(consumerToken, lang);
		host = "http://opencaching." + lang + "/okapi";
		//getAuthToken
	}
	
	public OkApi(OAuthToken consumerToken, OAuthToken accessToken, String lang) {
		this(consumerToken, lang);
		this.accessToken = accessToken;
	}
	
	
	public User getUser(String username) throws NoSuchUserException {
		URLParams requestParams = new URLParams();
		requestParams.appendParam("username", username);
		requestParams.appendParam("fields", "uuid|profile_url|caches_found|caches_notfound|caches|hidden|rcdms_given");
		try {
			String res = auth.authorizedGetRequest(host + "/services/users/by_name", requestParams, accessToken);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(res);
			
			return new User(obj);
		} catch (Exception e) {
			throw new NoSuchUserException();
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		try {

			OkApi api = new OkApi(new OAuthToken("H2XVcvwueYwEKtwEwB6E",
					"s8a9nuWRJrThhG4k3qD8eZCQhYkErg9GfaGuuua4"), new OAuthToken("vmEJ6BHtdQw72qX7gsHC", "TT52PJRVw4tQeWqbqXuuEPrw4etbxZDfJz7q8Pau"), "pl");
			Desktop.getDesktop().browse(api.getUser("Grushenko").getProfileURL().toURI());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
