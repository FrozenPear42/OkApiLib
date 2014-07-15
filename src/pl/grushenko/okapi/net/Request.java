package pl.grushenko.okapi.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

	public static String getRequest(String url, URLParams params) throws Exception {
		
		URL obj = new URL(url + "?" + params.getParamString());
		
		
		HttpURLConnection connection = (HttpURLConnection) obj.openConnection();           
		
		connection.setRequestMethod("GET"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		
		InputStream is;
		if(connection.getResponseCode()== 200)
			is = connection.getInputStream();
		else
			is = connection.getErrorStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while((line = reader.readLine()) != null)
			sb.append(line);

		return sb.toString();
	}	
	
	
	
}
