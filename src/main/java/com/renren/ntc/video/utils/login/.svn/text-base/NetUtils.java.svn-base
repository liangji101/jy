package com.renren.ntc.video.utils.login;

import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.model.SessionKeyInfo;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.utils.ApiSignatureUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.SKUtil;
import net.paoding.rose.web.Invocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


public class NetUtils implements NtcConstants {
	
	private static  Log logger = LogFactory.getLog(NetUtils.class );
	
    public static final String getUrlWithQueryString(HttpServletRequest request) {
        String url = request.getRequestURI();// path,不含schema、host、port及query
        String queryString = request.getQueryString();
        if (StringUtils.isEmpty(url)) {
            url = "/";
        }
        if (StringUtils.isNotEmpty(queryString)) {
            url += "?" + queryString;
        }
        return url;
    }

    public static final UserToken getUserTokenFromCookie(HttpServletRequest request) {
        UserToken userToken = new UserToken();
        userToken.setUserId(NumberUtils.toInt(CookieManager.getInstance().getCookie(request, COOKIE_KEY_USER), 0));
        userToken.setToken(CookieManager.getInstance().getCookie(request, COOKIE_KEY_TOKEN));
        return userToken;
    }

    public static final String getTokenFromCookie(HttpServletRequest request) {
        return CookieManager.getInstance().getCookie(request, COOKIE_KEY_TOKEN);
    }

    public static final void clearTokenFromCookie(HttpServletResponse response) {
        CookieManager.getInstance().clearCookie(response, COOKIE_KEY_USER, -1, "/" , DOMAIN_URL);
        CookieManager.getInstance().clearCookie(response, COOKIE_KEY_TOKEN, -1, "/", DOMAIN_URL);
    }


    public static final void setUserTokenToCookie(HttpServletResponse response, UserToken token) {
        if (token == null) {
            clearTokenFromCookie(response);
            return;
        }
        Date expiredTime = token.getExpiredTime();
        long seconds = -1;
        if (expiredTime != null) {
            seconds = expiredTime.getTime()/1000 - System.currentTimeMillis() / 1000;
            if (seconds > Integer.MAX_VALUE) {
                seconds = Integer.MAX_VALUE;
            }
            seconds -= 60;
        }
        CookieManager.getInstance().saveCookie(response, COOKIE_KEY_TOKEN, token.getToken(), (int) seconds, "/", NtcConstants.DOMAIN_URL);
		CookieManager.getInstance().saveCookie(response, COOKIE_KEY_USER, String.valueOf(token.getUserId()), Long.valueOf(seconds).intValue(), "/", NtcConstants.DOMAIN_URL);
    }

	public static SessionKeyInfo getMobileToken(Invocation inv) {
		String token = inv.getParameter(Constants.TOKEN);
		SessionKeyInfo ski  = SKUtil.parseInfiniteSessionKey(token);
		return  ski;
	}
	
	
	
	
	public static boolean checkSig(HttpServletRequest request,String secretKey) {
		
		List<String> result = new ArrayList<String>(6);//先设置为6的大小，后续自动增长
		Enumeration<String>  en =  request.getParameterNames();
		String sig = null;
		while (en.hasMoreElements()) {
			String element = (String) en.nextElement();
			if (Constants.SIG.equals(element)) {
				sig = request.getParameter(element);
				continue;
			}
			result.add(element + "=" + request.getParameter(element));

		}
		boolean isSig = ApiSignatureUtil.getInstance().verifySignature(result,secretKey,sig);
		if(!isSig){
			logger.error("---sig="+sig+"------未通过------序列--" +  result.toString());
		}
	    return isSig;
}	
}
