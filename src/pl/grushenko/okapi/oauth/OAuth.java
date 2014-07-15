package pl.grushenko.okapi.oauth;

import java.awt.Desktop;
import java.net.URL;
import java.util.Date;
import java.util.Random;

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
		URLParams requestParams = new URLParams();
		requestParams.appendParam("oauth_consumer_key", this.consumerKey);
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", String.valueOf(rand.nextInt()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_callback", "oob");
		
		requestParams = OAuthUtils.signRequest(this.host + "/services/oauth/request_token", requestParams, new OAuthToken(consumerKey, consumerSecret));
		
		URLParams res = URLParams.parseParamsString(Request.getRequest(this.host + "/services/oauth/request_token", requestParams));
		return new OAuthToken(res.getParam("oauth_token"), res.getParam("oauth_token_secret"));
	}

	public String authorizeToken(OAuthToken unauthorizedToken) throws Exception, AuthorizationException {

		URLParams requestParams = new URLParams();
		requestParams.appendParam("interactivity", "minimal");
		requestParams.appendParam("oauth_token", unauthorizedToken.getKey());
		Desktop.getDesktop().browse(new URL(this.host + "/services/oauth/authorize?" + requestParams.getParamString()).toURI());
		
		return null;
	}

	public OAuthToken getAccessToken(OAuthToken authorizedToken, String PIN) throws Exception {
		URLParams requestParams = new URLParams();
		requestParams.appendParam("oauth_verifier", PIN);
		
		URLParams res = URLParams.parseParamsString(authorizedGetRequest(this.host + "/services/oauth/access_token", requestParams, authorizedToken));
		
		return new OAuthToken(res.getParam("oauth_token"), res.getParam("oauth_token_secret"));
	}

	public String authorizedGetRequest(String url, URLParams requestParams, OAuthToken accessToken) throws Exception {
		requestParams.appendParam("oauth_consumer_key", this.consumerKey);
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", String.valueOf(rand.nextInt()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_token", accessToken.getKey());
		
		requestParams = OAuthUtils.signRequest(url, requestParams, new OAuthToken(consumerKey, consumerSecret), accessToken);
		return Request.getRequest(url, requestParams);
		
	}
	
	
}
