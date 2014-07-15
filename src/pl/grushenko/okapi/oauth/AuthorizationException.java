package pl.grushenko.okapi.oauth;

public class AuthorizationException extends Exception {

	public AuthorizationException(String msg)
	{
		super("Authorization Exception: " + msg);
	}
	
}
