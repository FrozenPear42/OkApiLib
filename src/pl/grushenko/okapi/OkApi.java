package pl.grushenko.okapi;

import java.net.URL;

import pl.grushenko.okapi.net.Request;
import pl.grushenko.okapi.net.URLParams;
import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;

public class OkApi {

	public static void main(String[] args) {
		try {
			
			
			new OAuth(new OAuthToken("9JeHfmstAxeSrkucDtA9",
					"cTn4g2THMxwM7AkcMCwcYAUeKdhdxHdzsstzhc8V"), "pl")
					.requestToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
