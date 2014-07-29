package com.renren.ntc.video.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.ImageSizeUtil;
import com.renren.ntc.video.utils.JsonResponse;
import com.renren.ntc.video.utils.ImageSizeUtil.ImageSize;
import com.renren.ntc.video.utils.renren.utils.HttpURLUtils;

@Path("")
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	
    @Get("")
    public String index(Invocation inv){
        String userAgent = inv.getRequest().getHeader("user-agent");
        logger.info("user agent: " + userAgent);
        if(userAgent!= null && userAgent.contains("iPhone")) {
            return "iphone_index.vm";
        }
        return "index.vm";
    }
    
    @Get("download")
    public String raydvDownload(@Param("ref")String ref){
    	if(ref == null){
    		ref = "没有传ref参数";
    	}
    	logger.info("***---raydv download---下载光影DV的ref是："+ref);
    	return "r:http://itunes.apple.com/cn/app/guang-yingdv/id552718710?ls=1&mt=8";
    }
    
    @Get("musicDownload")
    public String musicDownload(@Param("ref")String ref){
    	if(ref == null){
    		ref = "没有传ref参数";
    	}
    	logger.info("***---music download---下载人人电台的ref是："+ref);
    	return "r:http://itunes.apple.com/cn/app/ren-ren-dian-tai/id546611769?ls=1&mt=8";
    }
    
    @Get("commentme")
    public String commentme(){
    	logger.info("***commentme");
    	return "r:http://itunes.apple.com/cn/app/guang-yingdv/id552718710?ls=1&mt=8";
    }

    @Get("iphone")
    public String iphoneIndex(){
        return "iphone_index.vm";
    }

    @Get("iphone/dev")
    public String iphoneDevIndex(){
        return "iphone_dev_index.vm";
    }

    @Get("resourceNotFound")
    public String resourceNotFound(Invocation inv){
    	String userAgent = inv.getRequest().getHeader("user-agent");
    	if(userAgent!= null && userAgent.contains("RRCam")) {
    		return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.FAIL);
        }
    	return "404";
    }

    @Get("serverError")
    public String serverError(){
    	return "500";
    }

    @Get("videoNotFound")
    public String videoNotFound(){
        return "video_not_found";
    }

    @Get("userNotFound")
    public String userNotFound(){
        return "user_not_found";
    }
    
    @Get("videoViewDeny")
    public String videoNotCommitted(){
    	return "video_not_committed";
    }

    @Get("renren")
    public String renren() {
        return "renren";
    }
}
