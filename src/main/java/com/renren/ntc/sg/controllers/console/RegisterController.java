package com.renren.ntc.sg.controllers.console;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.renren.ntc.sg.interceptors.access.RegistHostHolder;
import com.renren.ntc.sg.service.RegistUserService;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * Regist
 */

@Path("regist")
public class RegisterController  {

	@Autowired
	private RegistUserService userService;

	@Autowired
	private RegistHostHolder hostHolder;

	private static int MAX_EMAIL_LENGTH = 60;

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	//注册的时候ajax校验用户名，违禁词和嫌疑词不让注册
	@Post("")
	public String valiName(@Param("name") String name){
    		return "regist";
	}

    @Post("add")
    public String add(@Param("name") String name){

        return "regist";
    }


}
