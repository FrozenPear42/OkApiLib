package pl.grushenko.okapi.oauth;

import java.net.URLEncoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import pl.grushenko.okapi.net.URLParams;

public class OAuthUtils {
	public static URLParams signRequest(String url, URLParams requestParams,OAuthToken consumerToken, OAuthToken token){
		
		String signingBase;
		try {
		signingBase = "GET&" + URLEncoder.encode(url, "utf-8") + "&"
				+ URLEncoder.encode(requestParams.getParamString(), "utf-8");

		String signature = signSHA1(URLEncoder.encode(consumerToken.getSecret(), "utf-8") + "&" + URLEncoder.encode(token.getSecret(), "utf-8"), signingBase);
		
		requestParams.appendParam("oauth_signature", URLEncoder.encode(signature, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return requestParams;
	
	}

	public static URLParams signRequest(String url, URLParams requestParams,OAuthToken consumerToken) {
		
		String signingBase;
		
		try {
		signingBase = "GET&" + URLEncoder.encode(url, "utf-8") + "&"
				+ URLEncoder.encode(requestParams.getParamString(), "utf-8");

		String signature = signSHA1(URLEncoder.encode(consumerToken.getSecret(), "utf-8") + "&", signingBase);
		
		requestParams.appendParam("oauth_signature", URLEncoder.encode(signature, "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return requestParams;
	
	}

	
	
	private static String signSHA1(String key, String text) {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		try{
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] raw = mac.doFinal(text.getBytes());
			return DatatypeConverter.printBase64Binary(raw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
