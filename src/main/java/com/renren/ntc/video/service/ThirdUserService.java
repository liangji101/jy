package com.renren.ntc.video.service;

import com.renren.ntc.video.biz.dao.ThirdUserDAO;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.utils.BaseUtil;
import com.renren.ntc.video.utils.renren.RenrenApiClient;
import com.renren.ntc.video.utils.renren.param.impl.AccessToken;
import com.renren.ntc.video.utils.renren.services.FriendsService;
import com.renren.ntc.video.utils.renren.services.RenrenApiException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ThirdUserService {

    private static final Logger logger = Logger.getLogger(ThirdUserService.class);

    @Autowired
    private ThirdUserDAO thirdUserDAO;

    @Autowired
    private UserService userService;

    public ThirdUser getThirdUserByUid(int uid) {
        return thirdUserDAO.getThirdUserByUid(uid);
    }

    /*
     public void createInfoBySina(int sc_uid,int sina_uid,String sina_token,String sina_refresh_token){
         thirdUserDAO.createInfoBySina(sc_uid, sina_uid, sina_token, sina_refresh_token);
     }

     public void createInfoByQQ(int sc_uid,int qq_uid,String qq_token,String qq_refresh_token){
         thirdUserDAO.createInfoByQQ(sc_uid, qq_uid, qq_token, qq_refresh_token);
     }

     public void addInfoWithRenRen(int sc_uid,int rr_uid,String rr_token,String rr_refresh_token){
         thirdUserDAO.addInfoWithRenRen(sc_uid, rr_uid, rr_token, rr_refresh_token);
     }

     public void addInfoWithSina(int sc_uid,int sina_uid,String sina_token,String sina_refresh_token){
         thirdUserDAO.addInfoWithSina(sc_uid, sina_uid, sina_token, sina_refresh_token);
     }

     public void addInfoWithQQ(int sc_uid,int qq_uid,String qq_token,String qq_refresh_token){
         thirdUserDAO.addInfoWithQQ(sc_uid, qq_uid, qq_token, qq_refresh_token);
     }

     */

    public void clearRRInfoByUid(int uid) {
        thirdUserDAO.clearRRInfoByUid(uid);
    }

    public void clearSinaInfoByUid(int uid) {
        thirdUserDAO.clearSinaInfoByUid(uid);
    }

    public void clearQQInfoByUid(int uid) {
        thirdUserDAO.clearQQInfoByUid(uid);
    }

    public boolean createOrUpdateRenRen(int sc_uid, long rr_uid, String rr_token, String rr_refresh_token) {
        int a = thirdUserDAO.createOrUpdateRenRen(sc_uid, rr_uid, rr_token, rr_refresh_token);
        if (1 > a) {
            return false;
        }
        return true;
    }

    public boolean createOrUpdateSina(int sc_uid, long sina_uid, String sina_token,
                                      String sina_refresh_token) {
        int a = thirdUserDAO.createOrUpdateSina(sc_uid, sina_uid, sina_token, sina_refresh_token);
        if (1 > a) {
            return false;
        }
        return true;
    }

    public boolean createOrUpdateQQ(int sc_uid, long qq_uid, String qq_token,
                                    String qq_refresh_token) {
        int a = thirdUserDAO.createOrUpdateQQ(sc_uid, qq_uid, qq_token, qq_refresh_token);
        if (1 > a) {
            return false;
        }
        return true;
    }

    public ThirdUser getByRrUid(long uid) {
        return thirdUserDAO.getByRrUid(uid);
    }

    public List<ThirdUser> getByRrUid(List<Long> uids, int offset, int count) {
        List<ThirdUser> ss = thirdUserDAO.getByRrUid(uids, offset, count);
        if (null == ss) {
            ss = new ArrayList<ThirdUser>();
        }
        return ss;
    }

    public ThirdUser getBySinaUid(long uid) {
        return thirdUserDAO.getBySinaUid(uid);

    }

    public List<ThirdUser> getBySinaUid(List<Long> uids, int offset, int count) {
        List<ThirdUser> ss = thirdUserDAO.getBySinaUid(uids, offset, count);
        if (null == ss) {
            ss = new ArrayList<ThirdUser>();
        }
        return ss;
    }

    public JSONArray getScUid(List<ThirdUser> ss, int hostId) {
        JSONArray ar = new JSONArray();
        List<Integer> arr = new ArrayList<Integer>();
        for (ThirdUser s : ss) {
            int sc_uid = s.getSc_uid();
            arr.add(sc_uid);
        }
        List<User> lu = userService.queryByUserIds(arr);
        ar = userService.paserUser(lu, hostId);
        return ar;
    }

    public boolean updateRenRen(int uid, String third_token,
                                String third_refresh_token) {
        int re = thirdUserDAO.updateRenRen(uid, third_token, third_refresh_token);
        if (re > 0) {
            return true;
        }
        return false;
    }

    public boolean updateSina(int uid, String third_token,
                              String third_refresh_token) {
        int re = thirdUserDAO.updateSina(uid, third_token, third_refresh_token);
        if (re > 0) {
            return true;
        }
        return false;
    }

    public boolean unbundRenRen(int uid) {
        int re = thirdUserDAO.unBundRenRen(uid);
        if (re > 0) {
            return true;
        }
        return false;
    }

    public boolean unbundSina(int uid) {
        int re = thirdUserDAO.unBundSina(uid);
        if (re > 0) {
            return true;
        }
        return false;
    }

    public int getRRState(int uid) {
        long flag = thirdUserDAO.getRRState(uid);
        if (flag == 0l) {
            return 0;
        } else {
            return 2;
        }
    }

    public int getSinaState(int uid) {
        long flag = thirdUserDAO.getSinaState(uid);
        if (flag == 0l) {
            return 0;//未绑定账号，返回0
        } else {
            return 2;
        }
    }

    public boolean isFriend(int uid1, int uid2) {
        ThirdUser thirdUser1 = thirdUserDAO.getThirdUserByUid(uid1);
        ThirdUser thirdUser2 = thirdUserDAO.getThirdUserByUid(uid2);

        if(thirdUser1 == null || thirdUser2 == null || thirdUser1.getRr_uid() == 0 || thirdUser2.getRr_uid() == 0) {
            return false;
        }

        JSONArray jsonArray = RenrenApiClient.getInstance().getFriendsService().areFriends(String.valueOf(thirdUser1.getRr_uid()), String.valueOf(thirdUser2.getRr_uid()), new AccessToken(thirdUser1.getRr_token()));
        if (jsonArray == null || jsonArray.size() <= 0) {
            return false;
        } else {
            Object isFriend = jsonArray.getJSONObject(0).get("are_friends");
            return isFriend != null && isFriend.toString().equals("1");
        }
    }

    public List<ThirdUser> getAllRRUsers(){
    	return thirdUserDAO.getAllRRUsers();
    }
    
    public List<ThirdUser> getRRUsers(long offset,long count){
    	return thirdUserDAO.getRRUsers(offset,count);
    }
    
    public long countRRUsers(){
    	return thirdUserDAO.countRRUsers();
    }

	public long getRRUid(int uid) {
		try {
			return thirdUserDAO.getRRState(uid);
		} catch ( EmptyResultDataAccessException e ){
			logger.info("can not find rr uid from sc uid:" + uid);
			return 0;
		}
	}
}
