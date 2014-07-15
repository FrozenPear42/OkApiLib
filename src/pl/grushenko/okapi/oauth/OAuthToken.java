package pl.grushenko.okapi.oauth;

public class OAuthToken {
	private String key;
	private String secret;
	
	public OAuthToken(String pKey, String pSecret) {
		this.key = pKey;
		this.secret = pSecret;	
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getSecret() {
		return this.secret;
	}
	
}
