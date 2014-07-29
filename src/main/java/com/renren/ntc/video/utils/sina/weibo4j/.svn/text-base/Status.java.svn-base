package com.renren.ntc.video.utils.sina.weibo4j;

import com.renren.ntc.video.utils.sina.weibo4j.model.PostParameter;
import com.renren.ntc.video.utils.sina.weibo4j.model.WeiboException;
import com.renren.ntc.video.utils.sina.weibo4j.util.WeiboConfig;

public class Status {
	
	
	public int update(String status)
			throws WeiboException {
		
		Weibo.client.post(
				WeiboConfig.getValue("baseURL") + "statuses/update.json",
				new PostParameter[] { new PostParameter("status",
						status) });
		return 1;
	}
	
	public int update_url_text(String status,String picurl)
	throws WeiboException {

        Weibo.client.post(
		WeiboConfig.getValue("baseURL") + "statuses/upload_url_text.json",
		new PostParameter[] { new PostParameter("status",
				status) , new PostParameter("url",picurl)});
       return 1;
    }
	
}
