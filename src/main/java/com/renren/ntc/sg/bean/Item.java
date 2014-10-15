package com.renren.ntc.sg.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-10-15
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
*/
/*
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0,
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  `city` varchar(24) NOT NULL DEFAULT '' ,
  `province` varchar(24) NOT NULL DEFAULT '' ,
  `district` varchar(24) NOT NULL DEFAULT '' ,
  `address`  varchar(245) NOT NULL DEFAULT '' ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY shop_id (`shop_id`),
   KEY user_id (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

 */

public class Item {


    private  String id = "";
    private  String  shop_id  = "";
    private  String  user_id   = "";
    private  String  city   = "";
    private  String  province   = "";
    private  String  district   = "";
    private  String  address   = "";
    private  Date  create_time  ;
    private  Date  update_time  ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }






}
