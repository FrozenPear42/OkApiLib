package pl.grushenko.okapi.oauth;

import java.net.URL;
import java.util.Date;

import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;

public class OAuth {
	
	public static OAuthToken requestToken(OAuthToken consumerToken, String lang, String callback) throws Exception {
		
		String func = "http://opencaching." + lang + "/okapi/services/oauth/request_token";
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("oauth_consumer_key", consumerToken.getKey());
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", Integer.toHexString((int) new Date().getTime()));
		requestParams.appendParam("oauth_version", "1.0");
		if(callback != null)
			requestParams.appendParam("oauth_callback", callback);
		else
			requestParams.appendParam("oauth_callback", "oob");
		
		
		requestParams = OAuthUtils.signRequest(func, requestParams, consumerToken);
		
		URLParams res = URLParams.parseParamsString(Request.getRequest(func, requestParams));
		return new OAuthToken(res.getParam("oauth_token"), res.getParam("oauth_token_secret"));
	}

	public static String authorize(OAuthToken unauthorizedToken, String lang) throws Exception, AuthorizationException {
		String func = "http://opencaching." + lang + "/okapi/services/oauth/authorize";
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("interactivity", "minimal");
		requestParams.appendParam("oauth_token", unauthorizedToken.getKey());
		return func + "?" + requestParams.getParamString();
		
	}

	public static OAuthToken getAccessToken(OAuthToken authorizedToken, OAuthToken consumerToken, String lang, String PIN) throws Exception {
		
		String func = "http://opencaching." + lang + "/okapi/services/oauth/access_token";
		
		URLParams requestParams = new URLParams();
		requestParams.appendParam("oauth_verifier", PIN);
		
		URLParams res = URLParams.parseParamsString(Request.authorizedGetRequest(func, requestParams, consumerToken, authorizedToken));
		
		return new OAuthToken(res.getParam("oauth_token"), res.getParam("oauth_token_secret"));
	}
	
	
}
