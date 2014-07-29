package com.renren.ntc.video.biz;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author 张浩 E-mail:hao.zhang@renren-inc.com
 * @date 2012-5-30 下午04:06:38
 *
 * NTC项目的一些常量
 */

public interface NtcConstants {

    static final String                     COOKIE_KEY_USER           = "u";
    static final String                     COOKIE_KEY_TOKEN          = "t";
    // webhost在server_instance.xml中配置
    static final String                     PROFILE_CONF_HOST         = (String) ObjectUtils.defaultIfNull(StringUtils.trimToNull(System.getProperty("webhost")), "who");
//    String                     PROFILE_MAIN_DOMAIN       = "uume.com";
    static final String 					DB_CATALOG                = "";
    static final String                     DOMAIN                       = "jy.com";
    static final String                     DOMAIN_URL                = "www.jy.com";
    int                        MAX_LOGIN_DAYS            = 365;
}
