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
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Path("login")
public class LoginController implements NtcConstants {

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private UserService userService;

	private static final Logger logger = Logger.getLogger(LoginController.class);


	@Post
	public String login(Invocation inv, @Param("origURL") String origURL,
			@Param("phoneNumber") String phoneNumber, @Param("passwd") String passwd) {
		User user = hostHolder.getUser();
		if (user != null) {
			if (StringUtils.isNotEmpty(origURL)) {
				try {
					return "r:" + URLDecoder.decode(origURL, "UTF-8");
				} catch (Exception e) {
					logger.info("跳转失败：" + origURL, e);
					inv.addModel("origURL", origURL);
					return "r:/";
				}
			}
		}
		if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(passwd)) {
			inv.addModel("msg", " 用户名字或密码为空 ");
			inv.addModel("origURL", origURL);
			return "login";
		}
		UserToken requestToken = NetUtils.getUserTokenFromCookie(inv.getRequest());
		int userId = requestToken.getUserId();
		if (userId > 0 && userTokenService.isValid(requestToken)) {
			user = userService.query(userId);// 登录成功
			hostHolder.setUser(user);
			requestToken.setExpiredTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(MAX_LOGIN_DAYS)));
			userTokenService.setToken(requestToken);
			if (StringUtils.isNotEmpty(origURL)) {
				try {
					return "r:" + URLDecoder.decode(origURL, "UTF-8");
				} catch (Exception e) {
					logger.info("跳转失败：" + origURL, e);
					inv.addModel("origURL", origURL);
					return "r:/";
				}
			}
		}
		user = userService.CheckUserPassport(phoneNumber, passwd);
		if (user != null) {
			hostHolder.setUser(user);
			UserToken oldUserToken = userTokenService.getToken(user.getId());
			if (oldUserToken == null) {
				AccessToken at = AccessTokenManager.getInstance().getAccessToken(
						user.getId());
				oldUserToken = new UserToken();
				oldUserToken.setUserId(user.getId());
				oldUserToken.setToken(at.getSessionKey());
			}
			oldUserToken.setExpiredTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(MAX_LOGIN_DAYS)));
			userTokenService.setToken(oldUserToken);// 更新Token
			NetUtils.setUserTokenToCookie(inv.getResponse(), oldUserToken);
			if (StringUtils.isNotEmpty(origURL)) {
				try {
					return "r:" + URLDecoder.decode(origURL, "UTF-8");
				} catch (Exception e) {
					logger.info("跳转失败：" + origURL, e);
					inv.addModel("origURL", origURL);
					return "r:/";
				}
			}
		} else {
			inv.addModel("msg", "用户名或密码不正确");
			inv.addModel("origURL", origURL);
			return "login";
		}
		return "r:/";
	}
	


  


}