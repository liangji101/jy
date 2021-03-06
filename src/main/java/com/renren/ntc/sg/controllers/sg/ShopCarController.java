package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.*;
import com.renren.ntc.sg.biz.dao.AddressDAO;
import com.renren.ntc.sg.biz.dao.ItemsDAO;
import com.renren.ntc.sg.biz.dao.ShopCategoryDAO;
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

import java.util.ArrayList;
import java.util.List;

@Path("shopCar")
public class ShopCarController {

    private static int DEFAULT_SHOP_ID = 1;

    @Autowired
    public NtcHostHolder holder;

    @Autowired
    public ShopDAO shopDAO;

    @Autowired
    public AddressDAO addressDAO;

    @Autowired
    public ItemsDAO itemsDAO;

    @Autowired
    public ShopCategoryDAO shopCategoryDAO;


    @Get("confirm")
    @Post("confirm")
    public String hot (Invocation inv,@Param("shop_id") long shop_id,@Param("items") String items){

        User u = holder.getUser();
        long user_id = 0;
        if (null != u) {
            user_id = u.getId();
        }
        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
            shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }

        if (StringUtils.isBlank(items)) {
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  items %s", shop_id, items));
            return "r:/sg/shop?shop_id="+shop_id;
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
                i4v.setExt(item.getCount());
                i4v.setCount(count);
                ok = false;
            }
            itemls.add(i4v);
        }
        List<Address>  addressls = addressDAO.getAddresses(user_id, 0, 20);

        inv.addModel( "addressls",addressls);
        inv.addModel("shop", shop);
        inv.addModel("itemls", itemls);
        if (!ok) {
          inv.addModel("msg", "部分商品库存不足");
        }
        return "order_confirm";
    }
}


