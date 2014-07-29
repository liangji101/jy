package com.renren.ntc.video.controllers.api.login;


import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.AccessToken;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.ThirdUserService;
import com.renren.ntc.video.service.mgr.AccessTokenManager;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 *   yunming.zhu
 */
@Path("")
public class LoginController implements NtcConstants {

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ThirdUserService thirdUserService;

	
	@Post("")
	public String login(Invocation inv,
                        @Param("app_id") int appId,
                        @Param("email") String email,
                        @Param("passwd") String passwd) {
		
		if(appId != 11520){
			return "@" + JsonResponse
			.formFailResponse(Constants.ErrorCode.APP_ID_INVALID);
		}
		if(!BaseUtil.islegal(email) || !email.matches(".*@.*\\..*")){
			return "@" + JsonResponse
			.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "email");
		}
		
		User user = hostHolder.getUser();
		if (null != user){
			ThirdUser tu = thirdUserService.getThirdUserByUid(user.getId());
			AccessToken at = AccessTokenManager.getInstance().getAccessToken(user.getId());
			return "@" + JsonResponse.formTokenResponse(at.getSessionKey(), at.getSessionSecret(), BaseUtil.formThirdInfo(tu), user.getSycType());
		}
	
		user = userService.CheckUserPassport(email, passwd);
		if (null == user) {
			return "@" + JsonResponse
					.formFailResponse(Constants.ErrorCode.USER_PASSWD_INVALID);
		}

		ThirdUser tu = thirdUserService.getThirdUserByUid(user.getId());
		AccessToken at = AccessTokenManager.getInstance().getAccessToken(user.getId());
		return "@" + JsonResponse.formTokenResponse(at.getSessionKey(), at.getSessionSecret(), BaseUtil.formThirdInfo(tu), false, user);
	}
	
	@Post("newPass")
	public String newPass(Invocation inv,
                          @Param("app_id") int appId,
                          @Param("email") String email,
                          @Param("passwd") String passwd,
                          @Param("newpasswd") String newpasswd) {

		if(appId != 11520){
			return "@" + JsonResponse
			.formFailResponse(Constants.ErrorCode.APP_ID_INVALID);
		}
		
		if(!BaseUtil.islegal(email) || !email.matches(".*@.*\\..*")){
			return "@" + JsonResponse
			.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "email");
		}
		
		if (!BaseUtil.islegal(passwd)||!BaseUtil.islegal(newpasswd)) {
			return "@" + JsonResponse
					.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, " passwd  or newpasswd");
		}
		
		User user = userService.CheckUserPassport(email, passwd);
		if (null == user) {
			return "@" + JsonResponse
					.formFailResponse(Constants.ErrorCode.USER_PASSWD_INVALID);
		}
		int re = userService.updatePass(user.getId(),newpasswd);
		if (re !=1){
			return "@" + JsonResponse
			.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		} 
		return "@" + JsonResponse.formSuccessResponse();
	}
	
}
