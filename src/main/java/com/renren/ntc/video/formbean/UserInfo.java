package com.renren.ntc.video.formbean;

public class UserInfo {
	private com.renren.ntc.video.biz.model.User user;
	private int videoCount;
	private int attentionedByCount;
	private int attentioningCount;
	private int isAttention;
	
	public com.renren.ntc.video.biz.model.User getUser() {
		return user;
	}
	public void setUser(com.renren.ntc.video.biz.model.User fans) {
		this.user = fans;
	}
	public int getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(int videoCount) {
		this.videoCount = videoCount;
	}
	public int getAttentionedByCount() {
		return attentionedByCount;
	}
	public void setAttentionedByCount(int attentionedByCount) {
		this.attentionedByCount = attentionedByCount;
	}
	public int getAttentioningCount() {
		return attentioningCount;
	}
	public void setAttentioningCount(int attentioningCount) {
		this.attentioningCount = attentioningCount;
	}
	public int getIsAttention() {
		return isAttention;
	}
	public void setIsAttention(int isAttention) {
		this.isAttention = isAttention;
	}
	
	
}
