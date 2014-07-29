package com.renren.ntc.video.utils;

import net.sf.json.JSONObject;

import java.util.Map;
import java.util.Map.Entry;


/**
 * 把各种类型的参数进行封装字符串，返回给调用者。
 * @author Zhancheng Deng 
 * @since 2011-8-5 下午05:47:25
 */
public class ResultWrapper {

	
	 /**
	  *  把 {@link Constants.ErrorCode} 中的整形值封装成 {@link String}
	  */
	public static String wrapErrorCode(int errorCode) {
		 	JSONObject json = new JSONObject();
	        json.accumulate("result", errorCode);
	        json.accumulate("code", errorCode);
	        if( errorCode != Constants.ErrorCode.SUCCESS ){
	        	json.accumulate("message", Constants.infoMap.get(errorCode));
	        }
	        return json.toString();
	    }
	 
	 /**
	  * 把Map类型对象封装成 {@link String} 类型
	  */
	public static String wrapMap(Map<String, String> map) {
	        StringBuilder sb = new StringBuilder();
	        if (map == null || map.isEmpty()) return "";
	        for (Entry<String, String> e : map.entrySet()) {
	            sb.append(e.getKey() + " = " + e.getValue() + ",");
	        }
	        sb.deleteCharAt(sb.length() - 1);
	        return sb.toString();
	    }
}
