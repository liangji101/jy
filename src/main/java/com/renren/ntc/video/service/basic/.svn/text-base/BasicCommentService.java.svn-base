package com.renren.ntc.video.service.basic;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.renren.ntc.video.formbean.Comment;

public interface BasicCommentService {
	public boolean addComment(String comment_index,long comment_id,int uid,String content,int desUid);
	public JSONArray getComments(String comment_index,int offset , int count );
	public JSONArray getCommentsReverse(String comment_index, int offset, int count);
	public JSONArray getWebCommentsReverse(String comment_index,int offset,int count);
	public List<Comment> getCommentBeans(String CommentIndex, int offset, int count);
	public JSONObject getComment(String comment_index,long comment_id);
	public boolean delComment(String comment_index,long comment_id);
	public boolean updateComment(String comment_index,long comment_id,int uid,String content);
	
}
