package com.renren.ntc.sg.interceptors;

import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.bean.User;
import com.renren.ntc.sg.dao.UserDAO;
import com.renren.ntc.sg.interceptors.access.NtcHostHolder;
import com.renren.ntc.sg.service.UserService;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.CookieManager;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Interceptor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private UserDAO userDAO;


    private static final Logger logger = LoggerFactory.getLogger(AccessCommonInterceptor.class);

    public AccessCommonInterceptor(){
    	setPriority(10000);
    }
    
    protected Class<? extends Annotation> getDenyAnnotationClass() {
        return DenyCommonAccess.class;
    }

    @Override
    protected Object before(Invocation inv) throws Exception {
    	String path = inv.getRequest().getRequestURI() ;
        User u = null    ;
        String uuid  = CookieManager.getInstance().getCookie(inv.getRequest(), Constants.COOKIE_KEY_USER);
        if(null  != uuid) {
            u = userDAO.getUser(Long.valueOf(uuid));
        }
        if( null == u ) {
            String userName = SUtils.generName();
            u  = userService.createUser(userName , 0,  "pwd", 1 );
            CookieManager.getInstance().saveCookie(inv.getResponse(), Constants.COOKIE_KEY_USER,u.getId()+ "" , -1 , "/");
        }

        hostHolder.setUser(u);
        return true;
	}
    
    protected Object after(Invocation inv, Object instruction) throws Exception {

        return instruction;
    }

}