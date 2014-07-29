package com.renren.ntc.video.controllers.api.like;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.EntityUserService;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.EventUtil;
import com.renren.ntc.video.utils.JsonResponse;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 */

@AccessTokenChecker
@Path("")
public class LikeController {

	@Autowired
	EntityUserService eService;

	@Autowired
	private NtcHostHolder hostHolder;




	@Get("like")
	@Post("like")
	public String add(@Param("en_id") long eid, @Param("en_type") int en_type, @Param("des_uid") int desUid,
			@Param("app_verStr") long v) throws Throwable {

		User user = hostHolder.getUser();
		if (eid == 0) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "en_id");
		}

		return "@" + JsonResponse.formSuccessResponse();
	}

	@Get("/unlike")
	@Post("/unlike")
	public String delete(Invocation inv, @Param("en_id") long en_id, @Param("en_type") int en_type) throws Throwable {
		User user = hostHolder.getUser();
		if (en_id == 0 || en_type == 0) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.LIKE_ERROR);
		}
		boolean flag = eService.unlike(user.getId(), en_id, en_type);
		if (!flag) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.LIKE_ERROR);
		}
		return "@" + JsonResponse.formSuccessResponse();
	}

	@Get("getEn")
	@Post("getEn")
	public String get(Invocation inv, @Param("uid") int uid, @Param("offset") int offset, @Param("count") int count

	) throws Throwable {
		if (0 > offset) {
			offset = 0;
		}

		if (0 >= count) {
			count = 20;
		}

		int hostId = hostHolder.getUser().getId();
		if (uid <= 0) {
			uid = hostId;
		}
		List<Long> le = eService.getLikeEnId(uid, 1, offset, count);
		return "@" + JsonResponse.formComplexResponse(le);
	}
}
