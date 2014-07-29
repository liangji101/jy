package com.renren.ntc.video.biz.model;

import java.util.Date;

/**
 * 新鲜事的实体部分
 */
public class Feed {
	private long id;
	private int uid;

	private int comment_count;

	private String comment_index;

	/**create_time
	 * 用户输入的部分限定为140个字
	 */
	private String content;

	/**
	 */
	private byte feedType;
	/**
	 * 发布时间
	 */
	private Date create_time;
	/**
	 * 评论数
	 */

	private Content jcontent;

	private long fcomment_id;
	private String fcontent;
	private int fcomment_owner;
	private Date fcomment_time;
	private long lcomment_id;
	private String lcontent;
	private int lcomment_owner;
	private Date lcomment_time;
	private long score;

	public Feed() {
	}

	public Feed(long feedId, int uid, String content, byte feedType,
			Date create_time,String commentindex) {
		this.id = feedId;
		this.uid = uid;
		this.content = content;
		this.feedType = feedType;
		this.comment_index = commentindex;
		this.create_time = create_time;
	}
	public void setFcommentId(long fcomment_id) {
		this.fcomment_id = fcomment_id;
	}

	public long getFcommentId() {
		return fcomment_id;
	}

	public void setFcontent(String fcontent) {
		this.fcontent = fcontent;
	}

	public String getFcontent() {
		return fcontent;
	}

	public void setFcommentOwner(int fcomment_owner) {
		this.fcomment_owner = fcomment_owner;
	}

	public int getFcommentOwner() {
		return fcomment_owner;
	}

	public void setFcomment_time(Date fcomment_time) {
		this.fcomment_time = fcomment_time;
	}

	public Date getFcommentTime() {
		return fcomment_time;
	}

	public void setLcommentId(long lcomment_id) {
		this.lcomment_id = lcomment_id;
	}

	public long getLcommentId() {
		return lcomment_id;
	}

	public void setLcontent(String lcontent) {
		this.lcontent = lcontent;
	}

	public String getLcontent() {
		return lcontent;
	}

	public void setLcommentOwner(int lcomment_owner) {
		this.lcomment_owner = lcomment_owner;
	}

	public int getLcommentOwner() {
		return lcomment_owner;
	}

	public void setLcommentTime(Date lcomment_time) {
		this.lcomment_time = lcomment_time;
	}

	public Date getLcomment_time() {
		return lcomment_time;
	}

	public void setJcontent(Content con) {
		this.jcontent = con;
	}

	public Content getJcontent() {
		return jcontent;
	}

	public void setCommentCount(int comment_count) {
		this.comment_count = comment_count;
	}

	public int getCommentCount() {
		return comment_count;
	}

	public void setCommentIndex(String comment_index) {
		this.comment_index = comment_index;
	}

	public String getCommentIndex() {
		return comment_index;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getFeedType() {
		return feedType;
	}

	public void setFeedType(byte feedType) {
		this.feedType = feedType;
	}

	public Date getCreateTime() {
		return create_time;
	}

	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}

	public void setScore(long score) {
		this.score=score;
		
	}
	public long getScore() {
		return score;
		
	}

//	@Override
//	public String toString() {
//		final StringBuilder sb = new StringBuilder();
//		sb.append("Feed");
//		sb.append("{id=").append(id);
//		sb.append(", uid=").append(uid);
//		sb.append(", content='").append(content).append('\'');
//		sb.append(", feedType=").append(feedType);
//		sb.append(", create_time=").append(create_time);
//		sb.append('}');
//		return sb.toString();
//	}
}
