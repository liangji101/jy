package com.renren.ntc.video.utils;

import java.io.*;
import java.util.Properties;


public class SearchConfig {
	 private Properties searchPro;
	 private String searchFile = "search.properties";
	 private String SQL = "sql";
	 private String CONDITION = "search.condition";
	 private String INDEX = "index.path";
	 
	 
	 public SearchConfig(){
	     initSearch();
	 }
	 
	 public void initSearch() {
		try {
			searchPro = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(searchFile));
			searchPro.load(in);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	 public String getSql(){
	  return searchPro.getProperty(SQL, "");
	 }
	 
	 public String getCondition(){
	  return searchPro.getProperty(CONDITION, "");
	 }
	 
	 public File getIndexPath(){
	  String path = searchPro.getProperty(INDEX, "");
	  File file = new File(path);
	  if (!file.exists()) {
	   file.mkdir();
	  }
	  return file;
	 }
	 
	 public long getPeriod(){
	  String period = searchPro.getProperty("period", "0");
	  return Integer.valueOf(period);
	 }
	 
	 public String getUpdateField(){
	  return searchPro.getProperty("update.field", "");
	 }
	 
	 public String getUpdateValue(){
	  return searchPro.getProperty("update.value", "");
	 }
	 
}
