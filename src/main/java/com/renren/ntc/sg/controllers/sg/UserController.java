package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.*;
import com.renren.ntc.sg.dao.*;
import com.renren.ntc.sg.interceptors.access.NtcHostHolder;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Path("user")
public class UserController {

    private static int DEFAULT_SHOP_ID = 1;
    @Autowired
    public ShopDAO shopDAO;

    @Autowired
    public OrdersDAO orderDAO;

    @Autowired
    public NtcHostHolder holder;

    @Autowired
    public DeviceDAO deviceDAO;

    @Autowired
    public AddressDAO addressDAO;


    // 检查库存
    // 判断地址是否Ok

    @Get("profile")
    public String save(Invocation inv) {

        User u = holder.getUser();
        long user_id = 0;
        if (null != u) {
            user_id = u.getId();
        }


        List<Address>  addressls = addressDAO.getAddresses(user_id,0,20);
        List<Order>  orders = orderDAO.getOrder(user_id,0,20);
        inv.addModel( "address",addressls);
        inv.addModel( "orders",orders);
        return "user";
    }



}


