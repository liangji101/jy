package com.renren.ntc.sg.bean;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-8
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class Product {

    private long id;
    private String   serialNo;

    private String    name  ;

    private String     pic_url ;
    private int category_id      ;
    private int     category_sub_id;


    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCategory_sub_id() {
        return category_sub_id;
    }

    public void setCategory_sub_id(int category_sub_id) {
        this.category_sub_id = category_sub_id;
    }


}
