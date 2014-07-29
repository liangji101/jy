package com.renren.ntc.video.biz.model;

import com.renren.ntc.video.constants.CustomPosition;
import com.renren.ntc.video.constants.CustomType;

/**
 * 客户端用户自定义
 * User: 帅伟良
 * Mail: weiliang.shuai@renren-inc.com
 * Date: 12-11-5
 * Time: 下午4:00
 */
public class Custom {
    private long id;

    /**
     * 自定义元素的类型
     */
    private int type;

    /**
     * 自定义元素的位置
     */
    private int position;

    /**
     * 图片链接地址
     */
    private String imgUrl = "";

    /**
     * 该位置点击转向的url
     */
    private String href = "";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Custom() {
    }

    @Override
    public String toString() {
        return "Custom{" +
                "id=" + id +
                ", type=" + type +
                ", position=" + position +
                ", imgUrl='" + imgUrl + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
