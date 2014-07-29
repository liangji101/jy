package com.renren.ntc.video.controllers.api.attention;


import java.util.List;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.AttentionService;
import com.renren.ntc.video.service.RelationShipService;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;

/**
 */
@AccessTokenChecker
@Path("")
public class AttentionController {
	
	@Autowired
	private RelationShipService relationShip;
	
	@Autowired
	private NtcHostHolder hostHolder;
	
	@Autowired
	private UserService userService;


    @Autowired
    private AttentionService attentionService;

    private static Logger logger = Logger.getLogger(AttentionController.class);

	@Get("add")
	@Post("add")
	public String add(@Param("target") int target){
		User user = hostHolder.getUser();
		int uid = user.getId();
		
		if(uid == target){
			return "@"+ JsonResponse.formFailResponse(Constants.ErrorCode.ILLAGEL_ATTRNTION);
		}
		
		User tt = userService.query(target);
		if(null == tt){
			return "@"+ JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID, "target");
		}

        return "@" + attentionService.attention(uid, target).getIosResponse();

	}

    @Get("hotUser")
    @Post("hotUser")
    public String attHotUser(@Param("uids") String uids) {
        if(!StringUtils.isEmpty(uids)) {
            for(String uid : uids.split(Constants.UID_SPILITTER)) {
                try {
                    int iUid = Integer.valueOf(uid);
                    attentionService.attention(hostHolder.getUser().getId(), iUid);
                } catch (NumberFormatException e) {
                    logger.warn(String.format("uid: %s is error!", uid), e);
                } catch (Exception ignored){
                    logger.warn("unknown exception", ignored);
                }
            }
        }
        return "@" + JsonResponse.formSuccessResponse();
    }

    @Post("/confirm")
    @Get("/confirm")
    public String confirm(@Param("src_uid") int srcUid, @Param("mid") long mid) {
        User user = hostHolder.getUser();
        if(srcUid <= 0 || mid <= 0) {
            return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.INPUT_CODE_INVALID);
        }

        int target = user.getId();
        logger.info("用户 " + srcUid + " 自动关注 用户 " + target);
        boolean ret;
        ret = relationShip.addAttention(srcUid, target);
        boolean result = ret;

        if(!result) {
            return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.FAIL);
        }
        //加入mid和src uid没有绑定，会带来一些问题，因为找不到mid，消息无法置为已读
        //这一块应该绑定以下
        relationShip.delPendingAttention(user.getId(), srcUid);
        
        return "@" + JsonResponse.formSuccessResponse();
    }

    @Post("/ignore")
    @Get("/ignore")
    public String ignore(@Param("src_uid") int srcUid, @Param("mid") long mid) {
    	 if(srcUid <= 0 || mid <= 0) {
              return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.INPUT_CODE_INVALID);
         }
    	User user = hostHolder.getUser() ;
    	int uid = user.getId();
    	relationShip.delPendingAttention(uid,srcUid);
        return "@" + JsonResponse.formSuccessResponse();
    }
	
	@Get("del")
	@Post("del")
	public String del(@Param("target") int target) {
        User user = hostHolder.getUser();
		if (null == user){
			return "@"+ JsonResponse.formFailResponse(Constants.ErrorCode.NEED_LONGIN);
		}
		int uid = user.getId();
		User tt = userService.query(target);
		if(null == tt){
			return "@"+ JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
		}

        if(!relationShip.delAttention(uid, target))
			return "@"+ JsonResponse.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		
		return "@" + JsonResponse.formSuccessResponse();
	}
	
	
	@Get("/get")
	@Post("/get")
	public String getAttentions(@Param("uid") int uid,
                                @Param("type") int type,
                                @Param("offset") int offset,
                                @Param("count") int count) {
		User user = hostHolder.getUser();
		int hostId = user.getId();
		if (uid <= 0 ){
		   uid = hostId;
		}
		if(offset < 0){
			offset = 0;
		}
		if(count <= 0){
			count = 20;
		}
		
		List <Integer>  ls;
		if(type == 1){
		  ls = relationShip.getPassAttentions(uid,offset,count);
		}else {
		  ls = relationShip.getActiveAttentions(uid,offset,count);
		}
		
		List <User> ll   = userService.queryByUserIds(ls);
		JSONArray tmp = userService.paserUser(ll,hostId);
		return "@" + JsonResponse.formComplexResponse(tmp);
	}

	
	@Get("/getMarkUser")
	@Post("/getMarkUser")
	public String getMarkUser(@Param("uid") int uid,
                              @Param("type") int type,
                              @Param("offset") int offset,
                              @Param("count") int count) {
		User user = hostHolder.getUser();
		int hostId = user.getId();
		if (uid <= 0 ){
		   uid = hostId;
		}
		if(offset < 0){
			offset = 0;
		}
		if(count <= 0){
			count = 20;
		}

		List<User> users = userService.queryByUserIds(
                type == 1 ? relationShip.getPassAttentions(uid, offset, count) : relationShip.getActiveAttentions(uid,offset,count)
        );
		//把自己加进入。
		if(type ==3){
			users = userService.queryByUserIds(relationShip.getHuFengUids(uid,offset,count));
		}
//		if(offset == 0){
//		   users.add(user);
//		}
		JSONArray tmp = userService.paserUser(users,hostId);
		return "@" + JsonResponse.formComplexResponse(tmp);
	}
}
