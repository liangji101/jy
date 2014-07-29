package com.renren.ntc.video.biz.model;

import java.util.Date;

import com.renren.ntc.video.utils.EmojiDecoder;


/**
 * 
 * @author dapeng.zhou
 * 封装mysql中保存的评论
 *
 */
public class CommentInfo {
	private long id;
	private long shareId;
	private long commentId;
	private long uid;
	private String content;
	private long desUid;
	private Date createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getShareId() {
		return shareId;
	}
	public void setShareId(long shareId) {
		this.shareId = shareId;
	}
	public long getCommentId() {
		return commentId;
	}
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getDesUid() {
		return desUid;
	}
	public void setDesUid(long desUid) {
		this.desUid = desUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
