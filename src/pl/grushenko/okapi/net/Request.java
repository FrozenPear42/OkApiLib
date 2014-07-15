package pl.grushenko.okapi.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {
	
	public static URLParams postRequest(URL url, URLParams params) throws Exception {
 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();           
		connection.setDoOutput(true);
		connection.setDoInput(true);
		
		connection.setRequestMethod("POST"); 
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		connection.setRequestProperty("charset", "utf-8");
		connection.setRequestProperty("Content-Length", "" + Integer.toString(params.getParamString().length()));
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(params.getParamString());
		wr.flush();
		wr.close();

		System.out.println(url.getFile());
		System.out.println(params.getParamString());
		System.out.println(connection.getResponseCode());
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder sb = new StringBuilder();
		
		String line;
		while((line = reader.readLine()) != null)
			sb.append(line);
		
		System.out.println(sb.toString());
		
		return null;
	}
}
