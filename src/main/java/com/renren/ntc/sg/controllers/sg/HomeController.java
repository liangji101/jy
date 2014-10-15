package com.renren.ntc.sg.controllers.sg;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.EntityUserService;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

@Path("")
public class HomeController {

	/**
	 * 用于处理用户喜欢和不喜欢的ajax请求，成功返回1，失败返回0
	 * @return
	 */
    @Get("")
	@Post("")
	public String index(@Param("shop_id")long shop_id) {

        return "minShop";

	}
}
