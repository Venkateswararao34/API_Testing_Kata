package com.booking.api.restassured.config;

import java.io.File;
import java.util.Properties;
import java.util.HashMap;
import java.io.InputStream;
import java.io.FileInputStream;

public class LoadEnvironmentProperties {
	
	public static HashMap<String,String> EnvironmentMap	=	new HashMap<String,String>();
	
	/*
	 * This method loads and return the environment properties
	 * Example: SIT/SIT/UAT etc
	 * 
	 */
	
	public HashMap<String,String> loadPropertiesFromFile() {
		try {
			Config config								=	new Config();
			Properties properties						=	new Properties();
			
			String fileLocation							=	config.getWorkingDirectory()+File.separator+config.getPropertyFilePath()+File.separator+config.getExecutionEnvironment()+".properties";
			
			InputStream inputStream						=	null;
			
			properties.load(new FileInputStream(fileLocation));
			
			for(String key : properties.stringPropertyNames()) {
				EnvironmentMap.put(key, properties.getProperty(key));
			}
			
			
		}catch(NumberFormatException nfe) {
			nfe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return EnvironmentMap;
	}
	
}
