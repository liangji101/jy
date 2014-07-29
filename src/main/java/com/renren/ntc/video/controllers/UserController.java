package com.renren.ntc.video.controllers;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.formbean.UserInfo;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.*;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Time: 下午3:10
 */
@LoginRequired
@Path("")
public class UserController {
	@Autowired
    EntityUserService eService;

	@Autowired
    CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private NtcHostHolder hostHolder;

	@Autowired
	private RelationShipService relationShip;


	private enum HomeValue {
		VIDEOS, FOCUSES, FANS, LIKES, MARKS;
	}

	private static Logger logger = Logger.getLogger(UserController.class);

	@Get({ "{userId:[0-9]+}", "{userId:[0-9]+}/video" })
	public String userHome(Invocation inv, @Param("userId") int uid) {
		if (userService.query(uid) == null) {
			logger.error("访问的用户信息uid:" + uid + "不存在");
			return "r:/userNotFound";
		} else {
			formCounts(inv, uid);
			inv.addModel("value", HomeValue.VIDEOS.ordinal());
			return "home";
		}
	}

	@Get("{userId:[0-9]+}/focus")
	public String newFocus(Invocation inv, @Param("userId") int uid) {

		if (userService.query(uid) == null) {
			logger.error("访问的用户focus信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			formCounts(inv, uid);
			inv.addModel("value", HomeValue.FOCUSES.ordinal());
			return "home";
		}
	}

	@Get("{userId:[0-9]+}/fans")
	public String fans(Invocation inv, @Param("userId") int uid) {

		if (userService.query(uid) == null) {
			logger.error("访问的用户fans信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			formCounts(inv, uid);
			inv.addModel("value", HomeValue.FANS.ordinal());
			return "home";
		}
	}

	@Get("{userId:[0-9]+}/likes")
	public String loves(Invocation inv, @Param("userId") int uid) {

		if (userService.query(uid) == null) {
			logger.error("访问的用户likes信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			formCounts(inv, uid);
			inv.addModel("value", HomeValue.LIKES.ordinal());
			return "home";
		}
	}

	@Get("{userId:[0-9]+}/marks")
	public String mark(Invocation inv, @Param("userId") int uid) {

		if (userService.query(uid) == null) {
			logger.error("访问的用户mark信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			formCounts(inv, uid);
			inv.addModel("value", HomeValue.MARKS.ordinal());
			return "home";
		}
	}

	/**
	 * 获取视频数、喜欢数，关注数，被关注数，mark数
	 * 
	 * @param inv
	 * @param uid
	 */
	private void formCounts(Invocation inv, int uid) {
		inv.addModel("likeCount", eService.getLikeCount(uid));
		inv.addModel("markCount", eService.getMarkCount(uid));
		inv.addModel("fanCount", relationShip.getPassAttentionsCount(uid));
		inv.addModel("foucusCount", relationShip.getActiveAttentionsCount(uid));
        if(hostHolder.getUser().getId() != uid) {
            User currentViewUser = userService.query(uid);
            //查看别人的视频，添加是否已经关注
            inv.addModel("userInfo", userService.parseUserToUserInfo(currentViewUser, hostHolder.getUser()));
            inv.addModel("user", currentViewUser);
        } else {
            inv.addModel("user", userService.query(hostHolder.getUser().getId()));
        }
	}

	@Get("{userId:[0-9]+}/video/more")
	public String nextVideos(Invocation inv, @Param("userId") int uid,
			@Param("offset") int offset, @Param("count") int count) {

		if (0 >= offset) {
			offset = 0;
		}
		if (0 >= count || 6 < count) {
			count = 6;
		}
		if (userService.query(uid) == null) {
            logger.error("访问的用户vide/more信息不存在,uid:" + uid);
            return "r:/resourceNotFound";
        } else {
			inv.addModel("userId", uid);
			User user = hostHolder.getUser();
			boolean canEdit = true;
			if(user==null || user.getId()!=uid) {
				canEdit=false;
			}
			inv.addModel("canEdit",canEdit);
			return "home_video.vm";
		}
	}

	@Get("{userId:[0-9]+}/likes/more")
	public String likes(Invocation inv, @Param("userId") int uid,
			@Param("offset") int offset, @Param("count") int count) {

		if (0 >= offset) {
			offset = 0;
		}
		if (0 >= count || 6 < count) {
			count = 6;
		}
		List<Long> loveIds = eService.getLikeEnId(uid, 1, offset, count);
		if (userService.query(uid) == null) {
			logger.error("访问的用户likes/more信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			inv.addModel("userId", uid);
			return "home_likes.vm";
		}
	}

	@Get("{userId:[0-9]+}/marks/more")
	public String marks(Invocation inv, @Param("userId") int uid,
			@Param("offset") int offset, @Param("count") int count) {

		if (0 >= offset) {
			offset = 0;
		}
		if (0 >= count || 6 < count) {
			count = 6;
		}
		List<Long> markIds = eService.getMarkEnId(uid, 1, offset, count);
		if (userService.query(uid) == null) {
			logger.error("访问的用户marks/more信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			inv.addModel("userId", uid);
			return "home_marks.vm";
		}
	}

	@Get("{userId:[0-9]+}/focus/more")
	public String nextFocus(Invocation inv, @Param("userId") int uid,
			@Param("offset") int offset, @Param("count") int count) {

		// User user = hostHolder.getUser();
		User user = new User();
		user = userService.query(uid);

		if (offset < 0) {
			offset = 0;
		}
		if (count <= 0 || count > 6) {
			count = 8;
		}
		// 获得关注中对象
		List<Integer> ls = relationShip.getActiveAttentions(user.getId(),
				offset, count);
		logger.info("" + ls.size() + "++++++++++" + ls.toString());
		List<User> ll = userService.queryByUserIds(ls);
		List<UserInfo> userList = userService.parseUserListToUInfo(ll, user);
		if (userService.query(uid) == null) {
			logger.error("访问的用户focus/more信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			inv.addModel("UserList", userList);
			inv.addModel("userId", uid);
			return "home_focus.vm";
		}
	}

	@Get("{userId:[0-9]+}/fans/more")
	public String nextFans(Invocation inv, @Param("userId") int uid,
			@Param("offset") int offset, @Param("count") int count) {

		// User user = hostHolder.getUser();
		User user = new User();
		user = userService.query(uid);

		if (offset < 0) {
			offset = 0;
		}
		if (count <= 0 || count > 6) {
			count = 8;
		}

		// 获得关注者对象
		List<Integer> ls = relationShip.getPassAttentions(user.getId(), offset,
				count);
		List<User> ll = userService.queryByUserIds(ls);
		List<UserInfo> userList = userService.parseUserListToUInfo(ll, user);
		if (userService.query(uid) == null) {
			logger.error("访问的用户fans/more信息不存在,uid:" + uid);
			return "r:/resourceNotFound";
		} else {
			inv.addModel("UserList", userList);
			inv.addModel("userId", uid);
			return "home_fans.vm";
		}
	}

	@Get("{userId:[0-9]+}/video/cancerLike")
	public String cancelVideoLike(@Param("userId") int uid,
			@Param("shareId") long shareId) {
		if (uid == 0 || shareId == 0) {
			return false + "";
		} else {
			boolean flag = eService.unlike(uid, shareId, 1);
			return "" + flag;
		}
	}

 

}
