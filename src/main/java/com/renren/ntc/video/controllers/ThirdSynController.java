package com.renren.ntc.video.controllers;

import com.renren.ntc.video.annotations.LoginRequired;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.interceptors.access.NtcHostHolder;
import com.renren.ntc.video.service.ThirdUserService;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Date: 12-9-7
 * Time: 下午4:21
 */
@LoginRequired
@Path("third")
public class ThirdSynController {


    @Autowired
    private UserService userService;

    @Autowired
    private ThirdUserService thirdUserService;

    @Autowired
    private NtcHostHolder hostHolder;


    @Post("all")
    public String updateRenrenSynState(@Param("action") String action) {
        ThirdUser thirdUser = thirdUserService.getThirdUserByUid(hostHolder.getUser().getId());
        if(action != null && action.equals("open")) {
            int sycType = 0;
            if(thirdUser.getRr_uid() > 0) {
                sycType = sycType | 1;
            }

            if(thirdUser.getSina_uid() > 0 ) {
                sycType = sycType | 2;
            }
            userService.updateSyc(sycType, hostHolder.getUser().getId());
            return "@" + sycType;
        } else {
            userService.updateSyc(0, hostHolder.getUser().getId());
            return "@0";
        }
    }

    /**
     * 新浪观看
     * @return
     */
}
