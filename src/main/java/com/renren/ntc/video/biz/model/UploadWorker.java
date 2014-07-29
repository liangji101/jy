package com.renren.ntc.video.biz.model;


public class UploadWorker {
    private int uid;
    private String name;
    
    public UploadWorker(int uid , String name ) {
    	this.uid = uid;
    	this.name = name;
    }
 
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
