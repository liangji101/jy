package com.renren.ntc.video.utils.renren.services;

import com.renren.ntc.video.utils.renren.RenrenApiInvoker;
import com.renren.ntc.video.utils.renren.param.Auth;
import net.sf.json.JSONObject;

import java.util.TreeMap;


/**
 * 状态
 * @author DuYang (yang.du@renren-inc.com) 2011-12-14
 *
 */
public class LikeService extends BaseService {

    public static final String TYPE_ALL    = "all";

    public static final String TYPE_RECENT = "recent";

    public LikeService(RenrenApiInvoker invoker) {
        super(invoker);
        // TODO Auto-generated constructor stub
    }

    public void like(String url,Auth auth) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put(auth.getKey(), auth.getValue());
        params.put("method", "like.like");
        params.put("url", url);
        JSONObject json = this.getResultJSONObject(params);
    }

    
}
