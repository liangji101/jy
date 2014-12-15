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
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Path("order")
public class OrderController {

    private static int DEFAULT_SHOP_ID = 1;
    @Autowired
    public ShopDAO shopDAO;

    @Autowired
    public OrdersDAO orderDAO;

    @Autowired
    public NtcHostHolder holder;


    @Autowired
    public ItemsDAO itemsDAO;

    @Autowired
    public DeviceDAO deviceDAO;

    @Autowired
    public SWPOrderDAO swpOrderDAO;


    @Get("loading")
    public String loadingPage(Invocation inv) {
        return "loadingPage";
    }


    // 检查库存
    // 判断地址是否Ok

    @Get("save")
    @Post("save")
    public String save(Invocation inv, @Param("shop_id") long shop_id, @Param("items") String items, @Param("order_id") String order_id) {

        User u = holder.getUser();
        long user_id = 0;
        if (null != u) {
            user_id = u.getId();
        }
        if (0 >= shop_id) {
            shop_id = Constants.DEFAULT_SHOP;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if (null == shop) {
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  ", shop_id));
            return "@ error";
        }

        if (StringUtils.isBlank(items)) {
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  items %s", shop_id, items));
            return "@ error";
        }

        boolean ok = true;
        JSONArray jbarr = (JSONArray) JSONArray.parse(items);
        List<Item4V> itemls = new ArrayList<Item4V>();
        for (int i = 0; i < jbarr.size(); i++) {
            JSONObject jb = (JSONObject) jbarr.get(i);
            long item_id = jb.getLong("item_id");
            int count = jb.getInteger("count");
            Item item = itemsDAO.getItem(SUtils.generTableName(shop_id),shop_id, item_id);
            //计算库存是否足够
            Item4V i4v = new Item4V();
            i4v.setExt(count);
            i4v.setCount(count);
            i4v.setName(item.getName());
            i4v.setId(item.getId());
            i4v.setCategory_id(item.getCategory_id());
            i4v.setPic_url(item.getPic_url());
            i4v.setPrice(item.getPrice());
            i4v.setShop_id(item.getShop_id());

            if (item.getCount() < count) {
                //库存剩余
                i4v.setExt(item.getCount());
                i4v.setCount(count);
                i4v.setInfo ("只剩这些了,正在通知店家补货");
                ok = false;
            }
            itemls.add(i4v);
        }
        inv.addModel("itemls", itemls);
        if (!ok) {
            return "order_confirm";
        }

        if (!SUtils.islegal(order_id)) {
            order_id = SUtils.getOrderId();
            LoggerUtils.getInstance().log(String.format("re error order %s,  items %s  ", order_id, items));
            LoggerUtils.getInstance().log(String.format("create new  order %s ", order_id));
        }
        //库存变化 TODO
        for (Item4V it : itemls ){
            int  count  = it.getCount() ;
            long s_id = it.getShop_id();
            long i_id  =  it.getId();
            itemsDAO.decr(SUtils.generTableName(s_id),s_id,i_id,count) ;
            LoggerUtils.getInstance().log(String.format(" item  %d   decr %d" , i_id , count));
        }
        Order order = new Order();
        order.setOrder_id(order_id);
        order.setShop_id(shop_id);
        order.setRemarks("remarks");
        order.setSnapshot(items);
        order.setStatus(1);         //已经确认的状态
        order.setUser_id(user_id);
        int re = orderDAO.insertUpdate(order);
        if (re == 1) {
            return "error";
        }
        return "order";
    }



}


