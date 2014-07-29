package com.renren.ntc.video.interceptors;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.SessionKeyInfo;
import com.renren.ntc.video.biz.model.SessionKeyValidationResult;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.mgr.AccessTokenManager;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;
import com.renren.ntc.video.utils.SKUtil;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

public class AccessTokenCheckerInterceptor extends ControllerInterceptorAdapter {

    @Autowired
    private NtcHostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return AccessTokenChecker.class;
	}
    
    @Override
    protected Object before(Invocation inv) throws Exception {
    	AccessTokenChecker annotation = inv.getControllerClass().getAnnotation(AccessTokenChecker.class);
		User user = null;
		String  appId = inv.getParameter(Constants.APP_ID);
		String  token = inv.getParameter(Constants.TOKEN);
		if (annotation == null) {
             annotation  = inv.getMethod().getAnnotation(AccessTokenChecker.class);
        }
		int id = 0 ;
		try{
		    id = Integer.valueOf(appId);
		}catch(Exception e ){
			e.printStackTrace();
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.ACCESSTOKEN_INVALID);
		}
		if(null == token){

			if(!annotation.LoginRequired()){
				return true;
			}
			
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.ACCESSTOKEN_INVALID);
			
		}
		SessionKeyValidationResult re = AccessTokenManager.getInstance().validateSessionKey(id, token);
		
		if(SessionKeyValidationResult.SK_VALIDATION_OK != re.getValidationCode()){
			
			     return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.TOKEN_INVALID);
		}
		String ss = AccessTokenManager.getInstance().getSessionSecret(appId, token);
		
//		if(!NetUtils.checkSig(inv.getRequest(),ss)){
//			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
//		}
		SessionKeyInfo skInfo = SKUtil.parseInfiniteSessionKey(token);
		user = userService.query(skInfo.getUid());
		if(null == user){
	        	return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.ACCESSTOKEN_INVALID);
		}
		hostHolder.setUser(user);
		return true;
	}
    
}