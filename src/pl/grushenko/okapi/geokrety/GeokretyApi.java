package pl.grushenko.okapi.geokrety;

import java.text.SimpleDateFormat;
import java.util.Date;

import pl.grushenko.okapi.SensitiveData;
import pl.grushenko.okapi.geokrety.Geokret.GeokretState;
import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.util.Location;

public class GeokretyApi {
	public static String getSecureId(String username, String password) throws Exception {
		URLParams params = new URLParams();
		params.appendParam("login", username);
		params.appendParam("password", password);
		return Request.postRequest("http://geokrety.org/api-login2secid.php", params);
	}
	
	public static Geokret getGeokretById(String referenceNumber) throws Exception {
		URLParams params = new URLParams();
		params.appendParam("gkid", String.valueOf(referenceNumberToId(referenceNumber)));
		String res = Request.getRequest("http://geokrety.org/export2.php", params);
		return GeokretyParser.parseGeokret(res);
	}
	
	public static Geokret getGeokretById(int id) throws Exception {
		return getGeokretById(idToReferenceNumber(id));
	}	
	
	public static void logGeokret(String secureId, String trackingCode, GeokretState logType, Date date, String comment, Location loc, String waypoint) throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd|HH|mm");
		String[] data = format.format(date).split("|");
		
		
		URLParams params = new URLParams();
		params.appendParam("secid", secureId);
		params.appendParam("nr", trackingCode);
		params.appendParam("formname", "ruchy");
		params.appendParam("data", data[0]);
		params.appendParam("godzina", data[1]);
		params.appendParam("minuta", data[2]);
		params.appendParam("comment", comment);
		params.appendParam("app", SensitiveData.appName);
		params.appendParam("app_ver", SensitiveData.appVersion);
		
		params.appendParam("latlon", String.valueOf(loc.getLat()) + " " + String.valueOf(loc.getLon()));
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
