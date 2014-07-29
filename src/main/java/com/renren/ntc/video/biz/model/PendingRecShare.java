package com.renren.ntc.video.biz.model;

import java.util.Date;

public class PendingRecShare {
	private long id;
	private long shareId;
	private Date opTime;
	private int result;
	private Date createTime;
	private String opTimeStr;
	public String getOpTimeStr() {
		return opTimeStr;
	}
	public void setOpTimeStr(String opTimeStr) {
		this.opTimeStr = opTimeStr;
	}
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
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
