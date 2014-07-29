package com.renren.ntc.video.controllers.api.comment;

import com.renren.ntc.video.annotations.AccessTokenChecker;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.CommentIndex;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.CommentService;
import com.renren.ntc.video.utils.*;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@AccessTokenChecker
@Path("")
public class CommentController {

    @Autowired
    private CommentService commservice;
    
    @Autowired
    private UserService userService;

    @Autowired
    private NtcHostHolder hostHolder;
    


    private static Log logger = LogFactory.getLog(CommentController.class);

    @Get("/add")
    @Post("/add")
    public String addComment(
            @Param("comment_index") String comment_index,
			@Param("content")  String content,
            @Param("reply_to_uid") int desUid , @Param("app_verStr") long v) {
		CommentIndex ci = null;
		if(logger.isInfoEnabled()){
			logger.info(String.format("comment_index %s , content %s ,desUid %d ", comment_index,content,desUid));
		}
		try {
			  ci  = BaseUtil.paser(comment_index);
		} catch (Exception e) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.SHAREMISS);
		}
		
	    User user =	hostHolder.getUser();
      
        return "@" + JsonResponse.formSuccessResponse();
    }

    @Get("/del")
    @Post("/del")
    //未开发完成
    public String delComment(@Param("comment_index") String comment_index,
                             @Param("comment_id") int comment_id) {

        CommentIndex ci = null;
        try {
            ci = BaseUtil.paser(comment_index);
        } catch (Exception e) {
            return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.PARMATERS_INVALID);
        }
        boolean bool = commservice.delComment(comment_index, comment_id);
        if (!bool) {
            return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.OP_ERROR);

        }

    

        return "@" + JsonResponse.formSuccessResponse();
    }

    @Get("/update")
    @Post("/update")
    public String updateComment(@Param("comment_index") String comment_index,
                                @Param("comment_id") int comment_id,
                                @Param("content")  String content) {

        User user = hostHolder.getUser();
        int uid = user.getId();
        boolean bool = commservice.updateComment(comment_index, comment_id, uid, content);
        if (bool) {
            return "@" + JsonResponse.formSuccessResponse();
        }
        return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.OP_ERROR);
    }

    @Get("/gets")
    @Post("/gets")
    public String getCommentsByPage(@Param("comment_index") String comment_index,
                                    @Param("offset") int offset,
                                    @Param("count") int count) {
        JSONArray list = commservice.getComments(comment_index, offset, count);
        return "@" + JsonResponse.formComplexResponse(list);
    }

    @Get("/get")
    @Post("/get")
    public String getComment(@Param("comment_index") String comment_index,
                             @Param("comment_id") int comment_id) {
        JSONObject obj = commservice.getComment(comment_index, comment_id);
        return "@" + JsonResponse.formComplexResponse(obj);
    }


}
