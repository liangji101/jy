package com.renren.ntc.video.controllers.comment;

import java.util.Date;

import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.BasicDBObject;
import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.CommentIndex;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.CommentService;
import com.renren.ntc.video.service.SequenceService;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.EmojiEncoder;
import com.renren.ntc.video.utils.EventUtil;
import com.renren.ntc.video.utils.JsonResponse;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-7-21
 * Time: 下午4:18
 */

@Path("")
public class CommentController {

    @Autowired
    private CommentService commservice;

    @Autowired
    private NtcHostHolder hostHolder;

    
    @Autowired
    private UserService userService;



    @LoginRequired
    @Post("/add")
    @Get("/add")
    public String addComment(
            @Param("comment_index") String commentIndex,
            @Param("content")  String content,
            @Param("desUid") int desUid) {
    	CommentIndex ci = null;
		try {
			  ci  = BaseUtil.paser(commentIndex);
		} catch (Exception e) {
			return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.SHAREMISS);
		}
	    User user =	hostHolder.getUser();
	    long comment_id = 1L;
        JSONObject obj = new JSONObject();
        obj.put("op", "done");
        obj.put("commentId", comment_id);
        return "@" + obj.toString();
    }

    @Get("/more")
    public String getCommentsByPage(
            @Param("comment_index") String commentIndex,
            @Param("offset") int offset,
            @Param("count") int count) {
        JSONArray list = commservice.getWebComments(commentIndex, offset, count);
        //不能走这个,因为涉及到html标签的解码
//        JSONArray list = commservice.getComments(commentIndex, offset, count);
        return "@" + JsonResponse.formComplexResponse(list);
    }
    
    //删除评论
    @Post("del")
    public String del(@Param("comment_index") String commentIndex, @Param("comment_id") long cid) {
        CommentIndex ci = null;
        try {
            ci = BaseUtil.paser(commentIndex);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(ci == null) {
            return "@fail";
        }
        try{
        	BasicDBObject obj = commservice.getActualComment(commentIndex, cid);
        	int uid = obj.getInt("uid");
        	User user = hostHolder.getUser();
        	//保证删除操作的是评论的所有者
        	if (uid != user.getId()){
        		return "@fail";
        	}
        	if(commservice.delComment(commentIndex, cid)) {
                return "@success";
            } else {
                return "@fail";
            }
        }catch(Exception e){
        	e.printStackTrace();
        	return "@fail";
        }
    }
}
