package com.renren.ntc.video.interceptors;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.utils.BaseUtil;
import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
*
* 标注:{@link LoginRequiredInterceptor}
*
* @author [yunming.zhu@opi-corp.com]
*
* @date Mar 25, 2011 02:40:40 AM
*
*/

public class LoginRequiredInterceptor extends ControllerInterceptorAdapter {

	@Autowired
	private NtcHostHolder hostHolder;

    private static final Logger logger = Logger.getLogger(LoginRequiredInterceptor.class);


	private static enum displayMode  {
		page, popup, mobile, touch, iframe, hidden
	};

	public LoginRequiredInterceptor(){
		setPriority(1000);
	}

	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginRequired.class;
	}

	@Override
	public List<Class<? extends Annotation>> getRequiredAnnotationClasses() {
	        Class clazz = LoginRequired.class;
	        List<Class<? extends Annotation>> list = new ArrayList<Class<? extends Annotation>>(2);
	        list.add(clazz);
	        return list;
	}

	@Override
	public Object before(Invocation inv) throws Exception {
        User user = hostHolder.getUser();
        if (user == null) {
            String isAjax = inv.getParameter("isAjax");
            if(isAjax != null && isAjax.equals("true")) {
                return "@redirect_to:/login";
            }
            
            String from56 = inv.getParameter("from56");
            if("true".equals(from56)){//返回json数据
            	JSONObject jObject = new JSONObject();
            	jObject.put("status", "-1");
            	jObject.put("msg", "尚未登陆");
            	jObject.put("origURL", inv.getParameter("origURL"));
            	return "@" + jObject;
            }
            //同意过滤掉origURL的参数以免请求人人oauth的时候出错,written by dapeng.zhou
            String origURL = BaseUtil.getResourceFullLocation(inv.getRequest());
            logger.info("the origURL is "+origURL);
//            if(origURL.indexOf("%3F") != -1){
//            	origURL = origURL.substring(0, origURL.indexOf("%3F"));
//            }
            //end
            return "r:" + "/login?rf=r&domain="
                    + NtcConstants.DOMAIN_URL + "&origURL="
                    + origURL;
        }
        return true;
	}

}
