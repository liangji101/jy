package com.renren.ntc.video.utils;

import net.sf.json.JSONObject;

/**
 * 
 * @author dapeng.zhou
 *
 */
public class SycmentUtil {
	/**
	 *前5个参数是collback时发送消息需要用到的
	 * @param uid  
	 * @param desUid
	 * @param content
	 * @param eid
	 * @param type
	 *后3个是在collback时需要将相应的数据保存到相应的表中
	 *commentIndex,commentId是在保存评论时用到的
	 *enType用在保存like和mark时
	 *不需要保存其中某个字段是则保存为0或""
	 * @param commentIndex
	 * @param commentId
	 * @param enType
	 * @return
	 */
	public static String getContent(int uid,int desUid,String content,long eid,int type,
			String commentIndex,long commentId,int enType){
		JSONObject obj = new JSONObject();
		obj.put("uid", uid);
		obj.put("desUid", desUid);
		obj.put("content", content);
		obj.put("eid", eid);
		obj.put("type", type);
		obj.put("commentIndex", commentIndex);
		obj.put("commentId",commentId);
		obj.put("enType", enType);
		return obj.toString();
	}
	public static void main(String[] args) {
//		System.out.println(SycmentUtil.getContent(1, 2, "gegeheh",3, 3,"hello",45,1));
		String str = "{\"uid\":1,\"desUid\":2,\"content\":\"gegeheh\",\"eid\":3,\"type\":3,\"commentIndex\":\"hello\",\"commentId\":45,\"enType\":1}";
		JSONObject obj = JSONObject.fromObject(str);
		System.out.println(obj.getInt("uid"));
		System.out.println(obj.getInt("desUid"));
		System.out.println(obj.getString("content"));
	}
	
	
	public static String getContent(long feedId ) {
		JSONObject obj = new JSONObject();
		obj.put("feedId", feedId);
		return obj.toString();
	}
}
