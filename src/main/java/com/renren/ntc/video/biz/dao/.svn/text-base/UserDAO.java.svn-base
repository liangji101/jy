package com.renren.ntc.video.biz.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.utils.Constants;


@DAO(catalog = Constants.DB_TOKEN)
public interface UserDAO {
    String TABLE                  = "user";
    // -------- { Column Defines
    String ID                     = "id";
    String NAME                   = "name";
    String PASSWORD               = "password";
    String EMAIL                  = "email"; 
    
    // -------- } Column Defines

    String FIELD_PK               = "id";
    String FIELDS_WITHOUT_PK      = "name,password,email,headurl , att_privacy,phoneNumber, push_type , sycType";
    String INSERT     =             "name,password,email,headurl ,phoneNumber";
    String FIELDS_ALL             = FIELD_PK + "," + FIELDS_WITHOUT_PK;


    @SQL("update "+TABLE+" set name=:1,phoneNumber=:2,att_privacy=:3 where id=:4")
    public int updateInfo(String name, String phoneNumber, int privacyAttr, int uid);
    
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id in (:1)")
    public Map<Integer, User> queryAllMap(Collection<Integer> ids);

    @SQL("select COUNT(" + FIELD_PK + ") from " + TABLE)
    public int countAll();

    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where email=:1 limit 1")
    public User queryByEmail(String email);
    
    

    /**
     * 通过集合用户id，批量的查询出用户信息
     * 
     * @param userIdList
     * @return
     */
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id in (:1)")
    public List<User> queryByUserIds(List<Integer> userIdList);

    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id = :1")
    public User query(int id);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id in (:1)")
    public Map<Integer, User> query(Collection<Integer> ids);

    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where email = :1")
    public User query(String email);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where name = :1")
    public List<User> queryByName(String name);
    
//    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where name like :1 limit 100")
//    public List<User> queryLikeName(String name);

    String SQL_UPDATE_MODEL_FILEDS_WITHOUT_PK = "name=:model.name,headurl=:model.headurl," +
    		"phoneNumber=:model.phoneNumber,push_type=:model.pushType,sycType=:model.sycType ";

	@SQL("update " + TABLE + " set " + SQL_UPDATE_MODEL_FILEDS_WITHOUT_PK
			+ " where id=:model.id")
	public int update(@SQLParam("model")
                          User model);

   
    /**
     * 插入员工信息
     * 
     * @param model
     * @return
     */
    @SQL("insert  into "
            + TABLE
            + " ("
            + INSERT
            + ") VALUES (:model.name,:model.password," +
            		":model.email,:model.headurl,:model.phoneNumber)")
    @ReturnGeneratedKeys
    public Integer save(@SQLParam("model") User model);
    
    
    @SQL("insert  into "
            + TABLE
            + " ("
            + "email,password"
            + ") VALUES (:model.email,:model.password)")
    @ReturnGeneratedKeys
    public Integer saveUserNamePass(@SQLParam("model") User model);

    /**
     * 删除员工信息
     * 
     * @param id
     */
    @SQL("delete from " + TABLE + " where `id` = :1")
    public void delete(int id);

    /**
     * 查询密码是否正确
     * @param email
     * @param md5Pass
     * @return
     */
    @SQL("select "+ FIELDS_ALL +" from " + TABLE + " where `email` = :1 and `password` = :2")
    public User selectPassport(String email, String md5Pass);
    
    @SQL("select "+FIELDS_ALL +" from " + TABLE + " where `name` = :1 and `password` = :2")
    public User checkUser(String username, String passwd);

    @SQL("update "+ TABLE + " set headurl = :2  where id = :1")
	public Integer updateHeadurl(int uid, String headurl);

    /**
     * 修改用户关注权限
     * @param uid 用户id
     * @param attPrivacy 关注权限 0、自动批准关注 1、自动批准人人网好友 2、亲自批准关注者
     * @return 是否修改成功
     */
    @SQL("update " + TABLE + " set att_privacy = :2 where id = :1")
    public int updateAttPrivacy(int uid, int attPrivacy);
    
 
    @SQL("select "+ FIELDS_ALL +" from " + TABLE+ " where name like :1 limit :2,:3")
	public List<User> queryLikeName(String key, int offset, int count);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id in (:1)")
    public List<User> queryAllList(Collection<Integer> ids);

    @SQL("update " + TABLE + " set password = :2 where id = :1")
	public int updatePass(int uid, String newpasswd);

    @SQL("update " + TABLE + " set push_type = :2 where id = :1")
    public int updatePushType(int uid, int pushType);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE )
	public List<User> getAllUser();

    
    /**
     * push type 2^9  自动同步到人人
     *           2^8  自动同步到sina
     * 
     * 
     */
//    @SQL("update " + TABLE + " set push_type = :2|push_type where id = :1")
//	public int updateSycSet(int uid, int prc);

    @SQL("update " + TABLE + " set sycType = :1 where id =:2")
	public Object updateSyc(int syc, int uid);
    
    //仅限后台编辑时调用
//    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id not in (select id from rec_user) order by create_time desc limit :1,:2")
//    public List<User> getNonHotUsers(int offset, int count);
    
//    @SQL("select count(id) from " + TABLE + " where id not in (select id from rec_user)")
//    public int countNonHotUsers();
    
//    @SQL("select count(id) from " + TABLE + " where email like :1 and create_time between :2 and :3")
//    public int countNewThirdUser(String src,String createTime,String endTime);
    
//    @SQL("select count(id) from " + TABLE + " where email not like 'rr_%' and email not like 'sina_%' and create_time between :1 and :2")
//    public int countNewUser(String beginTime,String endTime);
 
    //更新用户的pushType时调用
//    @SQL("select " + FIELDS_ALL + " from " + TABLE + " limit :1,:2")
//    public List<User> getUsers(long offset,long count);
    
//    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where push_type<16 limit :1,:2")
//    public List<User> getOldPushTypeUsers(long offset,long count);
    
//    @SQL("select count(id) from " + TABLE)
//    public long countAllUsers();

    @SQL("select push_type from " + TABLE + " where id = :1")
	public int getPushType(int uid);
    
    @SQL("select create_time from " + TABLE + " where id = :1")
	public Date getCreateTime(int uid);
    
//    @SQL("select count(id) from user where push_type<16")
//    public int countOldPushType();
}