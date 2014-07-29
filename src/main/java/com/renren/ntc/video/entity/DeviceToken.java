package com.renren.ntc.video.entity;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-6-27
 * Time: 下午12:13
 */
public class DeviceToken {
    private int uid;
    
    private String deviceToken;
    
    private String clientVersion;
    

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        if(deviceToken.length() != 64) {
            throw new IllegalArgumentException("deviceToken必须是64个字符");
        }
        this.deviceToken = deviceToken;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public DeviceToken() {

    }

}
