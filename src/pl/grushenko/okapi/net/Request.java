package pl.grushenko.okapi.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import pl.grushenko.okapi.oauth.OAuthToken;
import pl.grushenko.okapi.oauth.OAuthUtils;

public class Request {

	public static String getRequest(String url, URLParams params) throws Exception {
		
		URL obj = new URL(url + "?" + params.getParamString());
		
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();           
		connection.setRequestMethod("GET"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		
		
		//Error buffer
		if(connection.getResponseCode() != 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
				sb.append(line);
			
			throw new ConnectException(String.valueOf(connection.getResponseCode()) + ": "+ sb.toString());
		
		}
			
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while((line = reader.readLine()) != null)
			sb.append(line);

		return sb.toString();
	}	
	
	public static String authorizedGetRequest(String url, URLParams requestParams, OAuthToken consumerToken, OAuthToken accessToken ) throws Exception {
		requestParams.appendParam("oauth_consumer_key", consumerToken.getKey());
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", Integer.toHexString((int) new Date().getTime()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_token", accessToken.getKey());
		
		requestParams = OAuthUtils.signRequest(url, requestParams, consumerToken, accessToken);
		return Request.getRequest(url, requestParams);
		
	}
	
	public static InputStream authorizedRawGetRequest(String url, URLParams requestParams, OAuthToken consumerToken, OAuthToken accessToken) throws Exception {
		requestParams.appendParam("oauth_consumer_key", consumerToken.getKey());
		requestParams.appendParam("oauth_signature_method", "HMAC-SHA1");
		requestParams.appendParam("oauth_timestamp", String.valueOf((new Date().getTime()/1000)));
		requestParams.appendParam("oauth_nonce", Integer.toHexString((int) new Date().getTime()));
		requestParams.appendParam("oauth_version", "1.0");
		requestParams.appendParam("oauth_token", accessToken.getKey());
		
		requestParams = OAuthUtils.signRequest(url, requestParams, consumerToken, accessToken);
		return Request.getRequestRaw(url, requestParams);
		
	}
	

	public static String postRequest(String url, URLParams params) throws Exception {
		
		URL obj = new URL(url);
		
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();           
		
		connection.setDoOutput(true);
		
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(params.getParamString());
		wr.flush();
		wr.close();
		
		//Error buffer
		if(connection.getResponseCode() != 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
				sb.append(line);
			
			throw new ConnectException(String.valueOf(connection.getResponseCode()) + ": "+ sb.toString());
		
		}
			
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while((line = reader.readLine()) != null)
			sb.append(line);

		return sb.toString();
	}	
	
	
	
	public static InputStream getRequestRaw(String url, URLParams params) throws Exception {
		
		URL obj = new URL(url + "?" + params.getParamString());
		
		
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();           
		connection.setRequestMethod("GET"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 

		if(connection.getResponseCode() != 200) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null)
				sb.append(line);
			
			throw new ConnectException(String.valueOf(connection.getResponseCode()) + ": " + sb.toString());
		}
		
		return connection.getInputStream();		
	}
	
	
	
	
}
