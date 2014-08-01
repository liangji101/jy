package com.renren.ntc.video.controllers;

import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.logic.UserTokenService;
import com.renren.ntc.video.biz.model.AccessToken;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.mgr.AccessTokenManager;
import com.renren.ntc.video.utils.login.NetUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Path("test")
public class TestController implements NtcConstants {

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private UserService userService;

	private static final Logger logger = Logger.getLogger(TestController.class);
   

	@Get("")
	public String test(Invocation inv) {
		return "test";
	}
	


  


}