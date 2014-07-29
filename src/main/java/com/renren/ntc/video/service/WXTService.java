package com.renren.ntc.video.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mongodb.util.JSON;
import com.renren.ntc.video.entity.WXTstden;
import com.renren.ntc.video.utils.WxtApiInvoker;

@Service
public class WXTService {
	
	private static String partner = "20131211154443";
	private static String periodType = "7";
	/**
	 * @param args
	 */

	public WXTstden createUser(String username,String passwd,String nickname,String validPeriodType ) {
		String url = "http://api.wangxiaotong.com/api/student/create";
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("nickname",nickname);
		params.put("passwd", passwd);
		params.put("username", username);
		params.put("validPeriodType", validPeriodType);
		String response = WxtApiInvoker.getInstance().sendPostRestRequest(params, url);
		return cPaser(response);
	}
	

	private WXTstden cPaser(String response) {
		if(StringUtils.isEmpty(response)){
		  return null;	
			
		}
		if (-1 != response.indexOf("\"error\"")){
			  return null;	
		}
		WXTstden wxtst = new WXTstden();
		JSONObject jb = null;
		try {
			 jb = (JSONObject) JSON.parse(response);
		} catch (Exception e) {
			   e.printStackTrace();
               return null;
		}
		
		if(null == jb){
			return null;
		}
		wxtst.setAvatarUrl(jb.getString("avatarUrl"));
		wxtst.setEmail(jb.getString("email"));
		wxtst.setUniqueUserId(jb.getString("uniqueUserId"));
		wxtst.setMobile(jb.getString("mobile"));
		wxtst.setNickname("nickname");
		wxtst.setUsername(jb.getString("username"));
		wxtst.setValidEndTime(jb.getString("validEndTime"));
		wxtst.setValidStartTime(jb.getString("validStartTime"));
	    wxtst.setValidPeriodType(jb.getString("validPeriodType"));
		wxtst.setIsnew(true);
		return wxtst ;
	}


	/**
	 * @param args
	 */

	public String attendCourseRoom(String uid,String courseId) {
		String url = "http://api.wangxiaotong.com/api/room/dispatch?";
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("uniqueUserId",uid);
		params.put("uniqueCourseId", courseId);
		String pass = WxtApiInvoker.getInstance().sig(params);
		return url + pass;
	}
	
	
	public String attendPlaybackRoom(String uid,String courseId) {
		String url = "http://api.wangxiaotong.com/api/record/dispatch?";
		Map<String, String> params = new HashMap<String, String>();
		params.put("partner", partner);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("uniqueUserId",uid);
		params.put("uniqueCourseId", courseId);
		String pass = WxtApiInvoker.getInstance().sig(params);
		return url + pass;
	}
	
	
	public String getCourseId(String uid) {
		return null;
	}
	
	public static void main (String args[]){
		
		WXTService wxt = new WXTService();
		wxt.createUser("test1222", "test1222", "test1222", "1");
		System.out.println();
	}

}
