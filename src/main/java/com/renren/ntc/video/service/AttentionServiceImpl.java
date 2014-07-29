package com.renren.ntc.video.service;

import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.constants.AttentionPrivacy;
import com.renren.ntc.video.constants.AttentionStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.renren.ntc.video.constants.AttentionStatus.*;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-8-11
 * Time: 下午2:13
 */
@Service
public class AttentionServiceImpl implements AttentionService {

    private static final Logger logger = Logger.getLogger(AttentionServiceImpl.class);

    @Autowired
    private RelationShipService relationShip;

    @Autowired
    private UserService userService;

    @Autowired
    private ThirdUserService thirdUserService;

    /**
     * 自动关注用户
     * @param uid
     * @param target
     * @return
     */
     private boolean autoAttention(int uid, int target) {
        logger.info("用户 " + uid + " 自动关注 用户 " + target);
        boolean ret = relationShip.addAttention(uid, target);
        return ret;
    }

    @Override
    public AttentionStatus hotUserAttention(int uid, int target) {
        if(relationShip.isAttention(uid, target)) {
            logger.info("用户 " + uid + " 已经关注 用户 " + target);
            return FOCUSED;
        }
        if(autoAttention(uid, target)) {
            return FOCUSED;
        }
        return NONE;
    }

    /**
     * 自己确认自己的关注者
     * <p>人工验证流程</p>
     * <ul>
     *     <li>1、发送消息给target</li>
     *     <li>2、调用confirmLike接口</li>
     * </ul>
     * @param uid
     * @param target
     * @return
     */
    private boolean manualAttention(int uid, int target) {
        logger.info("用户 " + uid + " 需要用户 " + target + " 审核关注");
        relationShip.addPendingAttention(uid, target);
        return true;
    }

    /**
     * 关注用户逻辑
     * @param uid
     * @param target
     * @return
     */
    @Override
    public AttentionStatus attention(int uid, int target) {
        User user = userService.query(target);
        if(user == null) {
            return NONE;
        }
        if(relationShip.isAttention(uid, target)) {
            logger.info("用户 " + uid + " 已经关注 用户 " + target);
            return FOCUSED;
        }
        if(user.getAttPrivacy() < AttentionPrivacy.values().length) {
            switch (AttentionPrivacy.values()[user.getAttPrivacy()]) {
                case AUTO:
                    if(autoAttention(uid, target)) {
                        return FOCUSED;
                    }
                    break;
                case MANUAL:
                    if(manualAttention(uid, target)){
                        return WAITING;
                    }
                    break;
                case RENREN:
                    if(thirdUserService.isFriend(uid, target)) {
                        if(autoAttention(uid, target)){
                            return FOCUSED;
                        }
                    } else {
                        if(manualAttention(uid, target)){
                            return WAITING;
                        }
                    }
                    break;
                default:
                    return NONE;
            }
        }
        return NONE;
    }
}
