package com.renren.ntc.video.biz.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import com.renren.ntc.video.biz.model.Entity;
import com.renren.ntc.video.formbean.EntityOpCount;
import com.renren.ntc.video.utils.Constants;

/**
 * 
 * @author dapeng.zhou email:dapeng.zhou@renren-inc.com
 *与分享相关的dao
 */

@DAO(catalog = Constants.DB_SHARE)
public interface EntityUserDAO {
	
	
	 final static String FIELDS = "entity_id,entity_type,uid,type";
	 
	 final static String SELECT_FIELDS = "entity_id,entity_type,uid,type,create_time";
	
	 final static String TABLENAME = "entity_user";
	 final static String TABLENAMEOPP = "user_entity";
	 
	@SQL("insert ignore into " + TABLENAME + "("+  FIELDS +") values (:1,:2,:3,:4)")
	public int addEntityLinks(long entity_id, long etype, int uid, String type);
	
	@SQL("select uid from " + TABLENAME + " where entity_id=:1 and type = like")
	public List<Integer> getLikeUsers(long entity_id);
	
	
	@SQL("insert ignore into " + TABLENAMEOPP + "("+  FIELDS +") values (:1,:2,:3,:4)")
	public int addUserLinks(long entity_id, long etype, int uid, String type);
	
    @SQL("delete from " + TABLENAME + " where entity_id=:1 and entity_type=:2 and uid=:3 and type=:4")
	public int deleteEntityLinks(long entity_id, long etype, int uid, String type);
	
	@SQL("delete from " + TABLENAMEOPP + " where entity_id=:1 and entity_type=:2 and uid=:3 and type=:4")
	public int deleteUserLinks(long entity_id, long etype, int uid, String type);

	
	@SQL("select count(uid) from " + TABLENAME + " where entity_id=:1 and entity_type=:2 and type=:3")
	public int getLikeUserCount(long en_id, int etype, String type);

	@SQL("select uid from " + TABLENAME + " where entity_id=:1 and entity_type=:2 and type=:3")
	public List<Integer> getLikeUser(long en_id, int etype, String string);
	
	
	@SQL("select uid from "
			+ TABLENAME
			+ " where entity_id=:1 and entity_type=:2 and type=:3 order by create_time desc  limit :4")
	public List<Integer> getLikeUser(long en_id, int etype, String type, int count);
	
	
	@SQL("select uid from "
			+ TABLENAME
			+ " where entity_id=:1 and entity_type=:2 and type=:3 order by create_time desc  limit :4")
	public List<Integer> getMarkUser(long en_id, int etype, String type, int count);

	
	@SQL("select count(entity_id) from " + TABLENAMEOPP + " where uid=:1 and type = :2 ")
	public int getEnCount(int uid, String type);

	
	@SQL("select count(uid) from "
			+ TABLENAME
			+ " where entity_id=:1 and entity_type=:2 and type=:3" )
	public int getUserCount(long en_id, int etype, String type);

	
	@SQL("select count(uid) from "
			+ TABLENAME
			+ " where entity_id=:1 and entity_type=:2 and uid = :3 and type=:4" )
	public int isLike(long en_id, int etype, int uid, String type);

	
	@SQL("select entity_id , entity_type from " + TABLENAMEOPP + " where uid=:1 and type = :2 limit :3,:4")
	public  List <Entity> getEn(int uid, String type, int offset, int count);
	
	@SQL("select entity_id  from " + TABLENAMEOPP + " result where uid=:1 and type = :3 and entity_type= :2 order by create_time desc limit :4,:5")
	public  List <Long> getEnId(int uid, int en_type, String type, int offset, int count);

	@SQL("delete from " + TABLENAME + " where entity_id=:1")
	public int deleteShareForEntityUser(long shareId);
	
	@SQL("delete from " + TABLENAMEOPP + " where entity_id=:1")
	public int deleteShareForUserEntity(long shareId);

    @SQL("select entity_id ,count(*) as op_count ,type as op_type from ntc_user.entity_user where entity_id in (select share_id from ntc_user.share where (create_time between :1 and :2) and content like :5 and chk='y' and del =0) and uid in (select uid from ntc_token.third_user) and type= :4 group by entity_id order by op_count desc limit :3")
    public List<EntityOpCount> getTopOperatedEntity(String fromTime, String endTime, int limit, String opType,String title);
    
    @SQL("select entity_id ,count(*) as op_count ,type as op_type from ntc_user.entity_user where entity_id in (select share_id from ntc_user.share where (create_time between :1 and :2) and chk='y' and del =0 and share_id not in (:5)) and type= :4 group by entity_id order by op_count desc limit :3")
    public List<EntityOpCount> getTopOperatedEntitySupplement(String fromTime, String endTime, int limit, String opType,List<Long> ids);
    
    //运营需要的数据
    @SQL("select count(*) from " + TABLENAME + " where type='like' and create_time between :1 and :2")
    public int countLike(String beginTime,String endTime);
    
    @SQL("select entity_id,count(*) as op_count,type as op_type from entity_user where type=:1 and uid not in (1721,1722,1723,1724,1711,1712,1713,1714,1716,1717) group by entity_id order by op_count desc limit :2")
    public List<EntityOpCount> getTopOperatedEntityIgnoreTime(String opType,int limit);
}