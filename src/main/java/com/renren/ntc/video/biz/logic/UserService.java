package com.renren.ntc.video.biz.logic;
/**
 *  @author hao.zhang
 *  userService 接口类
 */
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.formbean.UserInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public interface UserService {
	
	public final static String    PASSPORT_MD5_KEY = "703hdskf979234k^*jds";
    
    public int updateInfo(String name, String phoneNumber, int privacyAttr, int uid);


    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    public User queryByEmail(String email);

    /**
     * 按集合查询用户列表
     * @param userIdList
     * @return List<User>
     */
    public List<User> queryByUserIds(Collection<Integer> userIdList) ;

   /**
    * 查询单个用户
    * @param id
    * @return
    */
    public User query(int id);

    /**
     * 按集合查询用户列表
     * @param ids
     * @return Map<Integer, User>,以用户id为key
     */
    public Map<Integer, User> query(Collection<Integer> ids) ;
    
    /**
     * 更新用户
     * @param model
     * @return
     */
    public int update(User model);
    
    /**
     * 删除用户
     * @param id
     */
    public void delete(int id);

    /**
     * 检测用户的用户名密码
     * @param userName
     * @param userPassport
     * @return
     */
    public User CheckUserPassport(String userName, String userPassport);
    
    /**
     * 创建用户，返回null代表创建失败，通常是已有用户
     * @param userName
     * @param userPassport
     * @return
     */
    public User createUser(String userName, String userPassport) throws Exception;

    
    public User getUser(String userName);
    
    
    public List<User> getAllUser();

	public int updateHeadurl(int uid, String headurl);
    /**
     * 修改用户关注权限
     * @param uid 用户id
     * @param attPrivacy 关注权限 0、自动批准关注 1、自动批准人人网好友 2、亲自批准关注者
     * @return 是否修改成功
     */
    int updateAttPrivacy(int uid, int attPrivacy);
   

	List<User> queryByUserIds(List<Integer> userIdList);


	public JSONArray paserUser(List<User> users, int hostId, boolean b);



	public JSONArray paserUser(List<User> lu, int hostId);
	
	
	
	public JSONObject paserUser(User u, int hostId);



	public JSONObject paserUser(User u, int hostId, boolean b);



	public int  updatePass(int uid, String newpasswd);


	List<UserInfo> parseUserListToUInfo(List<User> ll, User user);

    UserInfo parseUserToUserInfo(User isAttUser, User fans);


	public void updateSyc(int syc, int uid);

    void updatePushType(int uid, int pushType);
    
    public boolean resetDefaultAvatar(int uid);//设置用户的头像为默认头像
    
	public int getPushType(int uid);
}
