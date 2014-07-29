package com.renren.ntc.video.controllers;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.utils.login.NetUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import org.springframework.beans.factory.annotation.Autowired;


@LoginRequired
@Path("logout")
public class LogoutController {
	@Autowired
	private NtcHostHolder ntcHostHolder;
	@Get("")
	public String exit(Invocation inv){
		ntcHostHolder.setUser(null);
		NetUtils.clearTokenFromCookie(inv.getResponse());
		return "r:/";
	}
}
