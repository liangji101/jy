package com.renren.ntc.video.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.login.NetUtils;
import com.renren.ntc.video.utils.renren.utils.HttpURLUtils;

@Path("rrLogin")
public class RenRenLoginController {

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

	private Logger logger = Logger.getLogger(RenRenLoginController.class);

	@Get("")
	public String rrLogin(@Param("code") String code, @Param("error") String error,
			@Param("error_description") String errorDescription, Invocation inv,@Param("origURL")String origURL)  {
		if (StringUtils.isNotEmpty(error) || StringUtils.isNotEmpty(errorDescription)) {
            logger.warn("使用人人登录失败, code: " + code + "desc: " + errorDescription);
            return "/views/third_login_cancel.vm";
		}
		if(StringUtils.isEmpty(origURL)){
			origURL = "http://www.uume.com";
		}
		if (code == null || code.equals("")) {
			logger.info("人人返回的code为空");
			logger.error("the code from renren is null");
			return "/views/third_login_cancel.vm";
		}
		logger.info("the origURL is "+origURL);
		String paramURL = origURL;
		//先去掉此操作
//        if(paramURL.contains("?")) {
//        	paramURL =  paramURL.substring(0, paramURL.indexOf("?")-1);
//        }
        //解决锚点的问题
        try {
        	paramURL = URLEncoder.encode(paramURL, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			logger.info("something is wrong when encode the origURL , the origURL is "+paramURL);
			e1.printStackTrace();
			return "/views/third_login_cancel.vm";
		}
		//end
		String rrOAuthTokenEndpoint = "https://graph.renren.com/oauth/token";
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id", "127c5aaf292e42eebaba55f725d05524");
		parameters.put("client_secret", "ac9c1bda37484e4ca659e153ce9111cc");
		parameters.put("redirect_uri", "http://www.uume.com/rrLogin?origURL="+paramURL);
		parameters.put("grant_type", "authorization_code");
		parameters.put("code", code);
		String tokenResult = "";
		try{
			tokenResult = HttpURLUtils.doPost(rrOAuthTokenEndpoint, parameters);
		}catch(Exception e){
			logger.error("something is wrong with the parameters to request RR Oauth", e);
			return "/views/third_login_cancel.vm";
		}
		JSONObject tokenJson = JSONObject.fromObject(tokenResult);
		if (tokenJson == null) {
			logger.error("用人人的code去换取的token为空");
			return "/views/third_login_cancel.vm";
		}
		// todo 异常逻辑
		if (tokenJson.get("error") != null) {
			logger.error("用code去请求access_token出错：error is:"
					+ tokenJson.getString("error") + " error_description is "
					+ tokenJson.getString("error_description"));
			return "/views/third_login_cancel.vm";
		}
		String accessToken = tokenJson.getString("access_token");
		String refreshToken = tokenJson.getString("refresh_token");
		JSONObject obj = tokenJson.getJSONObject("user");
		if (obj == null) {
			logger.error("用code换取的json字符串异常,取不到user");
			return "/views/third_login_cancel.vm";
		}
		long uid = obj.getLong("id");
		String name = obj.getString("name");
		if (StringUtils.isEmpty(name)) {
			name = generUser("文艺小清新");
		}
		JSONObject rrUser = (JSONObject) tokenJson.get("user");
		if (rrUser == null) {
			logger.error("用code换取的json字符串异常,取不到user");
			return "/views/third_login_cancel.vm";
		}
		JSONArray a = rrUser.getJSONArray("avatar");
		String headurl = (String) (((JSONObject) a.get(0))).get("url");
		if (headurl == null) {
			logger.error("用code换取的json字符串异常,取不到headurl");
			return "/views/third_login_cancel.vm";
		}
		if (headurl.equals("")) {
			headurl = "http://head.xiaonei.com/photos/0/0/page-tiny.png";
		}
		ThirdUser thirdUser = thirdUserService.getByRrUid(uid);
		User user = null;
		// 此人人用户未被绑定过,则生成一个sc用户
		if (thirdUser == null) {
			String username = generUser(Constants.RR_PRE);
			String passwd = generPass(Constants.RR_PRE);
			try {
				user = userService.createUser(username, passwd);
				// 追加默认姓名和头像
				user.setName(name);
				user.setHeadurl(headurl);
				user.setPhoneNumber("");
				user.setPushType(Constants.DEFAULT_PUSHTYPE);
				user.setSycType(user.getSycType()|Constants.DEFAULT_RR_SYCTYPE);
				// 更新索引用户数据
				userService.update(user);
				//对新用户，默认关注光影DV官方账号
				attentionService.attention(user.getId(), Constants.RAYDV_UID);
			} catch (Exception e) {
				return "/views/third_login_cancel.vm";
			}
		} else {
			user = userService.query(thirdUser.getSc_uid());
			
		}
		// 如果未绑定则写入token,refresh_token，如果已绑定过则更新token,refresh_token
		boolean bind = thirdUserService.createOrUpdateRenRen(user.getId(), uid, accessToken, refreshToken);
		
		if (!bind) {
			return "/views/third_login_cancel.vm";
		}
		hostHolder.setUser(user);
		UserToken oldUserToken = userTokenService.getToken(user.getId());
		if (oldUserToken == null) {
			oldUserToken = new UserToken();
			oldUserToken.setUserId(user.getId());
			oldUserToken.setToken(RandomStringUtils.randomAlphanumeric(32));
		}
		oldUserToken.setExpiredTime(new Date(System.currentTimeMillis()
				+ TimeUnit.DAYS.toMillis(NtcConstants.MAX_LOGIN_DAYS)));
		userTokenService.setToken(oldUserToken);// 更新Token
		NetUtils.setUserTokenToCookie(inv.getResponse(), oldUserToken);
        inv.addModel("tokenT", oldUserToken.getToken().trim());
        inv.addModel("tokenU", oldUserToken.getUserId());
		logger.info("用人人网账号成功登录后应该跳转的路径：" + origURL);
//        inv.addModel("origURL", origURL.substring(0, origURL.indexOf("#")));
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