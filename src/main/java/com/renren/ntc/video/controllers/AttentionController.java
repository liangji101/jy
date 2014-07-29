package com.renren.ntc.video.controllers;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.service.AttentionService;
import com.renren.ntc.video.service.CommentService;
import com.renren.ntc.video.service.EntityUserService;
import com.renren.ntc.video.service.RelationShipService;

@LoginRequired
@Path("")
public class AttentionController {

	@Autowired
	private  EntityUserService eService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private RelationShipService relationShip;

    @Autowired
    private AttentionService attentionService;

	@Get("/attention/add")
	public int addAttention(@Param("userId") int uid,
                            @Param("target") int target) {
		if (uid == target) {
			return 0;
		}

        if(userService.query(uid) ==null) {
			return 0;
		}

		User targetUser = userService.query(target);
		if (null == targetUser) {
			return 0;
		}

		return attentionService.attention(uid, target).ordinal();
	}

	@Get("{userId:[0-9]+}/video/cancerAttention")
	public int cancerAttention(@Param("userId") int uid,
                               @Param("target") int target) {
		if (uid == target) {
			return 2;
		}
		User user = userService.query(uid);
		if(user==null) {
			return 2;
		}
		User tt = userService.query(target);
		if (null == tt) {
			return 2;
		}
		relationShip.delAttention(uid, target);
		int can = relationShip.isAttention(uid, target) ? 1 : 0;
	
		return can;
	}
}
