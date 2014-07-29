package com.renren.ntc.video.controllers.api.regist;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONArray;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.exception.UserNameDuplicateException;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.logic.UserTokenService;
import com.renren.ntc.video.biz.model.AccessToken;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.AttentionService;
import com.renren.ntc.video.service.ThirdUserService;
import com.renren.ntc.video.service.mgr.AccessTokenManager;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;
import com.renren.ntc.video.utils.login.NetUtils;

/**
 * yunming.zhu
 */

@Path("")
public class RegistController implements NtcConstants {
	@Autowired
	private UserService userService;

	@Autowired
	private NtcHostHolder hostHolder;
	
	@Autowired
	private ThirdUserService thirdUserService;
	
	@Autowired
	private UserTokenService userTokenService;
	
	@Autowired
	private AttentionService attentionService;

	static Log logger = LogFactory.getLog(RegistController.class);
	
	@Post("")
	@Get("")
	public String regist(Invocation inv, @Param("app_id")
	int appId, @Param("passwd")
	String passwd,@Param("name") String name,
	@Param("phoneNumber") String phoneNumber ) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format(" regist name %s passwd %s phoneNumber %s ", name, passwd, phoneNumber));
		}
		if (appId != 11520) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.APP_ID_INVALID);
		}
		
		phoneNumber = StringUtils.trimToNull(phoneNumber);
		passwd = StringUtils.trimToNull(passwd);
		name = StringUtils.trimToNull(name);
		if (!BaseUtil.islegal(phoneNumber)|| !BaseUtil.islegal(passwd) ) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "phoneNumber or passwd");
		}
		
		if (!BaseUtil.islegal(name)) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "name");
		}
		
		if(phoneNumber.matches("[0-9]{13}")){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "phoneNumber"); 
		}
		
		if (null != userService.getUser(phoneNumber)) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.USER_ALREADY_USED);
		}
		
		try {
			User user = userService.createUser(phoneNumber, passwd);
			name = name.trim().replaceAll(" ", "");
			user.setName(name);
			user.setHeadurl("");
			user.setPhoneNumber(phoneNumber);
			AccessToken at = AccessTokenManager.getInstance().getAccessToken(
					user.getId());
			UserToken userToken = new UserToken();
			userToken.setUserId(user.getId());
			userToken.setToken(at.getSessionKey());
			userToken.setExpiredTime(new Date(System.currentTimeMillis()
					+ TimeUnit.DAYS.toMillis(MAX_LOGIN_DAYS)));
			userTokenService.setToken(userToken);// 设置Token
			NetUtils.setUserTokenToCookie(inv.getResponse(), userToken);
			
			return "@"
					+ JsonResponse.formTokenResponse(at.getSessionKey(), at
                    .getSessionSecret(), new JSONArray(), true, user);
			
		} catch (UserNameDuplicateException e) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.USERNAME_DUPLICATED);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		}
	}
	
	public  String getNameFromEmail(String email){
		int index = email.lastIndexOf("@");
		String name = email.substring(0, index);
		return name;
	}
	

	@AccessTokenChecker(LoginRequired = false)
	@Post("bind")
	// todo 同一个第三方用户重复登陆有问题 , 及 第三方追加绑定
	public String bind(@Param("app_id") int appId,
                       @Param("third_token") String third_token,
                       @Param("third_refresh_token") String third_refresh_token,
                       @Param("third_uid") long third_uid,
                       @Param("type") String type) {
		if (logger.isInfoEnabled()) {
			logger.info(String.format(
					"bind   third_token %s third_refresh_token %s third_uid %d ",
					third_token, third_refresh_token, third_uid));
		}
		if (0 >= third_uid || !BaseUtil.islegal(third_token)
				|| !BaseUtil.islegal(third_refresh_token)
				|| !BaseUtil.typeContains(type)) {
			return "@"
					+ JsonResponse
							.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
		}
        boolean first = false;

		//先判断是否绑定过。
		// 检查以前是否绑定过。
		ThirdUser third = null;
		if (Constants.RR.equals(type)) {
			third = thirdUserService.getByRrUid(third_uid);
		} else {
			third = thirdUserService.getBySinaUid(third_uid);
		}
		//判断user 是否为空
		User nuser = hostHolder.getUser();
		boolean re = false;
		//已经绑定的情况
		if(null != third){
			if(null == nuser){
				nuser = userService.query(third.getSc_uid());
				int uid = nuser.getId();
				updateThird(type,uid,third_token,third_refresh_token,nuser);
				third  = thirdUserService.getThirdUserByUid(uid);
				userService.updateSyc(nuser.getSycType(),nuser.getId());
				AccessToken at = AccessTokenManager.getInstance().getAccessToken(
						uid);
				return "@" + JsonResponse.formTokenResponse(at.getSessionKey(), at
                        .getSessionSecret(), BaseUtil.formThirdInfo(third), first, nuser);
			}
			//用户不为空的情况。比对uid
			if(nuser.getId() != third.getSc_uid()){
				//提示处错
				if (Constants.RR.equals(type)) {
					return "@" + JsonResponse
					.formFailResponse(Constants.ErrorCode.ALLREADY_RR_BINDING);
				}
				if (Constants.SINA.equals(type)) {
					return "@" + JsonResponse
					.formFailResponse(Constants.ErrorCode.ALLREADY_SINA_BINDING);
				}
			}
			 //update
			int uid = nuser.getId();
			updateThird(type,uid,third_token,third_refresh_token,nuser);
			third  = thirdUserService.getThirdUserByUid(uid);
			
			
			AccessToken at = AccessTokenManager.getInstance().getAccessToken(
					nuser.getId());
			if (Constants.RR.equals(type)) {
				nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_RR_SYCTYPE);
			}
				if (Constants.SINA.equals(type)) {
					nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_SINA_SYCTYPE);
			}
			userService.updateSyc(nuser.getSycType(),nuser.getId());
			return "@"
			+ JsonResponse.formTokenResponse(at.getSessionKey(), at
                    .getSessionSecret(), BaseUtil.formThirdInfo(third), first, nuser);
			
		}
		
		//未绑定的情况。
		if ( null != nuser){
			
			if (Constants.RR.equals(type)) {
				// add or update
				re = thirdUserService.createOrUpdateRenRen(nuser.getId(),
						third_uid, third_token, third_refresh_token) ;
			}
			if (Constants.SINA.equals(type)) {
				re = thirdUserService.createOrUpdateSina(nuser.getId(),
						third_uid, third_token, third_refresh_token); 
			}
			if (!re) {
				return "@" + JsonResponse
								.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
			}
			third = thirdUserService.getThirdUserByUid(nuser.getId());
			AccessToken at = AccessTokenManager.getInstance().getAccessToken(
					third.getSc_uid());
			if (Constants.RR.equals(type)) {
				nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_RR_SYCTYPE);
			}
				if (Constants.SINA.equals(type)) {
					nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_SINA_SYCTYPE);
			}
			userService.updateSyc(nuser.getSycType(),nuser.getId());
			return "@"
					+ JsonResponse.formTokenResponse(at.getSessionKey(), at
                    .getSessionSecret(), BaseUtil.formThirdInfo(third), first, nuser);
		}
		
		
		//nuser 为空的情况
		String user = null;
		String pass = null;
		if (Constants.RR.equals(type)) {
			user = generUser(Constants.RR_PRE);
			pass = generPass(Constants.RR_PRE);

		}
		if (Constants.SINA.equals(type)) {
			user = generUser(Constants.SINA_PRE);
			pass = generPass(Constants.SINA_PRE);
		}
		try {
			nuser = userService.createUser(user, pass);
			//追加默认姓名和头像
			String name = BaseUtil.generName("文艺小清新", nuser.getId());
			nuser.setName(name);
			nuser.setHeadurl("http://head.xiaonei.com/photos/0/0/page-tiny.png");
			nuser.setPhoneNumber("");
			nuser.setPushType(Constants.DEFAULT_PUSHTYPE);
			userService.update(nuser);
			first = true;
			//对新用户，默认关注光影DV官方账号
			attentionService.attention(nuser.getId(), Constants.RAYDV_UID);
			//更新索引用户数据
		} catch (Exception ex) {
			return "@"+ JsonResponse
							.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		}
		if (null == nuser) {
			return "@"
					+ JsonResponse
							.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		}
		if (Constants.RR.equals(type)) {
			// add or update
			re = thirdUserService.createOrUpdateRenRen(nuser.getId(),
					third_uid, third_token, third_refresh_token) ;
		}
		if (Constants.SINA.equals(type)) {
			re = thirdUserService.createOrUpdateSina(nuser.getId(),
					third_uid, third_token, third_refresh_token); 
		}
		if (!re) {
			return "@" + JsonResponse
							.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		}
		third = thirdUserService.getThirdUserByUid(nuser.getId());
		
		AccessToken at = AccessTokenManager.getInstance().getAccessToken(
				third.getSc_uid());
		if (Constants.RR.equals(type)) {
			nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_RR_SYCTYPE);
		}
			if (Constants.SINA.equals(type)) {
				nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_SINA_SYCTYPE);
		}
		userService.updateSyc(nuser.getSycType(),nuser.getId());
		return "@"
				+ JsonResponse.formTokenResponse(at.getSessionKey(), at
                .getSessionSecret(), BaseUtil.formThirdInfo(third), first, nuser);
	}

	@AccessTokenChecker
	@Post("unbind")
	public String unbind(Invocation inv, @Param("type")
	String type) {

		if (!BaseUtil.typeContains(type)) {
			return "@"
					+ JsonResponse
							.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
		}
		User nuser = hostHolder.getUser();
		// 3rd token 校验
		if (Constants.RR.equals(type)) {
			thirdUserService.clearRRInfoByUid(nuser.getId());
		}
		if (Constants.SINA.equals(type)) {
			thirdUserService.clearSinaInfoByUid(nuser.getId());
		}
		if (Constants.QQ.equals(type)) {
			thirdUserService.clearQQInfoByUid(nuser.getId());
		}
		return "@" + JsonResponse.formSuccessResponse();
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

	
	private boolean updateThird(String type, int uid, String third_token,
			String third_refresh_token,User nuser) {
		boolean re = false;
		if (Constants.RR.equals(type)) {
			// add or update
			re = thirdUserService.updateRenRen(uid, third_token,
					third_refresh_token);
			nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_RR_SYCTYPE);
		}
		if (Constants.SINA.equals(type)) {
			re = thirdUserService.updateSina(uid, third_token,
					third_refresh_token);
			nuser.setSycType(nuser.getSycType()|Constants.DEFAULT_SINA_SYCTYPE);
		}
		return re;
	}
}
