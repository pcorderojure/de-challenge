package com.walmart.org;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
public class Config
{
	public static String config = "";
	Properties configFile;
	InputStream in;
	FileOutputStream out;
	
	
	public Config() throws IOException
	{
		configFile = new java.util.Properties();
		config = "config//parameters.config";
		in = new FileInputStream(config);
		configFile.load(in);
	}
	
 
	public String getProperty(String key)
	{
		String value = this.configFile.getProperty(key);
		return value;
	}
}