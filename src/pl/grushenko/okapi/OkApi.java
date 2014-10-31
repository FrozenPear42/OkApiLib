package pl.grushenko.okapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import pl.grushenko.okapi.cache.Geocache;
import pl.grushenko.okapi.cache.Geocache.GeocacheStatus;
import pl.grushenko.okapi.cache.GeocacheLog;
import pl.grushenko.okapi.cache.GeocacheLog.LogType;
import pl.grushenko.okapi.cache.User;
import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.oauth.OAuthToken;
import pl.grushenko.okapi.util.GeocacheParser;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class OkApi {
	
	  private static OAuthToken consumerToken = null;
	  private static OAuthToken accessToken = null;
	  private static boolean isInitialized = false;
	  private static String host = null;
	  
	
	public static void init(OAuthToken consumer, OAuthToken access, String lang) {
		consumerToken = consumer;
		accessToken = access;
		host = "http://opencaching." + lang + "/okapi";
		isInitialized = true;
	}
	  
	public static User getUser(String username) throws Exception {
		
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("username", username);
		requestParams.appendParam("fields", "uuid|profile_url|caches_found|caches_notfound|caches_hidden|rcmds_given");
		
		String res = Request.L1AuthGetRequest(host + "/services/users/by_username", requestParams, consumerToken);
		
		JsonObject obj = JsonObject.readFrom(res);
		
		return new User(obj);
	}
	
public static User getActiveUser() throws Exception {
		
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("fields", "uuid|username|profile_url|caches_found|caches_notfound|caches_hidden|rcmds_given");
		String res = Request.L3AuthGetRequest(host + "/services/users/user", requestParams, consumerToken,accessToken);
		
		JsonObject obj = JsonObject.readFrom(res);
		
		return new User(obj);
	}
	
	
	

	
	public static Geocache getCacheDetails(String code) throws Exception {
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		String flags = "location|type|status|url|owner|founds|notfounds|size2|difficulty|terrain|rating|rating_votes|description|hint2|recommendations|req_passwd|alt_wpts|last_found|date_hidden";
		String res = Request.L1AuthGetRequest(host + "/services/caches/geocache", new URLParams().appendParam("cache_code", code).appendParam("fields", flags), consumerToken);
		JsonObject obj = JsonObject.readFrom(res);
		return new Geocache(code, obj);
	
	}
	public static ArrayList<GeocacheLog> getCacheLogs(String code) throws Exception {
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		String flags =  "latest_logs";
		String res = Request.L1AuthGetRequest(host + "/services/caches/geocache", new URLParams().appendParam("cache_code", code).appendParam("fields", flags), consumerToken);
		JsonObject obj = JsonObject.readFrom(res);
		Iterator<JsonValue> it = obj.get("latest_logs").asArray().iterator();
		ArrayList<GeocacheLog> logs = new ArrayList<GeocacheLog>();
		while(it.hasNext()) {
			logs.add(GeocacheParser.parseLog(it.next().asObject()));
		}
		return logs;
	}

	public static Geocache getCacheBaseData(String code) throws Exception {
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		String flags = "name|short_description|location|type|status|is_found|is_not_found|is_watched|is_ignored";
		String res = Request.L3AuthGetRequest(host + "/services/caches/geocache", new URLParams().appendParam("cache_code", code).appendParam("fields", flags), consumerToken, accessToken);
		JsonObject obj = JsonObject.readFrom(res);
		return new Geocache(code, obj);
	}
	
	
	
	public static Geocache getCacheImages(String code) throws Exception {
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		String flags = "images";
		String res = Request.L1AuthGetRequest(host + "/services/caches/geocache", new URLParams().appendParam("cache_code", code).appendParam("fields", flags), consumerToken);
		JsonObject obj = JsonObject.readFrom(res);
		return new Geocache(code, obj);
	}
	
	
	
	public static String[] search(String query) throws Exception {
	
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		URLParams params = new URLParams();
		params.appendParam("name", "*" + query + "*");
		params.appendParam("status", GeocacheStatus.AVAILABLE.getData()+"|"+GeocacheStatus.ARCHIVED.getData()+"|"+GeocacheStatus.TEMPORARILY_UNAVAILABLE.getData());
		String res = Request.L1AuthGetRequest(host + "/services/caches/search/all", params, consumerToken);
		JsonObject obj = JsonObject.readFrom(res);
		JsonArray result = obj.get("results").asArray();
		
		String[] codes = new String[result.size()];
		
		for(int i = 0; i < result.size(); i++)
			codes[i] = result.get(i).asString();
		
		return codes;
	}
	
	public static String[] searchBox(String box) throws Exception {
		
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		URLParams params = new URLParams();
		params.appendParam("bbox", box);
		params.appendParam("limit", "500");
		String res = Request.L1AuthGetRequest(host + "/services/caches/search/bbox", params, consumerToken);
		JsonObject obj = JsonObject.readFrom(res);
		JsonArray result = obj.get("results").asArray();
		
		String[] codes = new String[result.size()];
		
		for(int i = 0; i < result.size(); i++)
			codes[i] = result.get(i).asString();
		
		return codes;
	}
	
	
	

	public static boolean submitLog(String cacheCode, LogType type, String comment, Date date, int rating, boolean recomend, String password, boolean needsMaintenance) throws Exception {
		
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");

		URLParams requestParams = new URLParams();
		requestParams.appendParam("cache_code", cacheCode);
		requestParams.appendParam("logtype", type.getData());
		requestParams.appendParam("comment", comment);
		requestParams.appendParam("when", new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:sszzzzz").format(date));
		if(password != null)
			requestParams.appendParam("password", password);
		if(rating != -1)
			requestParams.appendParam("rating", String.valueOf(rating));
		
		requestParams.appendParam("needs_maintenance", String.valueOf(needsMaintenance));
		requestParams.appendParam("recommend", String.valueOf(recomend));
		
		
		String res = Request.L3AuthGetRequest(host + "/services/logs/submit", requestParams, consumerToken, accessToken);
		
		JsonObject obj = JsonObject.readFrom(res);
		if(!obj.get("success").asBoolean())
			throw new Exception(obj.get("message").asString());
		
		return obj.get("success").asBoolean();
	}
	
	public static void submitLog(String cacheCode, LogType type, String comment, Date date, int rating) throws Exception {
		submitLog(cacheCode, type, comment, date, rating, false, null, false);
	}
	
	public static void submitLog(String cacheCode, LogType type, String comment, Date date) throws Exception {
		submitLog(cacheCode, type, comment, date, -1, false, null, false);
	}

	public static String[] getWatched() throws Exception {
	
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		URLParams params = new URLParams();
		params.appendParam("watched_only", "true");
		params.appendParam("status", GeocacheStatus.AVAILABLE.getData()+"|"+GeocacheStatus.ARCHIVED.getData()+"|"+GeocacheStatus.TEMPORARILY_UNAVAILABLE.getData());
		String res = Request.L3AuthGetRequest(host + "/services/caches/search/all", params, consumerToken, accessToken);
		JsonObject obj = JsonObject.readFrom(res);
		JsonArray result = obj.get("results").asArray();
		
		String[] codes = new String[result.size()];
		
		for(int i = 0; i < result.size(); i++)
			codes[i] = result.get(i).asString();
		
		return codes;
	}	

	public static String[] getMyCaches() throws Exception {
		
		if(!isInitialized) throw new Exception("OkApi must be initialized first!");
		
		User user = getActiveUser();
		
		URLParams params = new URLParams();
		params.appendParam("owner_uuid", user.getUuid());
		params.appendParam("status", GeocacheStatus.AVAILABLE.getData()+"|"+GeocacheStatus.ARCHIVED.getData()+"|"+GeocacheStatus.TEMPORARILY_UNAVAILABLE.getData());
		String res = Request.L3AuthGetRequest(host + "/services/caches/search/all", params, consumerToken, accessToken);
		JsonObject obj = JsonObject.readFrom(res);
		JsonArray result = obj.get("results").asArray();
		
		String[] codes = new String[result.size()];
		
		for(int i = 0; i < result.size(); i++)
			codes[i] = result.get(i).asString();
		
		return codes;
	}	
	
	
		
	
}
