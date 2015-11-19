package com.sharpower.utils;

import java.util.HashMap;
import java.util.Map;

public class AppDBpropertis {
	private Map<String, String> properties = new HashMap<>();
	
	private AppDBpropertis(){}
	
	private static AppDBpropertis instance = new AppDBpropertis();
	
	public static AppDBpropertis getInstance() {
		return instance;
	}
	
	public void addProperty(String propertyName, String propertyValue){
		properties.put(propertyName, propertyValue);
	}
	
	public String getProperty(String propertyName){
		return properties.get(propertyName);
	}

}