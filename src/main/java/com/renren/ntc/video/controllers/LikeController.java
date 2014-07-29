package com.renren.ntc.video.controllers;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.EntityUserService;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.EventUtil;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

@LoginRequired
@Path("like")
public class LikeController {

	@Autowired
	private EntityUserService eService;

	@Autowired
	private NtcHostHolder ntcHostHolder;

	
	@Autowired
	private UserService userService;

	/**
	 * 用于处理用户喜欢和不喜欢的ajax请求，成功返回1，失败返回0
	 * 
	 * @param shareId 分享id
	 * @param method 喜欢还是不喜欢
	 * @return
	 */
	@Post("")
	public String like(@Param("shareId")long shareId,
                       @Param("method")String method) {
		User user = ntcHostHolder.getUser();
		if("like".equals(method)){
				return "@0";
		}
		if("unlike".equals(method)){
				return "@0";
		}
		return "@0";
	}
}
