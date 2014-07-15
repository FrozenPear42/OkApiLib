package pl.grushenko.okapi.net;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class URLParams {
	
	private TreeMap<String, String> params;
	
	public URLParams() {
		this.params = new TreeMap<String, String>();
	}
	
	public void appendParam(String key, String value) {
		this.params.put(key, value);
	}
	
	public String getParam(String key)
	{
		return this.params.get(key);
	}
	
	public String getParamString(){
		StringBuilder sb = new StringBuilder();
		//sb.append('?');
		
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
	
	public static URLParams parseParamsString(String params) {
		URLParams res;
		res = new URLParams();
		
		return res;
	}
	
}
