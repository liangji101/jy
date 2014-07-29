package com.renren.ntc.video.controllers;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.logic.UserTokenService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.AttentionService;
import com.renren.ntc.video.service.ThirdUserService;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.login.NetUtils;
import com.renren.ntc.video.utils.sina.weibo4j.Oauth;
import com.renren.ntc.video.utils.sina.weibo4j.Users;
import com.renren.ntc.video.utils.sina.weibo4j.Weibo;
import com.renren.ntc.video.utils.sina.weibo4j.http.AccessToken;

@Path("sinaLogin")
public class SinaLoginController {
	@Autowired
	private ThirdUserService thirdUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private NtcHostHolder hostHolder;
	@Autowired
	private UserTokenService userTokenService;
	@Autowired
	private AttentionService attentionService;

	private Logger logger = Logger.getLogger(SinaLoginController.class);

	@Get("")
	public String sinaLogin(@Param("code") String code,
                            @Param("error") String error,
                            @Param("moblie") String moblie,
                            @Param("origURL")String origURL,
                            Invocation inv) {
		//对应手机端的登录。这个代码不要删除
		if(null != moblie){
			return "login.vm";
		}
		logger.info("the origURL from sina is "+origURL);
		if(StringUtils.isEmpty(origURL)){
			origURL = "http://www.uume.com";
		}
//		try{
//			origURL = URLDecoder.decode(origURL, "utf-8");
//		}catch(Exception e){
//			origURL = "http://www.uume.com";
//		}
		if (!StringUtils.isEmpty(error)) {
			logger.error("we get error here,but we want code!");
			return "/views/third_login_cancel.vm";
		}
		if (code == null || "".equals(code)) {
			logger.error("the code from sina is null");
			return "/views/third_login_cancel.vm";
		}
		Oauth oauth = new Oauth();
		AccessToken accessToken = null;
		long uid = 0;
		try {
			accessToken = oauth.getAccessTokenByCode(code);
			if (accessToken != null) {
				uid = Long.parseLong(accessToken.getUid());
			} else {
				logger.error("something is wrong when get uid from accessToken");
				return "/views/third_login_cancel.vm";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("something is wrong when get accessToken by code");
			return "/views/third_login_cancel.vm";
		}
		User user = new User();
		ThirdUser thirdUser = thirdUserService.getBySinaUid(uid);
		// 已经绑定过则直接查询，未绑定则创建一个user
		if (thirdUser != null) {
			user = userService.query(thirdUser.getSc_uid());
		} else {
			Weibo weibo = new Weibo();
			weibo.setToken(accessToken.getAccessToken());
			Users users = new Users();
			com.renren.ntc.video.utils.sina.weibo4j.model.User sinaUser = null;
			String username = generUser(Constants.SINA_PRE);
			String passwd = generPass(Constants.SINA_PRE);
			String headurl="";
			String name = "";
			// 更新索引用户数据
//			userService.update(user);
			try {
				user = userService.createUser(username, passwd);
				try {
					sinaUser = users.showUserById(uid + "");
					headurl = sinaUser.getAvatarLarge();
					name = sinaUser.getScreenName();
				} catch (Exception e1) {
				    headurl = "http://head.xiaonei.com/photos/0/0/page-tiny.png";
				    name = BaseUtil.generName("文艺小清新", user.getId());
				}
				user.setHeadurl(headurl);
				user.setName(name);
				user.setPhoneNumber("");
				user.setPushType(Constants.DEFAULT_PUSHTYPE);
				user.setSycType(user.getSycType()|Constants.DEFAULT_SINA_SYCTYPE);
				// 更新索引用户数据
				userService.update(user);
				userService.updateSyc(user.getSycType(),user.getId());
				//对新用户，默认关注光影DV官方账号
				attentionService.attention(user.getId(), Constants.RAYDV_UID);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("something is wrong when we create a new user");
				return "/views/third_login_cancel.vm";
			}
		}
		boolean bind = thirdUserService.createOrUpdateSina(
				user.getId(), uid, accessToken.getAccessToken(),
				accessToken.getExpireIn());
		if (!bind) {
			logger.error("something is wrong when we bind rr or sina");
			return "/views/third_login_cancel.vm";
		}
		// 在此update方法里已经更新了索引
		hostHolder.setUser(user);
		UserToken oldUserToken = userTokenService.getToken(user.getId());
		if (oldUserToken == null) {
			oldUserToken = new UserToken();
			oldUserToken.setUserId(user.getId());
			oldUserToken.setToken(RandomStringUtils.randomAlphanumeric(32));
		}
		oldUserToken.setExpiredTime(new Date(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(NtcConstants.MAX_LOGIN_DAYS)));
		userTokenService.setToken(oldUserToken);// 更新Token
        inv.addModel("tokenT", oldUserToken.getToken().trim());
        inv.addModel("tokenU", oldUserToken.getUserId());
		NetUtils.setUserTokenToCookie(inv.getResponse(), oldUserToken);
		inv.addModel("origURL", origURL);
		return "/views/third_login_success.vm";
	}


	private String generUser(String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		Random random = new Random(100000000);
		sb.append(random.nextInt());
		sb.append("_");
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	private String generPass(String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}
}
