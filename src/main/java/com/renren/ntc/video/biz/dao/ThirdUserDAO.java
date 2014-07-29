package com.renren.ntc.video.biz.dao;

import com.renren.ntc.video.entity.ThirdUser;
import com.renren.ntc.video.utils.Constants;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import java.util.List;

@DAO(catalog = Constants.DB_TOKEN)
public interface ThirdUserDAO {
	 String TABLE = " third_user ";
     String ALL_FIELDS = "id,sc_uid,rr_uid,sina_uid,qq_uid,rr_token,sina_token,qq_token,rr_refresh_token,sina_refresh_token,qq_refresh_token,modify_time,create_time";
	 String RR_FIELDS =  "sc_uid,rr_uid,rr_token,rr_refresh_token,modify_time,create_time";
	 String SINA_FIELDS = "sc_uid,sina_uid,sina_token,sina_refresh_token,modify_time,create_time";
	 String QQ_FIELDS = "sc_uid,qq_uid,qq_token,qq_refresh_token,modify_time,create_time";
	
	@SQL("select " + ALL_FIELDS + " from "  +  TABLE + " where sc_uid = :1")
	public ThirdUser getThirdUserByUid(int uid);		
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where rr_uid =:rr_uid")			
	public ThirdUser retriveRR(@SQLParam("rr_uid") long rr_uid);
	
	@SQL("select count(id) from " + TABLE + " where qq_uid =:qq_uid")			
	public Integer retriveQQ(@SQLParam("qq_uid") long qq_uid);
	
	@SQL("select count(id) from " + TABLE + " where sina_uid =:sina_uid")			
	public Integer retriveSINA(@SQLParam("sina_uid") long sina_uid);
	
	@SQL("update "+TABLE+" set rr_uid=0,rr_token='',rr_refresh_token='',modify_time=now() where sc_uid=:1")
	public void clearRRInfoByUid(int uid);
	
	@SQL("update "+TABLE+" set sina_uid=0,sina_token='',sina_refresh_token='',modify_time=now() where sc_uid=:1")
	public void clearSinaInfoByUid(int uid);
	
	@SQL("update "+TABLE+" set qq_uid=0,qq_token='',qq_refresh_token='',modify_time=now() where sc_uid=:1")
	public void clearQQInfoByUid(int uid);
	
	@SQL("insert into " + TABLE + "(" + SINA_FIELDS + ") values(:1,:2,:3,:4,now(),now()) ON DUPLICATE KEY UPDATE sina_uid=:2 ,sina_token=:3 ,sina_refresh_token=:4 ,modify_time=now()")			
	public int createOrUpdateSina(int sc_uid, long sina_uid, String sina_token, String sina_refresh_token);
	
	@SQL("insert into " + TABLE + "(" + RR_FIELDS + ") values(:sc_uid,:rr_uid,:rr_token,:rr_refresh_token,now(),now()) ON DUPLICATE KEY UPDATE rr_uid=:rr_uid, rr_token=:rr_token ,rr_refresh_token=:rr_refresh_token ,modify_time=now()")			
	public int createOrUpdateRenRen(@SQLParam("sc_uid") int sc_uid, @SQLParam("rr_uid") long rr_uid, @SQLParam("rr_token") String rr_token, @SQLParam("rr_refresh_token") String rr_refresh_token);
	
	@SQL("insert into " + TABLE + "(" + QQ_FIELDS + ") values(:sc_uid,:qq_uid,:qq_token,:qq_refresh_token,now(),now()) ON DUPLICATE KEY UPDATE qq_uid=:qq_uid, rr_token=:rr_token ,rr_refresh_token=:rr_refresh_token ,modify_time=now()")			
	public int createOrUpdateQQ(@SQLParam("sc_uid") int sc_uid, @SQLParam("qq_uid") long qq_uid, @SQLParam("qq_token") String qq_token, @SQLParam("qq_refresh_token") String qq_refresh_token);

	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where rr_uid =:1")	
	public ThirdUser getByRrUid(long uid);
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where rr_uid in (:1) limit :2 ,:3")	
	public List<ThirdUser> getByRrUid(List<Long> rrUids, int offset, int count);
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where sina_uid =:1")	
	public ThirdUser getBySinaUid(long uid);
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where sina_uid in (:1) limit :2 ,:3")	
	public List<ThirdUser> getBySinaUid(List<Long> sinaUids, int offset, int count);

    @SQL("select sc_uid from " + TABLE + " where rr_uid in (:1)" )
    public List<Integer> getByRRUids(List<Long> rrUids);
	
	@SQL("update "+TABLE+" set rr_token=:2,rr_refresh_token=:3,modify_time=now() where sc_uid=:1")
	public int updateRenRen(int uid, String third_token,
                            String third_refresh_token);
	
	@SQL("update "+TABLE+" set sina_token=:2,sina_refresh_token=:3,modify_time=now() where sc_uid=:1")
	public int updateSina(int uid, String third_token,
                          String third_refresh_token);
	
	@SQL("update third_user set rr_uid =0,rr_token='',rr_refresh_token='',modify_time=now() where sc_uid = :1")
	public int unBundRenRen(int uid);

	@SQL("update third_user set sina_uid =0,sina_token='',sina_refresh_token='',modify_time=now() where sc_uid = :1")
	public int unBundSina(int uid);

	@SQL("select rr_uid from third_user  where sc_uid = :1")
	public long getRRState(int uid);
	
	@SQL("select sina_uid from third_user  where sc_uid = :1")
	public long getSinaState(int uid);
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where rr_uid!=0")
	public List<ThirdUser> getAllRRUsers();
	
	@SQL("select " + ALL_FIELDS +" from " + TABLE + " where rr_uid!=0 limit :1,:2")
	public List<ThirdUser> getRRUsers(long offset,long count);
	
	@SQL("select count(id) from "+ TABLE +" where rr_uid!=0")
	public long countRRUsers();
}
