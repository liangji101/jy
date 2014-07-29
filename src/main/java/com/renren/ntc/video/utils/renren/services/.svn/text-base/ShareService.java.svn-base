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
public class ShareService extends BaseService {

    public static final String TYPE_ALL    = "all";

    public static final String TYPE_RECENT = "recent";

    public ShareService(RenrenApiInvoker invoker) {
        super(invoker);
        // TODO Auto-generated constructor stub
    }

    public void share(String url,String message,Auth auth) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put(auth.getKey(), auth.getValue());
        params.put("method", "share.share");
        //params.put("comment", message);
        params.put("url", url);
        params.put("type", 10 + "");
        this.getResultJSONObject(params);
    }
    
    protected JSONObject getResultJSONObject(TreeMap<String, String> params) {
        String ret = this.invoker.sendPostRestRequest(params);
        System.out.println("//return--- " + ret);
        return JSONObject.fromObject(ret);
    }

    
}
