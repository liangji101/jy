package com.renren.ntc.sg.controllers.sg;

import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.Shop;
import com.renren.ntc.sg.dao.ItemsDAO;
import com.renren.ntc.sg.dao.ShopDAO;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Path("")
public class HomeController {

    private static int DEFAULT_SHOP_ID = 1;

    @Autowired
    public  ShopDAO  shopDAO;
    @Autowired
    public ItemsDAO  itemsDAO;

	/**
	 * 用于处理用户喜欢和不喜欢的ajax请求，成功返回1，失败返回0
	 * @return
	 */
    @Get("")
	@Post("")
	public String index( Invocation inv,@Param("lat")double lat,@Param("lng") double lng )  {

        String  Url ="http://wap.koudaitong.com/v2/showcase/homepage?alias=rqh6lz6o" ;
        if(lat ==0  || lng == 0){
            Shop s = shopDAO.getShop(1);
            Url =  s.getShop_url();
        }
        System.out.println(String.format("------------------------"));
        double scope =  3000 / 100000.0;
        double maxLat = lat + scope;
        double minLat = lat - scope;
        double maxLng = lng + scope;
        double minLng = lng - scope;
        List<Shop> s  = shopDAO.getShop(maxLat,minLat,maxLng,minLng);
        System.out.println(String.format("get Shop size %s " ,s.size()));
        if (null == s || 0 == s.size())
        {
            System.out.println(String.format("Access default ,url %s ",Url));
            return "r:" + Url ;
        }
        int k = s.size();
        Random r =  new Random();
        int t = r.nextInt(k);
        t = (t >= k)?k-1:t;
        Shop ss = s.get(t);
        System.out.println(String.format("Access %s ,url %s ",t,ss.getShop_url()));
        return "r:" + ss.getShop_url() ;

	}

    @Get("item")
    @Post("item")
    public String item( Invocation inv,  @Param("itemID")long id) {

        if (id <= 0 ){
            id = DEFAULT_SHOP_ID;
        }
        Item item = itemsDAO.getItem(id);
        if(null == item ){
            inv.addModel("errmsg","error");
            return "miniDetail";
        }
        inv.addModel("item",item );
        return "miniDetail";
    }

    @Get("cart")
    @Post("cart")
    public String cart( Invocation inv) {
        List <Long> itemids =  getCartInfo(inv);
        List <Item> items = itemsDAO.getItems(itemids);
        if(null == items ){
            inv.addModel("errmsg","error");
            return "cart";
        }
        inv.addModel("items",items );
        return "cart";
    }

    private  List <Long>  getCartInfo( Invocation inv){
           return Collections.emptyList();
    }

    @Get("loading")
    public String loadingPage (Invocation inv){
        return "loadingPage";
    }

    @Get("about")
    public String about (Invocation inv){
        return "about";
    }

    @Post("feedback")
    @Get("feedback")
    public String feedback (Invocation inv ,@Param("feedback") String feedback,@Param("contact") String contact){
        System.out.println(String.format("Rec User feedback %s , %s ",feedback,contact));
        return "@done";
    }
    @Post("redirect")
    @Get("redirect")
    public String redirect (Invocation inv ){
        return "redirect";
    }
}
