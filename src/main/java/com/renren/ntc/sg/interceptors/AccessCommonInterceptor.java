package com.renren.ntc.sg.interceptors;

import com.renren.ntc.sg.annotations.DenyCommonAccess;
import com.renren.ntc.sg.bean.User;
import com.renren.ntc.sg.interceptors.access.NtcHostHolder;
import com.renren.ntc.sg.service.UserService;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Interceptor;
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
        User user = null;
        if(null  != user) {
            hostHolder.setUser(user);
        }
        return true;
	}
    
    protected Object after(Invocation inv, Object instruction) throws Exception {

        return instruction;
    }

}
