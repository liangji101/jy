package com.renren.ntc.sg.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.OrderInfo;
import org.apache.commons.codec.binary.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-11-21
 * Time: 上午10:54
 * To change this template use File | Settings | File Templates.
 */
public class SUtils {

    public static String toString(byte[] text) {
        try {
            return new String(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String span (String response){
                if (null == response) {
                    return null;
                }
        response =  response.replaceAll("会所","会_所");
        response =  response.replaceAll("公寓","公_寓");
        response =  response.replaceAll("乡巴佬","乡_巴_佬");
        return response;
    }

    public static boolean isOk(String response) {
        if(null == response){
            return false;
        }
        JSONObject ob = JSON.parseObject(response);
        if ("0".equals(ob.getString("error_code"))) {
            return true;
        }
        return false;
    }


    public static int getShopId (String key){
           String  shop_id =  CookieHepler.getInstance().getCookie(key) ;
           int id = 0;
           try {
                id = Integer.valueOf(shop_id);
           }catch (Exception e){
             e.printStackTrace();
           }
           return id;

    }
    public static String forURL(String url, String appkey, String tid, String mobile, String value) {
        return url + "?key=" + appkey + "&dtype=json&mobile=" + mobile + "&tpl_id=" + tid + "&tpl_value=" + value;

    }

    public static  JSONArray from (List<OrderInfo> ls){
        JSONArray jarry =   new JSONArray();
        if (null == ls )  {
            return jarry ;
        }
        for ( OrderInfo o :  ls) {
            JSONObject jb = new JSONObject ();
            jb.put( "order_id" , o.getOrder_id());
            jb.put( "info" ,o.getInfo());
            jarry.add(jb);
        }
        return jarry;
    }
}
