package pl.grushenko.okapi.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

	public static String getRequest(String url, URLParams params) throws Exception {
		
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
			
			throw new ConnectException(String.valueOf(connection.getResponseCode()) + ": " + sb.toString());} 
		return connection.getInputStream();		
	}	
	
}
