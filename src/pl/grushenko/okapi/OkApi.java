package pl.grushenko.okapi;

import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;




import pl.grushenko.okapi.cache.Geocache;
import pl.grushenko.okapi.cache.Log.LogType;
import pl.grushenko.okapi.cache.User;
import pl.grushenko.okapi.geokrety.GeokretyApi;
import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;
import pl.grushenko.okapi.util.ISO8601DateParser;

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
	

	public void submitLog(String cacheCode, LogType type, String comment, Date date, int rating, boolean recomend, String password, boolean needsMaintenance) throws Exception {
		URLParams requestParams = new URLParams();
		requestParams.appendParam("cache_code", cacheCode);
		requestParams.appendParam("logtype", type.getData());
		requestParams.appendParam("comment", comment);
		requestParams.appendParam("when", ISO8601DateParser.toString(date));
		if(password != null)
			requestParams.appendParam("password", password);
		if(rating != -1)
			requestParams.appendParam("rating", String.valueOf(rating));
		
		requestParams.appendParam("needs_maintenance", String.valueOf(needsMaintenance));
		requestParams.appendParam("recommend", String.valueOf(recomend));
		
		
		String res = auth.authorizedGetRequest(host + "/services/logs/submit", requestParams, accessToken);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(res);
		if(!(Boolean)obj.get("success"))
			throw new Exception((String)obj.get("message"));
		
	}
	
	public void submitLog(String cacheCode, LogType type, String comment, Date date, int rating) throws Exception {
		submitLog(cacheCode, type, comment, date, rating, false, null, false);
	}
	
	public void submitLog(String cacheCode, LogType type, String comment, Date date) throws Exception {
		submitLog(cacheCode, type, comment, date, -1, false, null, false);
	}	
	
	
	
	public static void main(String[] args) {
		try {

			OkApi api = new OkApi(SensitiveData.consumer, SensitiveData.access, "pl");
			System.out.println(GeokretyApi.idToReferenceNumber(16233));
			System.out.println(GeokretyApi.referenceNumberToId("GK3F69"));
			GeokretyApi.getGeokretById("GK3F69");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
