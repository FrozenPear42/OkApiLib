package pl.grushenko.okapi.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class Request {
	@Deprecated
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
	
	public static URLParams getRequest(String url, URLParams params) throws Exception {
		
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

		if(connection.getResponseCode() == 200)
		{
			URLParams res = new URLParams();
			try {	
				res = URLParams.parseParamsString(sb.toString());
			} catch (ParseException e){
				System.out.println(sb.toString());
			}
		
		
		return res;
		}else{
			throw new Exception();
		}
	}	
	
	
}
