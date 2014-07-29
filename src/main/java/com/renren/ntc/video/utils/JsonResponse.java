package com.renren.ntc.video.utils;

import com.renren.ntc.video.biz.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.List;


public class JsonResponse {
	private static int INDEX = 4;
	
	private static final String PLACEHOLD = "<percentage>";
	
	private static final String PLACEHOLDPLUS = "<plus>";
	
	public static String  formFailResponse(int code){
		JSONObject jb = new JSONObject();
		JSONObject jb2 = new JSONObject();
		jb2.put("code", code);
		jb2.put("message", Constants.getMessage(code));
		jb.put("error", jb2);
		return jb.toString(INDEX);
	}
	
	
	public static String  formSuccessResponse(){
		JSONObject jb = new JSONObject();
		jb.put("op", "done");
		return jb.toString(INDEX);
	}
	
	public static String  formTicketProvideResponse(List<String> ls){
		JSONObject jb = new JSONObject();
		jb.put("ticket", ls);
		return jb.toString(INDEX);
	}
	
	public static String  formFeedResponse(long feedId,String comment_index,int comment_count){
        JSONObject jb = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("feed_id", feedId);
        content.put("comment_index", comment_index);
        content.put("coment_count", comment_count);
        jb.put("data", content);
		return jb.toString(INDEX);
	}
	
	public static String  formShareResponse(long shareId,String comment_index,int comment_count){
        JSONObject jb = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("share_id", shareId);
        content.put("comment_index", comment_index);
        content.put("coment_count", comment_count);
        jb.put("data", content);
		return jb.toString(INDEX);
	}
	
	
	public static String  formComplexResponse(Object content){
        JSONObject jb = new JSONObject();
        jb.put("data", content);
		return jb.toString(INDEX);
	}

    public static String  formComplexResponse(Object content, boolean isEnd){
        JSONObject jb = new JSONObject();
        jb.put("data", content);
        jb.put("isEnd", isEnd);
        return jb.toString(INDEX);
    }

	
	public static String  formConnectionsResponse(List <Integer> ls){
        JSONObject jb = new JSONObject();
        JSONArray arr = new JSONArray();
        for (int id : ls ){
            arr.add(id);
        }
        jb.put("data", arr);
		return jb.toString(INDEX);
	}
	
	
	public static String formTokenResponse(String token,String ss,JSONArray aa , boolean first,User user){
		JSONObject jb = new JSONObject();
		jb.put("access_token", token);
		jb.put("security_key", ss);
		jb.put("binded", aa);
		jb.put("syc", user.getSycType());
		jb.put("pushType", user.getPushType());
		jb.put("privacy", user.getAttPrivacy());
		if(first){
		  jb.put("ne_info",1);
		}
	    return jb.toString(INDEX);
	}
	
	
	public static String formTokenResponse(String token,String ss,JSONArray aa,int syc){
		JSONObject jb = new JSONObject();
		jb.put("access_token", token);
		jb.put("security_key", ss);
		jb.put("syc", syc);
		jb.put("binded", aa);
	    return jb.toString(INDEX);
	}
	
	public static String form3rdFailResponse(int code, String key) {
		JSONObject jb = new JSONObject();
		JSONObject jb2 = new JSONObject();
		jb2.put("code", code);
		jb2.put("message", key);
		jb.put("error", jb2);
		return jb.toString(INDEX);
	}


	public static String formFailResponse(int code, String key) {
		JSONObject jb = new JSONObject();
		JSONObject jb2 = new JSONObject();
		jb2.put("code", code);
		jb2.put("message", String.format( Constants.getMessage(code),key));
		jb.put("error", jb2);
		return jb.toString(INDEX);
	}
	
}

