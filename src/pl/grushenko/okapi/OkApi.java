package pl.grushenko.okapi;

import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;

public class OkApi {

	public static void main(String[] args) {
		try {
			
			
			new OAuth(new OAuthToken("H2XVcvwueYwEKtwEwB6E",
					"s8a9nuWRJrThhG4k3qD8eZCQhYkErg9GfaGuuua4"), "pl")
					.requestToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
