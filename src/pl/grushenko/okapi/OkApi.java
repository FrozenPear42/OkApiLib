package pl.grushenko.okapi;

import pl.grushenko.okapi.oauth.OAuth;
import pl.grushenko.okapi.oauth.OAuthToken;

public class OkApi {

	public static void main(String[] args) {
		try {

			OAuth o = new OAuth(new OAuthToken("H2XVcvwueYwEKtwEwB6E",
					"s8a9nuWRJrThhG4k3qD8eZCQhYkErg9GfaGuuua4"), "pl");

			OAuthToken token = o.requestToken();
			o.authorizeToken(token);

			byte[] pin = new byte[8];
			
			System.in.read(pin);
			
			OAuthToken acc = o.getAccessToken(token, new String(pin, "utf-8"));
			
			System.out.println(acc.getKey());
			System.out.println(acc.getSecret());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
