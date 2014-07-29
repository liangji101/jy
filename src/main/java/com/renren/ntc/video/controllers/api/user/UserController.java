package com.renren.ntc.video.controllers.api.user;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.EmojiEncoder;
import com.renren.ntc.video.utils.JsonResponse;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * yunming.zhu
 */
@AccessTokenChecker
@Path("")
public class UserController {

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private UserService userService;
	
	
	private static Log logger = LogFactory.getLog(UserController.class);

	@Post("getInfo")
	public String getInfo(Invocation inv, @Param("uid") int uid) {
	
		User user = hostHolder.getUser();
		int hostId  = user.getId();
		if (0 == uid) {
			JSONObject info = userService.paserUser(user,hostId,true);
			return "@" + JsonResponse.formComplexResponse(info);
		}
		User u = userService.query(uid);
		JSONObject  ob = userService.paserUser(u,hostId,true);
		return "@" + JsonResponse.formComplexResponse(ob);
	}

	@Post("updateHeadurl")
	public String updateHeadurl(Invocation inv,
			@Param("headurl") String headurl) {
		User user = hostHolder.getUser();
		if (!BaseUtil.islegal(headurl)){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
		}
		if(!headurl.matches("^(http|https)://.*")){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, headurl);
			
		}
		//改头像后热门用户表也需要更新
		int uid = user.getId();
		int re = userService.updateHeadurl(uid,headurl);
		int result = 1;
		if (re != 1 || result != 1){
			return "@" +  JsonResponse.formFailResponse(Constants.ErrorCode.FAIL);
		}
		return "@" + JsonResponse.formSuccessResponse();
		
	}
	@Post("updateInfo")
	public String updateUserInfo(Invocation inv,
			@Param("headurl") String headurl, @Param("name") String name,
			@Param("phoneNumber")  String phoneNumber) {

		User user = hostHolder.getUser();
		if (!BaseUtil.islegal(headurl) || !BaseUtil.islegal(name)){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.HEAD_NAME_INVALID);
		}
		if(!BaseUtil.islegal(phoneNumber)){
			phoneNumber = "";
		}
		headurl = headurl.trim();
		name = name.trim().replaceAll(" ", "");
		if(!headurl.matches("^(http|https)://.*")){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.HEAD_NAME_INVALID);
		}
        user.setHeadurl(headurl);
        user.setName(EmojiEncoder.getInstance().convert(name));
        user.setPhoneNumber(phoneNumber);
		logger.info("*****************************************************更改用户信息！");
		int re = userService.update(user);
		int j = 1;
		//用户更新个人信息后同时更新rec_user表中的信息
		if (re != 1 || j != 1){
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.FAIL);
		}
		return "@" + JsonResponse.formSuccessResponse();
	}

    @Post("/updateAttPrivacy")
    public String updateAttPrivacy(Invocation inv, @Param("att_privacy") int privacy) {
        int result = userService.updateAttPrivacy(hostHolder.getUser().getId(), privacy);
        if( result != 1) {
            return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.FAIL);
        }
        return "@" + JsonResponse.formSuccessResponse();
    }
    
    
    /**
     * 0 关闭同步按钮  1 同步到人人 2 同步新浪  3 同步到人人又同步到新浪 
     * @param inv
     * @param syc
     * @return
     */
    @Post("/updateSyc")
    public String updateSyc(Invocation inv, @Param("syc") int syc) {
    	
    	
    	User user = hostHolder.getUser();
        if(syc < 0){
        	  return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "syc");
        }
    	userService.updateSyc(syc, user.getId());
        return "@" + JsonResponse.formSuccessResponse();
    }


    /**
     * 0 关闭同步按钮  1 同步到人人 2 同步新浪  3 同步到人人又同步到新浪 
     * @param inv
     * @param syc
     * @return
     */
    @Post("/getSycInfo")
    public String getSycInfo(Invocation inv) {
    	User user = hostHolder.getUser();
    	int syc = user.getSycType();
    	JSONObject jb = new JSONObject();
    	jb.put("syc", user.getSycType());
    	return "@" + JsonResponse.formComplexResponse(syc);
    }
	
	
	private List<Integer> cast(String uids) {
		List<Integer> ll = new ArrayList<Integer>();
		try {
			String[] us = uids.split(",");
			for (String u : us) {
				ll.add(Integer.valueOf(u));

			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return Collections.emptyList();
		}
		return ll;
	}
	
}
