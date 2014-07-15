package pl.grushenko.okapi.oauth;

public class AuthorizationException extends Exception {

	private static final long serialVersionUID = -4131916129683823979L;

	public AuthorizationException(String msg)
	{
		super("Authorization Exception: " + msg);
	}
	
}
