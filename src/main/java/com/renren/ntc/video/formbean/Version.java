package com.renren.ntc.video.formbean;

import java.io.Serializable;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-8-8
 * Time: 下午3:45
 */
public class Version implements Serializable {

    private long verNum;
    
    private String verStr;
    
    private String verDes;
    
    private String verUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (verNum != version.verNum) return false;
        if (verDes != null ? !verDes.equals(version.verDes) : version.verDes != null) return false;
        if (verStr != null ? !verStr.equals(version.verStr) : version.verStr != null) return false;
        if (verUrl != null ? !verUrl.equals(version.verUrl) : version.verUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (verNum ^ (verNum >>> 32));
        result = 31 * result + (verStr != null ? verStr.hashCode() : 0);
        result = 31 * result + (verDes != null ? verDes.hashCode() : 0);
        result = 31 * result + (verUrl != null ? verUrl.hashCode() : 0);
        return result;
    }

    public String getVerDes() {

        return verDes;
    }

    public void setVerDes(String verDes) {
        this.verDes = verDes;
    }

    public long getVerNum() {
        return verNum;
    }

    public void setVerNum(long verNum) {
        this.verNum = verNum;
    }

    public String getVerStr() {
        return verStr;
    }

    public void setVerStr(String verStr) {
        this.verStr = verStr;
    }

    public String getVerUrl() {
        return verUrl;
    }

    public void setVerUrl(String verUrl) {
        this.verUrl = verUrl;
    }

    @Override
    public String toString() {
        return "Version{" +
                "verDes='" + verDes + '\'' +
                ", verNum=" + verNum +
                ", verStr='" + verStr + '\'' +
                ", verUrl='" + verUrl + '\'' +
                '}';
    }
}
