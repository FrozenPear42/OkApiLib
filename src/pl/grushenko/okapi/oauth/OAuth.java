package pl.grushenko.okapi.oauth;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;

public class OAuth {

	private String consumerKey;
	private String consumerSecret;
	private String host;
	private Random rand;
	
	public OAuth(OAuthToken consumerToken, String host) {
		this.consumerKey = consumerToken.getKey();
		this.consumerSecret = consumerToken.getSecret();
		this.host = "http://opencaching." + host + "/okapi";
		this.rand = new Random();
	}

	public OAuthToken requestToken() throws Exception {
		URLParams requestParams;
		requestParams = new URLParams();
		requestParams.appendParam("oauth_consumer_key", this.consumerKey);
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000000)));
		requestParams.appendParam("oauth_nonce", String.valueOf(rand.nextInt()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_callback", "oob");
	
		String signingBase;
		signingBase = "POST&" + URLEncoder.encode(this.host, "utf-8") + "&"
				+ URLEncoder.encode(requestParams.getParamString(), "utf-8");

		String signature = signSHA1(consumerKey + "&" + consumerSecret, signingBase);
		
		requestParams.appendParam("oauth_signature", signature);
		
		Request.postRequest(new URL(this.host + "/services/oauth/request_token"), requestParams);
		
		
		return new OAuthToken(null, null);
	}

	public OAuthToken authorizeToken(OAuthToken unauthorizedToken) throws IOException, AuthorizationException {

		return new OAuthToken(null, null);
	}

	public OAuthToken getAccessToken(OAuthToken authorizedToken) throws IOException {

		return new OAuthToken(null, null);
	}

	private String signSHA1(String key, String text) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(signingKey);
		byte[] raw = mac.doFinal(text.getBytes());
		return DatatypeConverter.printBase64Binary(raw);
	}
	
}
