package com.renren.ntc.video.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.formbean.Comment;
import com.renren.ntc.video.service.basic.BasicCommentService;
import com.renren.ntc.video.utils.MongoDBUtil;

/**
 *  yunming.zhu
 */
@Service
public class CommentService implements BasicCommentService{
	
	private Logger logger = Logger.getLogger(CommentService.class);
	@Autowired
	private UserService userService;
	
	
	public boolean addComment(String comment_index,long comment_id,int uid,String content,int desUid){
		logger.info("the old comment is begining!");
		return MongoDBUtil.getInstance().addComment(comment_index,comment_id, uid, content,desUid);
	}
	
	
	public JSONArray getComments(String comment_index,int offset , int count ){    	   	
		BasicDBList ls =  MongoDBUtil.getInstance().getComments(comment_index,offset,count);
		return parseCommentContent(ls);
	}
	
	public JSONArray getWebComments(String comment_index,int offset , int count){
		BasicDBList ls =  MongoDBUtil.getInstance().getComments(comment_index,offset,count);
		return parseWebCommentContent(ls);
	}
	
	public JSONArray getCommentsReverse(String comment_index, int offset, int count) {
		BasicDBList ls = MongoDBUtil.getInstance().getCommentsReverse(comment_index, offset, count);
		return parseCommentContent(ls);
	}
	
	public JSONArray getWebCommentsReverse(String comment_index,int offset,int count){
		BasicDBList ls = MongoDBUtil.getInstance().getCommentsReverse(comment_index, offset, count);
		return parseWebCommentContent(ls);
	}
	
	public BasicDBList getCommentsDB(String comment_index,int offset , int count ){    	   	
		BasicDBList ls =  MongoDBUtil.getInstance().getComments(comment_index, offset, count);
		return ls;
		
	}

    public List<Comment> getCommentBeans(String CommentIndex, int offset, int count) {
        BasicDBList comments = this.getCommentsDB(CommentIndex, offset,
                count);
        Collections.reverse(comments);
        List<Comment> commentBeans = new ArrayList<Comment>();
        for (Object comment : comments) {
            BasicDBObject ob = (BasicDBObject) comment;
            Comment o = this.parseCommentContentToBean(ob);
            commentBeans.add(o);
        }
        return commentBeans;

    }

	
	
	public JSONObject getComment(String comment_index,long comment_id){
		BasicDBObject a =  MongoDBUtil.getInstance().getComment(comment_index, comment_id);
		return parseCommentContentJson(a);
	}
	
	public BasicDBObject getActualComment(String commentIndex,long commentId){
		return MongoDBUtil.getInstance().getComment(commentIndex, commentId);
	}
	
	public boolean delComment(String comment_index,long comment_id){
		return MongoDBUtil.getInstance().delComment(comment_index, comment_id);
	}
	
	public boolean updateComment(String comment_index,long comment_id,int uid,String content){
		return MongoDBUtil.getInstance().updateComment(comment_index, comment_id, uid, content);
	}
	private  JSONArray parseCommentContent(BasicDBList s) {
		JSONArray arr = new JSONArray();
		for (Object value : s) {
			BasicDBObject ob = (BasicDBObject) value;
			JSONObject o = parseCommentContentJson(ob);
			if (null != o){
			  arr.add(o);
			}
		}
		return arr;
	}
	//written by dapeng.zhou web端需要返回没有被unescapse的评论
	private JSONArray parseWebCommentContent(BasicDBList s){
		JSONArray arr = new JSONArray();
		for (Object value : s) {
			BasicDBObject ob = (BasicDBObject) value;
			JSONObject o = parseWebCommentContentJson(ob);
			if (null != o){
			  arr.add(o);
			}
		}
		return arr;
	}
	//end
    //手机端需要直接 做html unescape
	private  JSONObject parseCommentContentJson(BasicDBObject s) {
        JSONObject jbo = new JSONObject();
        jbo.put("comment_id", s.get("comment_id"));
        int uid = (Integer)s.get("uid");
        User user = userService.query(uid);
        if (user == null){
        	return null ;
        }
        jbo.put("owner", user.getId());
        String name = HtmlUtils.htmlUnescape(user.getName());
        jbo.put("owner_name", name);
        jbo.put("owner_headurl", user.getHeadurl());
        String content = HtmlUtils.htmlUnescape(s.getString("content"));
        jbo.put("content", content);
        jbo.put("time", s.get("time"));
        return jbo;
    }
	
	//written by dapeng.zhou
	//web端不用做html unescape
	private JSONObject parseWebCommentContentJson(BasicDBObject s){
		JSONObject jbo = new JSONObject();
        jbo.put("comment_id", s.get("comment_id"));
        int uid = (Integer)s.get("uid");
        User user = userService.query(uid);
        if (user == null){
        	return null ;
        }
        jbo.put("owner", user.getId());
        jbo.put("owner_name", user.getName());
        jbo.put("owner_headurl", user.getHeadurl());
        jbo.put("content", s.getString("content"));
        jbo.put("time", s.get("time"));
        return jbo;
	}
	//end
	
	private Comment parseCommentContentToBean(BasicDBObject s) {
        Comment jbo = new Comment();
        jbo.setCommentId(s.getString("comment_id"));
        
        int uid = (Integer)s.get("uid");
        User user = userService.query(uid);
        if (user == null){
        	return null ;
        }
        jbo.setOwnner(user.getId());
        jbo.setOwnnerName(user.getName());
        jbo.setOwnnerHeadurl(user.getHeadurl());
        jbo.setContent(s.getString("content"));
        String createTime=new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date ((long) s.getLong("time")));
        jbo.setCreateTime(createTime);
        return jbo;
    }
	
	public static void main(String[] args) {
		System.out.println(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date ()));
	}
}
