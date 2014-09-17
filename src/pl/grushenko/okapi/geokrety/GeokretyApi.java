package pl.grushenko.okapi.geokrety;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.grushenko.okapi.geokrety.Geokret.GeokretState;
import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;

public class GeokretyApi {
	
	private static String secID = null;
	private static String appName = null;
	private static String appVersion = null;
	private static boolean isInitialized = false;
	
	public static void init(String secID, String appName, String appVersion) {
		GeokretyApi.secID = secID;
		GeokretyApi.appName = appName;
		GeokretyApi.appVersion = appVersion;
		GeokretyApi.isInitialized = true;
	}
	
	public static String getSecureId(String username, String password) throws Exception {
		URLParams params = new URLParams();
		params.appendParam("login", username);
		params.appendParam("password", password);
		return Request.postRequest("http://geokrety.org/api-login2secid.php", params);
	}
	
	public static Geokret getGeokretById(String referenceNumber) throws Exception {
		return getGeokretById(referenceNumberToId(referenceNumber));
	}
	
	public static Geokret getGeokretById(int id) throws Exception {
		URLParams params = new URLParams();
		params.appendParam("gkid", String.valueOf(id));
		String res = Request.getRequest("http://geokrety.org/export2.php", params);
		return GeokretyParser.parseGeokret(res);
	}	
	
	public static void logGeokret(String trackingCode, GeokretState logType, Date date, String comment, String loc, String waypoint) throws Exception {
		
		if(!isInitialized) 
			throw new Exception("GeokretyApi must be initialized!");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd|HH|mm");
		String[] data = format.format(date).split("|");
		
		
		URLParams params = new URLParams();
		params.appendParam("secid", secID);
		params.appendParam("nr", trackingCode);
		params.appendParam("formname", "ruchy");
		params.appendParam("logtype", String.valueOf(logType.getId()));
		params.appendParam("data", data[0]);
		params.appendParam("godzina", data[1]);
		params.appendParam("minuta", data[2]);
		params.appendParam("comment", comment);
		params.appendParam("app", appName);
		params.appendParam("app_ver", appVersion);
		
		params.appendParam("latlon", loc);
		params.appendParam("waypoint", waypoint);
		
		Request.postRequest("http://geokrety.org/ruchy.php", params);	
		
		//TODO: error handling	
	}
	
	public static String idToReferenceNumber(int id) {
		return "GK" + Integer.toHexString(id).toUpperCase().substring(0, 4);
	}
	public static int referenceNumberToId(String rfNumber) {
		return Integer.parseInt(rfNumber.substring(2), 16);
	}
	
	
}
