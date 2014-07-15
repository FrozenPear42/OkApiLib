package pl.grushenko.okapi.oauth;

import java.io.IOException;
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
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", String.valueOf(rand.nextInt()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_callback", "oob");

		
		String signingBase;
		signingBase = "GET&" + URLEncoder.encode(this.host + "/services/oauth/request_token", "utf-8") + "&"
				+ URLEncoder.encode(requestParams.getParamString(), "utf-8");

		String signature = signSHA1(URLEncoder.encode(consumerSecret, "utf-8") + "&" , signingBase);
		
		requestParams.appendParam("oauth_signature", URLEncoder.encode(signature, "utf-8"));
		
		System.out.println(signingBase);
	
		URLParams res = Request.getRequest(this.host + "/services/oauth/request_token", requestParams);
	
		return new OAuthToken(res.getParam("oauth_token"), res.getParam("oauth_token_secret"));
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
