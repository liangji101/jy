package com.renren.ntc.video.utils;

import com.renren.ntc.video.biz.model.CommentIndex;
import com.renren.ntc.video.biz.model.Feed;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.formbean.FeedWithScore;
import com.renren.ntc.video.utils.sina.weibo4j.http.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


public class BaseUtil {
	private static final Random random = new Random();
	
	static Log logger = LogFactory.getLog(BaseUtil.class);
	
	private static List<String> ls = new ArrayList<String>() {{
        add(Constants.RR);
        add(Constants.SINA);
        add(Constants.QQ);
        add(Constants.SC);
    }};

	static {
        System.setProperty("deploy", "true");
	}

	public static boolean islegal(String key ){
		if (null == key || "".equals(key.trim())){
			return false ;
		}
		return true;
	}
	
	public static  String getResourceFullLocation(HttpServletRequest request) {
        StringBuffer sb = request.getRequestURL();
        String url = sb.toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            url = url + "?" + queryString;
        }
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }
	public static boolean typeContains(String type){
		return ls.contains(type);
	}
	
	//todo
	public static String generCommentIndex(String pre, long id ){
		StringBuffer sb = new StringBuffer () ;
		sb.append(pre);
		sb.append("_");
		sb.append(id);
		return sb.toString();
	}
	
	
	
	public static JSONArray formThirdInfo(ThirdUser tu){
		JSONArray arr = new JSONArray();
		if (null == tu){
			return arr;
		}
		if (tu.getSina_uid() > 0 ){
			JSONObject ob = new JSONObject();
			ob.put("sina_token" ,tu.getSina_token());
			ob.put("sina_refresh_token" ,tu.getSina_refresh_token());
			ob.put("sina_uid" ,tu.getSina_uid());
			arr.add(ob);
		}
		if (tu.getRr_uid() > 0 ){
			JSONObject ob = new JSONObject();
			ob.put("rr_token" ,tu.getRr_token());
			ob.put("rr_refresh_token" ,tu.getRr_refresh_token());
			ob.put("rr_uid" ,tu.getRr_uid() );
			arr.add(ob);
		}
		if (tu.getQq_uid() > 0 ){
			JSONObject ob = new JSONObject();
			ob.put("qq_token" ,tu.getQq_token());
			ob.put("qq_refresh_token" ,tu.getQq_refresh_token());
			ob.put("qq_uid" ,tu.getQq_uid());
			arr.add(ob);
		}
		return arr;
	}

	public static CommentIndex paser(String comment_index) throws Exception {
		 String [] arr = comment_index.split("_");
		 if (null == arr || arr.length!= 2){
			 throw new Exception ();
		 }
		 long id = Long.valueOf(arr[1]);
		 CommentIndex ci = new CommentIndex();
		 ci.setId(id);
		 ci.setPre(arr[0]);
		 return ci;
		
	}


	

//    public static Long getMid() {
//        return Math.abs(random.nextLong());
//    }

	public static String replaceFirst(String content, long shareid ,String userName) {
        if(content == null || "".equals(content)) {
            return "";
        }
        content =  content.replaceFirst("\"en_id\":\\d+","\"en_id\":" + shareid);
        return content.replaceFirst("\"title\":\"","\"title\":\"转自 " + userName + " " );
	}

	public static String generName(String key, int id) {
		StringBuffer sb  = new StringBuffer();
		sb.append(key);
		sb.append(id*33);
		return sb.toString();
	}

    private static final String FLASH_VARS_TEMPLATE = "%s%sban_bar_logo=on&appid=3000000130&en_id=%d&en_type=1&orientation=%s&islike=%d&origURL=%s&auto_start=%s";

    private static final String RELEASE_PLAYER_URL = "http://player.56.com/socialcam_%s.swf";

    private static final String DEV_PLAYER_URL = "http://www.56.com/flashApp/player_socialcam.12.10.12.c.swf";
//    private static final String DEV_PLAYER_URL = "http://www.56.com/flashApp/player_socialcam.12.12.07.a.swf";


    public static final String getPlayURL(long vid, long eid, int width , int height , int islike, String autoStart) {
            return DEV_PLAYER_URL
                + "?" +
                    "flvid=" + getBASE64(String.valueOf(vid)) + "&player_key=socialcam&" +  BaseUtil.getFlashVars(vid, eid, width,
                        height, 0, String.format("http://www.uume.com/share/%d", eid), "on");
    }

    public static final String getPlayURL(long vid) {
        if(System.getProperty("deploy") == null || System.getProperty("deploy").equals("true")) {
            return String.format(RELEASE_PLAYER_URL, getBASE64(String.valueOf(vid)));
        } else {
            return DEV_PLAYER_URL;
        }
    }

    public static final String getFlashVars(long vid, long eid, int width , int height , int islike , String origURL , String autoStart) {
        String flashVarVid = "";
        String playerKey = "";
        if(System.getProperty("deploy") != null && System.getProperty("deploy").equals("false")) {
            flashVarVid = "flvid=" + getBASE64(String.valueOf(vid)) + "&";
            playerKey = "player_key=socialcam&";
        }

        String orientation = "vertical";
        if (height < width) {
            orientation = "horizontal";
        }

        if(StringUtils.isEmpty(autoStart) || !autoStart.equals("on")) {
            autoStart = "off";
        }
        return String.format(FLASH_VARS_TEMPLATE, flashVarVid, playerKey, eid, orientation, islike, origURL, autoStart);
    }
	
	public static String getBASE64(String s) { 
		if (s == null) return "";
        return BASE64Encoder.encode(s.getBytes());
    }
	
	public static String getTransferedVid(long vid){
		return getBASE64(String.valueOf(vid));
	}
	
	public static String getShareUrl(String webHost, long enid) {
        return String.format("http://%s/share/%d", webHost, enid);
	}

	public static JSONObject buildShareInfo(long share_id,String syc_info) {
		JSONObject jb = new JSONObject();
		jb.put("share_id", share_id);
		jb.put("syc_info", syc_info);
		return jb;
	}

	public static String getJSONValue( String content ,String key ) {
		String cotent = null;
		try {
			JSONObject jb = JSONObject.fromObject(content);
			cotent = jb.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cotent;
	}
	
	public static String getJSONValue( String content ,String key ,String def) {
		String cotent = null;
		try {
			JSONObject jb = JSONObject.fromObject(content);
			cotent = jb.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null == cotent){
			cotent = def ;
		}
		return cotent;
	}



	public static String getComment(String content) {
		return  getJSONValue(content,"content");
	}

	public static List<Long> diff(List<FeedWithScore> ls2, List<Feed> lf) {
		   List<Long> fids =  new ArrayList<Long>();
		   List<Long> delids =  new ArrayList<Long>();
		   for (Feed f :lf){
			   fids.add(f.getId());
		   }
		   for (FeedWithScore feedId : ls2){
			   if(!fids.contains(feedId.getFeedId())){
				   delids.add(feedId.getFeedId());
			   }
		   }
		   return delids;
		}

	public static JSONArray appendDels(JSONArray con, List<Long> dels) {
		for ( long ids :dels){
			JSONObject jb = new JSONObject();
			jb.put("fid", ids);
			jb.put("del", 1);
			con.add(jb);
		}
		return con;
	}
	
	public static JSONArray paserPlayUrl(JSONObject re) {

		 JSONObject  jb = re.getJSONObject("info");
		 JSONArray ja = jb.getJSONArray("rfiles");
		 JSONArray jar =  new JSONArray ();
		 for (int i = 0 ; i < ja.size(); i ++){
			 JSONObject  o   = (JSONObject )ja.get(i);
//			  if("m_vga".equals(o.get("type"))){
//				  JSONObject ob = new JSONObject();
//				  ob.put("m_vga", o.getString("url"));
//				  ob.put("totaltime", o.getString("totaltime"));
//				  ob.put("filesize", o.getString("filesize"));
//				  jar.add(ob);
//			  }
//			  if("m_qvga".equals(o.get("type"))){
//				  JSONObject ob = new JSONObject();
//				  ob.put("m_qvga", o.getString("url"));
//				  ob.put("totaltime", o.getString("totaltime"));
//				  ob.put("filesize", o.getString("filesize"));
//				  jar.add(ob);
//			  }
			  if("qqvga".equals(o.get("type"))){
				  JSONObject ob = new JSONObject();
				  ob.put("qqvga", o.getString("url"));
				  ob.put("totaltime", o.getString("totaltime"));
				  ob.put("filesize", o.getString("filesize"));
				  jar.add(ob);
			  }
		 }
		 return jar;
	}


	public static String paserContent(String content) {
		if(content.split(" ", 2).length >= 2){
			content = content.split(" ", 2)[1];
		}
		return content;
	}

}