package com.renren.ntc.video.biz.logic.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.renren.ntc.video.biz.dao.UserDAO;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.formbean.UserInfo;
import com.renren.ntc.video.service.EntityUserService;
import com.renren.ntc.video.service.RelationShipService;
import com.renren.ntc.video.utils.EmojiEncoder;
import com.renren.ntc.video.utils.MD5;
import com.renren.ntc.video.utils.MemcachedUtil;

/**
 * @author 张浩 E-mail:hao.zhang@renren-inc.com
 * @date 2012-5-31 下午03:16:22
 *
 * 类说明
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;


	@Autowired
	private EntityUserService entityService;


	@Autowired
	private RelationShipService relationShip;
	
	
	

	public int updateInfo(String name,String phoneNumber,int privacyAttr,int uid){
		//更新索引
		logger.info("update index create");
		name = EmojiEncoder.getInstance().convert(name);
		int ret = userDAO.updateInfo(name, phoneNumber, privacyAttr, uid);
        if(ret > 0) {
            synUserToCache(uid);
        }
        return ret;
	}

	@Override
	public void delete(int id) {
		userDAO.delete(id);
	}


    private Object sysUser = new Object();

	@Override
	public User query(int id) {
        synchronized (sysUser) {
            User user = MemcachedUtil.getInstance().getUser(id);
            if(user == null) {
                user = userDAO.query(id);
                if(user == null) {
                    return null;
                }
                MemcachedUtil.getInstance().cacheUser(user);
                return user;
            }
            return user;
        }
	}

    @Override
    public void updatePushType(int uid, int pushType) {
        userDAO.updatePushType(uid, pushType);
        synUserToCache(uid);
    }

    private void synUserToCache(int uid) {
        MemcachedUtil.getInstance().cacheUser(userDAO.query(uid));
    }

	@Override
	public Map<Integer, User> query(Collection<Integer> ids) {
		return userDAO.query(ids);
	}

	@Override
	public User queryByEmail(String email) {
		return userDAO.queryByEmail(email);
	}

	@Override
	public List<User> queryByUserIds(List<Integer> userIdList) {
        List<User> users = new ArrayList<User>(userIdList.size());
        for(Integer id : userIdList) {
            users.add(query(id));
        }
		return users;
	}


	@Override
	public int update(User user) {
		int ret = userDAO.update(user);
        if(ret > 0) {
            synUserToCache(user.getId());
        }
    	logger.info("update index create");
    	return  ret;
	}


	@Override
	public User CheckUserPassport(String userName, String userPassport) {

		userPassport =	StringUtils.trimToNull(userPassport);
		userName     =  StringUtils.trimToNull(userName);
		if(null != userName && null != userPassport){
			String md5Pass = MD5.digest(userPassport + UserService.PASSPORT_MD5_KEY);
			User user = userDAO.selectPassport(userName, md5Pass);
			return user;
		}
		return null;
	}

	public User checkUser(String username,String passwd){
		username = StringUtils.trimToNull(username);
		passwd = StringUtils.trimToNull(passwd);
		if(null != username && null != passwd){
			String md5Pass = MD5.digest(passwd + UserService.PASSPORT_MD5_KEY);
			return userDAO.checkUser(username, md5Pass);
		}
		return null;
	}

	@Override
	public User getUser(String userName) {
		return userDAO.query(userName);
	}


	@Override
	public User createUser(String userName, String userPassport) throws Exception{

		userPassport =	userPassport.trim();
		userName     =  userName.trim();
		User user  = userDAO.query(userName);
		if (null != user) {
			throw new Exception("用户已经存在");
		}
		user = new User();
		user.setEmail(userName);
		String md5Pass = MD5
				.digest(userPassport + UserService.PASSPORT_MD5_KEY);
		user.setPassword(md5Pass);
		Integer id = userDAO.saveUserNamePass(user);
		user.setId(id);
		if (id == null || id < 0) {
			throw new Exception("创建用户失败,请稍候再试");
		}
        MemcachedUtil.getInstance().cacheUser(user);
		return user;
	}


	@Override
	public int  updateHeadurl(int uid , String headurl) {
        int ret = userDAO.updateHeadurl(uid , headurl);
        if(ret > 0) {
            synUserToCache(uid);
        }
        return ret;
	}

    @Override
    public int updateAttPrivacy(int uid, int attPrivacy){
        int ret = userDAO.updateAttPrivacy(uid, attPrivacy);
        if(ret > 0) {
            synUserToCache(uid);
        }
        return ret;
    }

	public   JSONObject paserUser(User user,int hostId){
		return paserUser( user, hostId, false);
	}


	public JSONArray paserUser(List<User> users, int hostId, boolean detail) {
		JSONArray arr = new JSONArray();
		for (User u : users) {
			JSONObject o = paserUser(u, hostId, detail);
				arr.add(o);
		}
		return arr;
	}

	public JSONObject paserUser(User user, int hostId, boolean detail) {
		JSONObject jo = new JSONObject();
		int uid = user.getId();
		jo.put("uid", uid);
		String name = HtmlUtils.htmlUnescape(user.getName());
		jo.put("name", name);
		jo.put("headurl", user.getHeadurl());
		jo.put("phoneNumber", user.getPhoneNumber());
		int  can = relationShip.isAttention(hostId, uid)?1:0;
		if (can == 0) {
				boolean pending = relationShip.isPendingAttention(uid, hostId);
				if (pending) {
					can = 2;
				}
		}
		jo.put("isFollowed", can);
		
		return jo;
	}



	@Override
	public JSONArray paserUser(List<User> lu,int hostId) {
		return paserUser(lu, hostId,false);
	}


	@Override
	public List<User> queryByUserIds(Collection<Integer> userIdList) {
		return userDAO.queryAllList(userIdList);
	}
//	@Override
//	public List<User> queryLikeName(String name){
//		return userDAO.queryLikeName("%"+name+"%");
//	}


	@Override
	public int updatePass(int uid, String newpasswd) {
		newpasswd = MD5.digest(newpasswd + UserService.PASSPORT_MD5_KEY);
		int ret = userDAO.updatePass(uid, newpasswd);
        if(ret > 0 ) {
            synUserToCache(uid);
        }
        return ret;
	}

	@Override
	public List<User> getAllUser() {
		return userDAO.getAllUser();
	}

	@Override
	public List<UserInfo> parseUserListToUInfo(List<User> users, User attDesUser) {
		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		for (User fans : users) {
            UserInfo userInfo = parseUserToUserInfo(fans, attDesUser);
            userInfos.add(userInfo);
		}
		return userInfos;
	}

    @Override
    public UserInfo parseUserToUserInfo(User targetUser, User attUser) {
        int can = 0;
        if(attUser != null) {
            can = relationShip.isAttention(attUser.getId(), targetUser.getId())?1:0;
            if(targetUser.getAttPrivacy()==2){
            if(can == 0){
                 boolean  pending = relationShip.isPendingAttention(attUser.getId(), targetUser.getId());
                 if(pending){
                     can = 2;
                 }
            }
            }
        }
        int videoCount = 0;
        UserInfo userInfo = new UserInfo();
        userInfo.setAttentionedByCount(relationShip.getPassAttentions(targetUser.getId()).size());
        userInfo.setAttentioningCount(relationShip.getActiveAttentionsCount(targetUser.getId()));
        userInfo.setUser(targetUser);
        userInfo.setVideoCount(videoCount);
        userInfo.setIsAttention(can);
        return userInfo;
    }

	@Override
	public void updateSyc(int syc,int uid) {
		userDAO.updateSyc(syc,uid);
        synUserToCache(uid);
	}

	@Override
	public boolean resetDefaultAvatar(int uid) {
        int ret = userDAO.updateHeadurl(uid, "http://head.xiaonei.com/photos/0/0/page-tiny.png");
		if(ret > 1) {
            synUserToCache(uid);
            return true;
        } else {
            return false;
        }
	}
	
//	@Override
//	public List<User> getNonHotUsers(int offset,int count){
//		return userDAO.getNonHotUsers(offset, count);
//	}
	
//	@Override
//	public int countNonHotUsers(){
//		return userDAO.countNonHotUsers();
//	}

//	@Override
//	public List<User> getUsers(long offset,long count){
//		return userDAO.getUsers(offset, count);
//	}
	
//	public List<User> getOldPushTypeUsers(long offset,long count){
//		return userDAO.getOldPushTypeUsers(offset, count);
//	}
	
//	@Override
//	public long countAllUsers(){
//		return userDAO.countAllUsers();
//	}

	@Override
	public int getPushType(int uid) {
		return userDAO.getPushType(uid);
	}
	
//	@Override
//	public int countOldPushType(){
//		return userDAO.countOldPushType();
//	}
}
