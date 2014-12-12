package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.Shop;
import com.renren.ntc.sg.bean.ShopCategory;
import com.renren.ntc.sg.dao.*;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Path("shop")
public class ShopController {

    private static int DEFAULT_SHOP_ID = 1;

    @Autowired
    public ShopDAO shopDAO;

    @Autowired
    public ItemsDAO itemsDAO;

    @Autowired
    public ShopCategoryDAO shopCategoryDAO;

    @Get("hot")
    public String hot (Invocation inv,@Param("shop_id") long shop_id ){
        // 校验合法性
        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
            shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }
        //获取 热门分类
        List<Item> itemls = itemsDAO.hot(SUtils.generTableName(shop_id),shop_id,0,20);
        inv.addModel("items", itemls);
        return "hot";
    }


    @Get("")
    public String index (Invocation inv,@Param("shop_id") long shop_id){
        // 校验合法性
        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
             LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
              shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }
        List<ShopCategory> category  = shopCategoryDAO.getCategory(shop.getId());

        if (null != category  &&  category.size() > 0){

            ShopCategory cate  = category.get(0);
            int cateId = cate.getCategory_id();
            List <Item> items = itemsDAO.getItems(SUtils.generTableName(shop_id),shop_id,cateId,0,20)  ;
            inv.addModel("items", items);
        }
        List<Item> itemls = itemsDAO.hot(SUtils.generTableName(shop_id),shop_id,0,20);


        inv.addModel("items", itemls);
        inv.addModel("categoryls",category);
        inv.addModel("shop",shop);
        return "shop" ;
        }

    @Get("getitems")
    public String getitems (Invocation inv,@Param("shop_id") long shop_id ,@Param("category_id") int category_id,
                             @Param("from") int from ,@Param("offset") int offset){
        // 校验合法性
        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
            shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }
        List<Item> itemls = itemsDAO.getItems(SUtils.generTableName(shop_id),shop_id, category_id, from, offset);
        inv.addModel("items", itemls);

        JSONObject jb =  new JSONObject() ;
        jb.put("code",0);
        JSONArray jarr =  new JSONArray() ;
        for (Item i : itemls ){
            JSONObject it =  new JSONObject() ;
            it.put("id" ,i.getId()) ;
            it.put("score" ,i.getScore()) ;
            it.put("price_new" ,i.getPrice_new()) ;
            it.put("price" ,i.getPrice()) ;
            it.put("count" ,i.getCount()) ;
            it.put("pic_url" ,i.getPic_url()) ;
            it.put("category_id" ,i.getCategory_id()) ;
            it.put("name" ,i.getName()) ;
            it.put("shop_id" ,i.getShop_id()) ;
            jarr.add(it);
        }

        jb.put("code",0);
        jb.put("data",jarr);
        return "@" + jb.toJSONString() ;
    }


    @Get("create")
    public String query (Invocation inv ,@Param("chn") String chn){

        return "@" ;
    }

}


