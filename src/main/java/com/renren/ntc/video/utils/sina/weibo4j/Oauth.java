package com.renren.ntc.video.utils.sina.weibo4j;

import net.sf.json.JSONObject;

import com.renren.ntc.video.utils.sina.weibo4j.http.AccessToken;
import com.renren.ntc.video.utils.sina.weibo4j.model.PostParameter;
import com.renren.ntc.video.utils.sina.weibo4j.model.WeiboException;
import com.renren.ntc.video.utils.sina.weibo4j.util.WeiboConfig;


public class Oauth {
	// ----------------------------针对站内应用处理SignedRequest获取accesstoken----------------------------------------
	public String access_token;
	public String user_id;

	public String getToken() {
		return access_token;
	}


	/*
	 * 处理解析后的json解析
	 */
	public String ts(String json) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(json);
			access_token = jsonObject.getString("oauth_token");
			user_id = jsonObject.getString("user_id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_token;

	}

	/*----------------------------Oauth接口--------------------------------------*/

	public AccessToken getAccessTokenByCode(String code) throws WeiboException {
		return new AccessToken(Weibo.client.post(
				WeiboConfig.getValue("accessTokenURL"),
				new PostParameter[] {
						new PostParameter("client_id", WeiboConfig
								.getValue("client_ID")),
						new PostParameter("client_secret", WeiboConfig
								.getValue("client_SERCRET")),
						new PostParameter("grant_type", "authorization_code"),
						new PostParameter("code", code),
						new PostParameter("redirect_uri", WeiboConfig
								.getValue("redirect_URI")) }, false));
	}

	public String authorize(String response_type) throws WeiboException {
		return WeiboConfig.getValue("authorizeURL").trim() + "?client_id="
				+ WeiboConfig.getValue("client_ID").trim() + "&redirect_uri="
				+ WeiboConfig.getValue("redirect_URI").trim()
				+ "&response_type=" + response_type;
	}
}
