package com.renren.ntc.video.utils.renren.services;

import com.renren.ntc.video.utils.renren.RenrenApiInvoker;

import java.util.TreeMap;


public class PageService extends BaseService {

    public PageService(RenrenApiInvoker invoker) {
        super(invoker);
        // TODO Auto-generated constructor stub
    }

    /**
     * 判断用户是否为Page的粉丝
     * @param userId 用户的ID
     * @param pageId Page的ID
     * @return
     */
    public boolean isFan(int userId, int pageId) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("method", "pages.isFan");
        params.put("page_id", String.valueOf(pageId));
        params.put("uid", String.valueOf(userId));
        return this.getResultBoolean(params);
    }

}
