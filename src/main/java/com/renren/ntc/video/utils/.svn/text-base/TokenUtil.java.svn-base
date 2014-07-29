package com.renren.ntc.video.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class TokenUtil {

    public static final String VIDEO_56_KEY = "gbvKKb4N3KwswuBLP5q@EHl^kjA?@1qz";

    public static final String VIDEO_56_MOBILE_KEY = "1evyP3(HtI.e9tau)77]UNI[X9]*p";

    /**
     * 生成Token
     * 
     * @param args
     * @param key
     * @return
     */
    public static String generateToken(Map<String, String> args, String key) {
        List<String> keys = new ArrayList<String>();
        keys.addAll(args.keySet());
        // 参数自然排序
        Collections.sort(keys);
        // key + value连接参数
        StringBuffer sb = new StringBuffer();
        for (String k : keys) {
            sb.append(k);
            sb.append(args.get(k));
        }
        String token = MD5.digest(sb.toString());
        token = MD5.digest(token + "." + key);
        return token;
    }

    /**
     * 生成3GToken
     * 
     * @param args
     * @param key
     * @return
     */
    public static String generateMobileToken(Map<String, String> args, String key) {
        List<String> keys = new ArrayList<String>();
        keys.addAll(args.keySet());
        Collections.sort(keys);
        StringBuffer sb = new StringBuffer();
        for (String k : keys) {
            //sb.append(k);
            sb.append(args.get(k));
        }
        sb.append(key);
        String token = MD5.digest(sb.toString());
//      String s = "renren_mobileaviimei-23refhtc hd234567132392049494ccac4ae691230de4d2d454738afa382021evyP3(HtI.e9tau)77]UNI[X9]*p";
//      System.out.println(MD5.digest(s));
        return token;
    }

    /**
     * 验证Token
     * 
     * @param args
     * @param key
     * @param token
     * @return
     */
    public static boolean valedateToken(Map<String, String> args, String key, String token) {
        String rightToken = generateToken(args, key);
        return rightToken.equals(token) ? true : false;
    }

}
