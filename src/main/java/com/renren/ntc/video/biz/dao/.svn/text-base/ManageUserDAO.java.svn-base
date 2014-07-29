package com.renren.ntc.video.biz.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.utils.Constants;

@DAO(catalog = Constants.DB_TOKEN)
public interface ManageUserDAO {
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
	@SQL("select COUNT(" + FIELD_PK + ") from " + TABLE)
    public int countAll();
	
	@SQL("select count(id) from " + TABLE + " where email like :1 and create_time between :2 and :3")
    public int countNewThirdUser(String src,String createTime,String endTime);
    
    @SQL("select count(id) from " + TABLE + " where email not like 'rr_%' and email not like 'sina_%' and create_time between :1 and :2")
    public int countNewUser(String beginTime,String endTime);
    
    @SQL("select count(id) from " + TABLE + " where id not in (select id from rec_user)")
    public int countNonHotUsers();
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where id not in (select id from rec_user) order by create_time desc limit :1,:2")
    public List<User> getNonHotUsers(int offset, int count);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where name like :1 limit 100")
    public List<User> queryLikeName(String name);
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where push_type<16 limit :1,:2")
    public List<User> getOldPushTypeUsers(long offset,long count);
    
    @SQL("select count(id) from user where push_type<16")
    public int countOldPushType();
    
    @SQL("select count(id) from user where sycType<7280")
    public int countNeedUpdateSycType();
    
    @SQL("select " + FIELDS_ALL + " from " + TABLE + " where sycType<7280 limit :1,:2")
    public List<User> getNeedUpdateSycTypeUsers(long offset,long count);
    
    @SQL("select count(id) from user where create_time<:1")
	public int countAllBefore(String date);
    
    @SQL("update user set password=:2 where id=:1")
    public int updatePasswd(int uid,String password);
    
    @SQL("update user set email=:2 where id=:1")
    public int updateEmail(int uid,String email);
}
