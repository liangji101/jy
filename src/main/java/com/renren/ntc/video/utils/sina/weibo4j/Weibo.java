package com.renren.ntc.video.utils.sina.weibo4j;

import com.renren.ntc.video.utils.sina.weibo4j.http.HttpClient;

/**
 * @author sinaWeibo
 * 
 */

public class Weibo implements java.io.Serializable {

	private static final long serialVersionUID = 4282616848978535016L;

	public static HttpClient client = new HttpClient();

	/**
	 * Sets token information
	 * 
	 * @param token
	 */
	public synchronized void setToken(String token) {
		client.setToken(token);
	}

}