package com.renren.ntc.video.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.logic.UserTokenService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.AttentionService;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.login.NetUtils;

@Path("regist")
public class RegisterController implements NtcConstants {

	@Autowired
	private UserService userService;

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private UserTokenService userTokenService;

	@Autowired
	private AttentionService attentionService;

	private static int MAX_EMAIL_LENGTH = 60;

	private static final Logger logger = Logger.getLogger(RegisterController.class);

	// 注册的时候ajax校验用户名，违禁词和嫌疑词不让注册
	@Post("valiName")
	public String valiName(@Param("name") String name) {

		HashMap<String, String> antispamMap = new HashMap<String, String>();
		antispamMap.put("name", name.trim().replaceAll(" ", ""));
		return "@1";
	}

	// 用于处理用户注册或者修改邮箱的时候发送的ajax请求，验证邮箱是否已经被注册，邮箱可用返回1，不可用返回0
	@Post("valiEmail")
	public String valiEmail(@Param("email") String email) {
		User user = userService.queryByEmail(email);
		if (user == null) {
			return "@1";
		} else {
			return "@0";
		}
	}

	// 用于处理在用户设置的时候的邮箱验证，如果此邮箱本来就是用户的邮箱则不报错
	@Post("valiUpdateEmail")
	public String valiUpdateEmail(@Param("email") String email) {
		User hostUser = hostHolder.getUser();
		User user = userService.queryByEmail(email);
		// 此邮箱未被注册
		if (user == null) {
			return "@1";
			// 此邮箱已被注册，但是此邮箱就是当前用户的邮箱
		} else if (hostUser != null && hostUser.getId() == user.getId()) {
			return "@1";
		} else {
			return "@0";
		}
	}

	@Get("")
	public String newRegister(Invocation inv) {
		User user = hostHolder.getUser();
		if (user != null) {
			return "r:/";
		}
		return "regist";
	}

	@Post("")
	public String register(Invocation inv, @Param("email") String email, @Param("passwd") String passwd,
			@Param("name") String name, @Param("phoneNumber") String phoneNumber) {

		String ucode = inv.getParameter("vcode");
		String code = (String) inv.getRequest().getSession().getAttribute(Constants.VALIDATECODE);

		if (!Check(code, ucode)) {
			System.out.println(String.format("umatch vcode %s  %s", code,ucode));
			inv.addModel("msg", "提交的验证码不正确");
			return "regist";
		}

		email = StringUtils.trimToNull(email);
		passwd = StringUtils.trimToNull(passwd);
		name = name.trim().replaceAll(" ", "");
		// 后台校验，防止恶意用户不通过浏览器提交表单
		String regEmail = "^[a-zA-Z0-9_-]{1,}@" + "([a-zA-Z0-9_-]{1,}\\.)+[a-zA-Z]{1,}$";
		if (!email.matches(regEmail) || email.length() > MAX_EMAIL_LENGTH) {
			inv.addModel("msg", "email格式不匹配或者邮箱过长");
			return "regist";
		}

		if (!(BaseUtil.islegal(email) && BaseUtil.islegal(name) && BaseUtil.islegal(passwd))) {
			inv.addModel("msg", "用户名或者密码或者邮箱为空");
			return "regist";
		}

		if (null != userService.getUser(email)) {
			inv.addModel("msg", "该邮箱已经被注册，请换其他邮箱");
			return "regist";
		}
		User user;
		try {
			user = userService.createUser(email, passwd);
			user.setName(name);
			user.setHeadurl("http://www.yunming.com/photo/page-tiny.png");
			user.setPhoneNumber(phoneNumber);
			user.setPushType(Constants.DEFAULT_PUSHTYPE);
			// 更新索引用户数据
			userService.update(user);
			hostHolder.setUser(user);
			UserToken userToken = new UserToken();
			userToken.setUserId(user.getId());
			userToken.setToken(RandomStringUtils.randomAlphanumeric(32));
			userToken.setExpiredTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(MAX_LOGIN_DAYS)));
			userTokenService.setToken(userToken);
			NetUtils.setUserTokenToCookie(inv.getResponse(), userToken);
			return "home";
		} catch (Exception e) {
			e.printStackTrace();
			inv.addModel("msg", e.getMessage());
			return "regist";
		}
	}

	private boolean Check(String code, String ucode) {
		return StringUtils.equals(code, ucode);
	}

}
