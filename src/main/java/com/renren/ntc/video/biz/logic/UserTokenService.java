package com.renren.ntc.video.biz.logic;

import com.renren.ntc.video.biz.NtcConstants;
import com.renren.ntc.video.biz.dao.UserTokenDAO;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.utils.MemcachedUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserTokenService implements NtcConstants {

    @Autowired
    UserTokenDAO userTokenDAO;

    public boolean isValid(UserToken requestToken) {
        if (requestToken == null || requestToken.getUserId() <= 0) {
            return false;
        }
        int userId = requestToken.getUserId();

        if (userId > 0) {
            UserToken userToken = getToken(userId);
            if (userToken != null) {
                if (StringUtils.equals(requestToken.getToken(), userToken.getToken())) { // 票相同
                    // remove this
                    if (userToken.getExpiredTime() != null && !userToken.getExpiredTime().before(new Date())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public UserToken getToken(int userId) {
        UserToken userToken = MemcachedUtil.getInstance().getUserToken(userId);
        if(userToken == null) {
            return userTokenDAO.query(userId);
        } else {
            return userToken;
        }
    }

    public void clearToken(int userId) {
        userTokenDAO.delete(userId);
    }

    public void setToken(UserToken userToken) {
        int ret =  userTokenDAO.update(userToken);
        if(ret > 0) {
        }
        MemcachedUtil.getInstance().cacheUserToken(userTokenDAO.query(userToken.getUserId()));
    }
}
