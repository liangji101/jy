package com.renren.ntc.video.interceptors;

import com.renren.ntc.video.annotations.DenyCommonAccess;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.logic.UserTokenService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.ThirdUserService;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;
import com.renren.ntc.video.utils.MemcachedUtil;
import com.renren.ntc.video.utils.login.CookieManager;
import com.renren.ntc.video.utils.login.NetUtils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Interceptor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@Interceptor(oncePerRequest = true)
public class AccessCommonInterceptor extends ControllerInterceptorAdapter {

    @Autowired
    private NtcHostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private ThirdUserService thirdUserService;

    
    private static final Logger logger = Logger.getLogger(AccessCommonInterceptor.class);

    public AccessCommonInterceptor(){
    	setPriority(10000);
    }
    
    protected Class<? extends Annotation> getDenyAnnotationClass() {
        return DenyCommonAccess.class;
    }

    @Override
    protected Object before(Invocation inv) throws Exception {
    	String path = inv.getRequest().getRequestURI() ;
    	if(path.startsWith("/api")){
            logger.info(String.format("iphone user: %s; mac: %s; visit path: %s", inv.getParameter(Constants.TOKEN), inv.getParameter(Constants.CLIENT_MAC), path));
    		return true;
    	}
        User user = null;
        UserToken requestToken = NetUtils.getUserTokenFromCookie(inv.getRequest());
        int userId = requestToken.getUserId();
        if (userId > 0 && userTokenService.isValid(requestToken)) {
            user = userService.query(userId);// 登录成功
        }

        if(user != null) {
            hostHolder.setUser(user);
        }
        return true;
	}
    
    protected Object after(Invocation inv, Object instruction) throws Exception {
    	String path = inv.getRequest().getRequestURI() ;
    	if(path.startsWith("/api")){
    	   inv.getResponse().setHeader("Content-Type", "text/json; charset=UTF-8");
    	   return instruction;
    	}
        return instruction;
    }

}
