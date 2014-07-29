package com.renren.ntc.video.controllers.api.version;

import com.renren.ntc.video.biz.dao.DeviceTokenDAO;
import com.renren.ntc.video.utils.ClientVersionUtils;
import com.renren.ntc.video.utils.JsonResponse;
import com.renren.ntc.video.utils.SKUtil;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-8-8
 * Time: 下午12:32
 */
@Path("")
public class VersionCheckController {

    @Autowired
    DeviceTokenDAO deviceTokenDao;

    @Get("check")
    @Post("check")
    public String check(@Param("verNum") long verNum, @Param("token") String token) {
        //BasicDBObject version =  MongoDBUtil.getInstance().getVersionByVerNum(verNum);
        if (token != null) {
            int uid = SKUtil.parseInfiniteSessionKey(token).getUid();
            deviceTokenDao.updateClient(String.valueOf(verNum), uid);
        }
        if (ClientVersionUtils.isNewerThan(verNum)) {
            return "@" + JsonResponse.formComplexResponse(JSONObject.fromObject(ClientVersionUtils.getNewestVersion()));
        } else {
            return "@" + JsonResponse.formSuccessResponse();
        }
    }
}
