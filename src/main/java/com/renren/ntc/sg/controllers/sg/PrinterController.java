package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.bean.Device;
import com.renren.ntc.sg.bean.OrderInfo;
import com.renren.ntc.sg.dao.DeviceDAO;
import com.renren.ntc.sg.dao.SWPOrderDAO;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SHttpClient;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Certificate;
import java.util.List;

@DenyCommonAccess
@Path("p")
public class PrinterController {

    private static int DEFAULT_S = 1;

    @Autowired
    public DeviceDAO deviceDAO;

    @Autowired
    public SWPOrderDAO swpOrderDAO;


    public static String SMSURL = "http://v.juhe.cn/sms/send";
    public static String APPKEY = "99209217f5a5a1ed2416e5e6d2af87fd";
    public static String TID = "1015";

    /**
     * 用于处理用户喜欢和不喜欢的ajax请求，成功返回1，失败返回0
     *
     * @return
     */
    @Get("get")
    @Post("get")
    public String get(Invocation inv, @Param("pid") long pid, @Param("token") String token) {
        // 验证
        if (0 > pid) {
            LoggerUtils.getInstance().log(String.format("pid illegal error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        Device dev = deviceDAO.getDev(pid);
        if (null == dev) {
            LoggerUtils.getInstance().log(String.format("dev is null error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)) {
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token, dev.getToken()));
            return "@" + Constants.PARATERERROR;
        }

        List<OrderInfo> orderinfo = swpOrderDAO.getOrder2Print();

        deviceDAO.update(pid, "looping");
        JSONObject jb = new JSONObject();
        jb.put("code", 0);
        jb.put("data", SUtils.from(orderinfo));
        return "@" + jb.toJSONString();
    }

    @Get("fb")
    @Post("fb")
    public String fb(Invocation inv, @Param("pid") long pid, @Param("token") String token, @Param("orderId") String orderId, @Param("re") String re, @Param("msg") String msg) {
        // 验证
        LoggerUtils.getInstance().log(String.format("fb request param  %d ,%s  ,re: %s , msg : %s ", pid, token, re, msg));
        if (0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param pid <0  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        Device dev = deviceDAO.getDev(pid);
        if (null == dev) {
            LoggerUtils.getInstance().log(String.format("error param  dev = null %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)) {
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token, dev.getToken()));
            return "@" + Constants.PARATERERROR;
        }
        int r = 0;
        if ("true".equals(re)) {
            r = swpOrderDAO.update(3, orderId);
        }
        if (r != 1) {
            LoggerUtils.getInstance().log(String.format("update order %s  pid  %d  token %s", orderId, pid, token));
        }
        //发邮件

        try {
            OrderInfo value = swpOrderDAO.getOrder(orderId);
            String v = null;
            String url;
            String mobile = "";
            byte[] t = null;
            String response = "Done";
            String vv = getAdress(value.getInfo());
            String orde = getOrs(value.getInfo());
            orde = orde.replaceAll("=", "").replaceAll("&", "").replaceAll("\\*", "");
            vv = vv.replaceAll("=", "").replaceAll("&", "");
            String ro = response.replace("=", "").replace("&", "");
            String message = "#address#=" + vv + "&#status#=" + ro + "&#orderDetail#=" + orde;
            message = SUtils.span(message);
            message = URLEncoder.encode(message,"utf-8");
            url = forURL(SMSURL, APPKEY, TID, "18612274066", message);
            System.out.println(String.format("Send  SMS mobile %s %s ,%s ", mobile, value.getOrder_id(), url));
            t = SHttpClient.getURLData(url, "");
            response = SUtils.toString(t);
            System.out.println(String.format("Post Shop SMS message No. %s : %s , %s  %s ", value.getOrder_id(), response, mobile, url));

            url = forURL(SMSURL, APPKEY, TID, "18600326217", message);
            System.out.println(String.format("Send  SMS mobile %s %s ,%s ", mobile, value.getOrder_id(), url));

            t = SHttpClient.getURLData(url, "");

            response = SUtils.toString(t);
            System.out.println(String.format("Post Shop SMS message No. %s : %s , %s  %s ", value.getOrder_id(), response, mobile, url));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "@" + Constants.DONE;
    }

    @Get("update")
    @Post("update")
    public String update(Invocation inv, @Param("pid") long pid, @Param("token") String token, @Param("status") String status) {
        // 验证
        if (0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        Device dev = deviceDAO.getDev(pid);
        if (null == dev) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)) {
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token, dev.getToken()));
            return "@" + Constants.PARATERERROR;
        }
        deviceDAO.update(pid, status);


        return "@" + Constants.DONE;
    }

    @Get("rp")
    @Post("rp")
    public String rp(Invocation inv, @Param("pid") long pid, @Param("token") String token, @Param("status") String status) {
        // 验证
        if (0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        Device dev = deviceDAO.getDev(pid);
        if (null == dev) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token));
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)) {
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token, dev.getToken()));
            return "@" + Constants.PARATERERROR;
        }
        LoggerUtils.getInstance().log(String.format("pinter %d  , fb :%s , ", pid, status));
        return "@" + Constants.DONE;
    }

    private static String getAdress(String address) {
        int index = address.indexOf("#address#");
        int index2 = address.indexOf("#orderDetail#");
        if (-1 == index || -1 == index) {
            return "";

        }
        return address.substring(index + 10, index2);
    }


    private static String getOrs(String address) {
        int index = address.indexOf("#orderDetail#");
        if (-1 == index) {
            return "";
        }
        String order = address.substring(index + 14, address.length());
        return order;
    }

    private static String forURL(String url, String appkey, String tid, String mobile, String value) {
        return url + "?key=" + appkey + "&dtype=json&mobile=" + mobile + "&tpl_id=" + tid + "&tpl_value=" + value;

    }
}
