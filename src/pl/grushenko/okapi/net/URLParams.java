package pl.grushenko.okapi.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class URLParams {
	//TODO: PERCENT ENCODING
	private TreeMap<String, String> params;
	
	public URLParams() {
		this.params = new TreeMap<String, String>();
	}
	
	public void appendParam(String key, String value) {
		try {
			this.params.put(URLEncoder.encode(key, "utf-8"), URLEncoder.encode(value, "utf-8").replace("+", "%20"));
		} catch (UnsupportedEncodingException e) {
			//SHOULD NEVER HAPPEN
			e.printStackTrace();
		}
	}
	
	
	public String getParam(String key)
	{
		try {
			return this.params.get(URLEncoder.encode(key, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			//SHOULD NEVER HAPPEN
			e.printStackTrace();
		}
		return null;
	}
	
	public String getParamString(){
		StringBuilder sb = new StringBuilder();
		
		Iterator<Entry<String,String>> it = params.entrySet().iterator();
		while(it.hasNext())
		{
			Entry<String,String> en = it.next();
			sb.append(en.getKey());
			sb.append('=');
			sb.append(en.getValue());
			if(it.hasNext())
				sb.append('&');
		}
		return sb.toString();
	}
	
	public static URLParams parseParamsString(String params) throws ParseException{
		URLParams res;
		res = new URLParams();
		
		if (params.charAt(0) == '?')
			params = params.substring(1);
		
		String[] entries = params.split("&");
		
		for(String entry : entries) {
			String[] pair = entry.split("=");
			if(pair == null || pair.length < 2)
				throw new ParseException("Parse error", 0);
			else
				res.appendParam(pair[0], pair[1]);
		}
		
		return res;
	}
	
}
