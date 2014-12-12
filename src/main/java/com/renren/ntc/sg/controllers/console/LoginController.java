package com.renren.ntc.sg.controllers.console;

import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.bean.RegistUser;
import com.renren.ntc.sg.dao.RegistUserDAO;
import com.renren.ntc.sg.interceptors.access.RegistHostHolder;
import com.renren.ntc.sg.service.RegistUserService;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.CookieManager;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * yunming.zhu
 */
@DenyCommonAccess
@Path("login")
public class LoginController  {

	@Autowired
	private RegistHostHolder hostHolder;

    @Autowired
    private RegistUserDAO userDAO;

	@Autowired
	private RegistUserService userService;


	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Get ("")
    @Post ("")
	public String newLogin(Invocation inv, @Param("origURL") String origURL) {
        RegistUser user = hostHolder.getUser();
		if (user != null) {
			return "r:" + origURL;
		}
		if (origURL == null || origURL.equals("")) {
			origURL = Constants.DOMAIN;
		}
		try {
			origURL = URLEncoder.encode(origURL, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "r:/";
		}
		inv.addModel("origURL", origURL);
		inv.addModel("msg", "abc");
		return "login";
	}


    @Get ("valid")
    @Post ("valid")
    public String Login(Invocation inv,@Param("phome") String phone,@Param("pwd") String pwd, @Param("origURL") String origURL) {
        RegistUser user = hostHolder.getUser();
        if (user != null) {
            return "r:" + origURL;
        }
        if (origURL == null || origURL.equals("")) {
            origURL = Constants.DOMAIN;
        }
        try {
            origURL = URLEncoder.encode(origURL, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "r:/";
        }
        inv.addModel("origURL", origURL);
        inv.addModel("msg", "");

        RegistUser u = userDAO.getUser(phone, pwd) ;
        if(null == u){
            inv.addModel("msg","用户名字或密码不正确");
            return "login";
        }
        CookieManager.getInstance().saveCookie(inv.getResponse(), Constants.COOKIE_KEY_REGISTUSER, SUtils.wrapper(u.getId()));
        return "r:" + origURL    ;
    }



}