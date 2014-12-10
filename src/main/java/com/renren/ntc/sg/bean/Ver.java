package com.renren.ntc.sg.bean;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-10
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class Ver {
    private long id;
    private String ver;
    private String url ;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
