package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.Device;
import com.renren.ntc.sg.bean.OrderInfo;
import com.renren.ntc.sg.dao.DeviceDAO;
import com.renren.ntc.sg.dao.SWPOrderDAO;
import com.renren.ntc.sg.dao.ShopDAO;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Path("")
public class OrderController {

    private static int DEFAULT_SHOP_ID = 1;

    @Autowired
    public ShopDAO shopDAO;
    @Autowired
    public SWPOrderDAO  orderDAO;

    @Autowired
    public DeviceDAO deviceDAO;

    @Autowired
    public SWPOrderDAO swpOrderDAO ;





    @Get("loading")
    public String loadingPage (Invocation inv){
        return "loadingPage";
    }




    @Get("create")
    public String query (Invocation inv ,@Param("shop_id") long shop_id, @Param("items") long items)
    {

        return "@" ;
    }

}


