package io.siv.support.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class Loader {
	private static final Map<String, Properties> map = new HashMap<String, Properties>();
	
	public static Properties propertiesForFileKey(String key) {
		return propertiesForFileKey(Loader.class, key);
	}
	
	public static Properties propertiesForFileKey(Class<?> clazz, String key) {
		if (map.containsKey(key)) return map.get(key);
		
		Properties value = load(clazz, key);
		map.put(key, value);
		return value;
	}
	
	private static Properties load(Class<?> clazz, String key) {
		 Properties p = new Properties();
		 InputStream in = clazz.getResourceAsStream("/" + key + ".properties");
		 
		 try {
			 p.load(in);
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
		 
		 return p;
	}
}
