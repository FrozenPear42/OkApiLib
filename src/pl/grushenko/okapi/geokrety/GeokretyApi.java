package pl.grushenko.okapi.geokrety;

import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;

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
	
	public static String idToReferenceNumber(int id) {
		return "GK" + Integer.toHexString(id).toUpperCase().substring(0, 4);
	}
	public static int referenceNumberToId(String rfNumber) {
		return Integer.parseInt(rfNumber.substring(2), 16);
	}
	
	
}
