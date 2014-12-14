package com.renren.ntc.sg.controllers.console;

import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.bean.RegistUser;
import com.renren.ntc.sg.interceptors.access.RegistHostHolder;
import com.renren.ntc.sg.service.RegistUserService;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 
 * Regist
 */

@DenyCommonAccess
@Path("shop")
public class ShopConsoleController {

	@Autowired
	private RegistUserService userService;

	@Autowired
	private RegistHostHolder hostHolder;


	//注册的时候ajax校验用户名，违禁词和嫌疑词不让注册
	@Post("")
    @Get("")
	public String inde(Invocation inv, @Param("shop_id") long shop_id){



         return "items";
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

    @Post("edit")
    public String edit(Invocation inv,@Param("item") String item){

        return  "@" ;
    }

}
