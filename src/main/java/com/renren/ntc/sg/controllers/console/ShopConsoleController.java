package com.renren.ntc.sg.controllers.console;

import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.annotations.AuthorizeCheck;
import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.annotations.LoginRequired;
import com.renren.ntc.sg.bean.*;
import com.renren.ntc.sg.biz.dao.ItemsDAO;
import com.renren.ntc.sg.biz.dao.ShopCategoryDAO;
import com.renren.ntc.sg.dao.ShopDAO;
import com.renren.ntc.sg.interceptors.access.RegistHostHolder;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.service.RegistUserService;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Regist
 */

@DenyCommonAccess
@LoginRequired
@AuthorizeCheck
@Path("shop")
public class ShopConsoleController {

	@Autowired
	private ShopDAO shopDAO;

    @Autowired
    private ShopCategoryDAO shopCategoryDAO ;


    @Autowired
    private ItemsDAO itemsDAO ;

	@Autowired
	private RegistHostHolder hostHolder;

    private Map keys = new HashMap();


    ShopConsoleController(){
        keys.put("name","");
        keys.put("pic_url","");
        keys.put("category_id","");
        keys.put("price","");
        keys.put("price_new","");
        keys.put("prcountice","");
        keys.put("pic_url","");
    }

    //注册的时候ajax校验用户名，违禁词和嫌疑词不让注册
	@Post("")
    @Get("")
	public String index(Invocation inv, @Param("shop_id") long shop_id ,@Param("category_id") int category_id ,
                        @Param("from") int from, @Param("offset") int offset){
        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
            shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }

        if ( 0 == from){
            from = 0;
        }
        if ( 0 == offset){
            offset = 50 ;
        }
        List<ShopCategory> categoryls  = shopCategoryDAO.getCategory(shop.getId());
        inv.addModel("shop",shop) ;

        if ( 0 == category_id ){
            if (categoryls.size() > 0){
            category_id =   categoryls.get(0).getCategory_id();
            }
            else{
                LoggerUtils.getInstance().log(String.format(" category is 0  && no category find on shop %d ",shop_id));
                return "shop";
            }
        }
        List<Item> itemls = itemsDAO.getItems(SUtils.generTableName(shop_id),shop_id,category_id,from,offset);
        if(from != 0){
           inv.addModel("previous_f", from - offset);
        }
        if(itemls.size() >=  offset){
           inv.addModel("next_f", from  + offset);
        }
        inv.addModel("itemls", itemls);
        inv.addModel("categoryls",categoryls);
        inv.addModel("curr_cate_id",category_id);
        return "shop";
	}

    @Post("del")
    @Get("del")
    public String del(Invocation inv, @Param("id") long id){

        return "items";
    }


    @Post("add")
    public String add(Invocation inv,@Param("item") String item){

        return  "@" ;
    }

    @Post("ud")
    @Get("ud")
    public String edit(Invocation inv ,@Param("id") String str_id,
                      @Param("value") String value){
        if( null == str_id){
            LoggerUtils.getInstance().log(String.format("str_id is null %s ",str_id));
            return "@error" ;
        }
        String[] keys = str_id.split("-");
        if(keys.length != 4){
            LoggerUtils.getInstance().log(String.format("str_id is null %s ",str_id));
            return "@error" ;
        }
        if ( null == value ) {
            return "@error" ;
        }
        long shop_id =  Long.valueOf(keys[1]);
        String  key = keys[2];
        long item_id =   Long.valueOf(keys[3]);
        itemsDAO.update(SUtils.generTableName(shop_id),item_id,key,value);

        return  "@"+Constants.DONE ;
    }


    @Post("query")
    @Get("query")
    public String query(Invocation inv, @Param("query") String query, @Param("shop_id") long shop_id){

        if (0  >= shop_id){
            shop_id = Constants.DEFAULT_SHOP ;
        }
        Shop shop = shopDAO.getShop(shop_id);

        if(null == shop){
            LoggerUtils.getInstance().log(String.format("can't find shop  %d  " ,shop_id) );
            shop = shopDAO.getShop( Constants.DEFAULT_SHOP);
        }
        List<ShopCategory> categoryls  = shopCategoryDAO.getCategory(shop.getId());
        long category_id =  categoryls.get(0).getId();
        query = SUtils.wrap(query);
        query = SUtils.wrap(query);
        List<Item>  itemls =  itemsDAO.search(SUtils.generTableName(shop_id),shop_id,query);

        inv.addModel("curr_cate_id",category_id);
        inv.addModel("categoryls",categoryls);
        inv.addModel("itemls", itemls);
        return "shop";
    }
}
