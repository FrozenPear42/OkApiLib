package pl.grushenko.okapi;

import java.awt.Desktop;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pl.grushenko.okapi.cache.Geocache;
import pl.grushenko.okapi.cache.User;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;

public class OkApi {


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
	
	
	public User getUser(String username) throws Exception {
		URLParams requestParams = new URLParams();
		requestParams.appendParam("username", username);
		requestParams.appendParam("fields", "uuid|profile_url|caches_found|caches_notfound|caches_hidden|rcmds_given");
		
		String res = auth.authorizedGetRequest(host + "/services/users/by_username", requestParams, accessToken);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(res);
		
		return new User(obj);
	}
	
	public Geocache getCache(String code) throws Exception {
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("cache_code", code);
		//requestParams.appendParam("my_location", loc);
		requestParams.appendParam("fields", "name|short_description|location|type|status|url|owner|is_found|is_not_found|is_watched|is_ignored|founds|notfounds|size2|difficulty|terrain|rating|description|hint2|recommendations|req_passwd|images|latest_logs|my_notes|trackables|alt_wpts|last_found|date_hidden");
		
		String res = auth.authorizedGetRequest(host + "/services/caches/geocache", requestParams, accessToken);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(res);
		return new Geocache(code, obj);
		
		
	}
	
	
	
	public static void main(String[] args) {
		try {

			OkApi api = new OkApi(SensitiveData.consumer, SensitiveData.access, "pl");
			api.getCache("OP0001");
			//Desktop.getDesktop().browse(api.getUser("Grushenko").getProfileURL().toURI());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
