package com.renren.ntc.video.service;

import com.renren.ntc.video.biz.dao.EntityUserDAO;
import com.renren.ntc.video.biz.logic.UserService;
import com.renren.ntc.video.biz.model.Entity;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.formbean.EntityOpCount;
import com.renren.ntc.video.utils.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityUserService {

	private static final Logger logger = Logger.getLogger(EntityUserService.class);

	@Autowired
	private EntityUserDAO entityuserdao;

    
    @Autowired
    private UserService userService;


	
	
	// to do 加一些回滚逻辑
	public boolean like(int uid, long en_id, int etype) {
		try {
			entityuserdao.addEntityLinks(en_id, etype, uid, Constants.LIKE);
			entityuserdao.addUserLinks(en_id, etype, uid, Constants.LIKE);
			// 全部加入异步逻辑
		} catch (Exception e) {
			System.out.println("//Error " + e.getLocalizedMessage());
		}
		return true;
	}
	
	public int isLike(long en_id, int etype, int uid) {
		return entityuserdao.isLike(en_id, etype, uid, Constants.LIKE);
	}
	
	public boolean  isMark(long en_id, int etype, int uid) {
		 return entityuserdao.isLike(en_id, etype, uid, Constants.MARK) > 0 ;
	}

	public boolean unlike(int uid, long en_id, int etype) {
		entityuserdao.deleteEntityLinks(en_id, etype, uid, Constants.LIKE);
		entityuserdao.deleteUserLinks(en_id, etype, uid, Constants.LIKE);
		return true;
	}

    public List<EntityOpCount> getTopLikeCountInYesterday(int limit) {
        List<EntityOpCount> entityOpCounts = MemcachedUtil.getInstance().getYesterdayEntityOpCount();
        if(entityOpCounts == null || entityOpCounts.size() == 0) {
            if(entityOpCounts == null || entityOpCounts.size() == 0) {
                entityOpCounts = entityuserdao.getTopOperatedEntity("2012-10-31 00:00:00", "2012-11-10 23:59:59", limit, Constants.LIKE , "%\"title\":\"#你幸福吗#%\"%");
                if(entityOpCounts.size()<15){
                	logger.info("###########"+entityOpCounts.size());
                	int limit2=15-entityOpCounts.size();
                	List<Long> ids =new ArrayList<Long>();
                	for(EntityOpCount eoc :entityOpCounts){
                		ids.add(eoc.getEntityId());
                	}
                	List<EntityOpCount> entityOpCountsSupplement = entityuserdao.getTopOperatedEntitySupplement("2012-10-31 00:00:00", "2012-11-10 23:59:59", limit2, Constants.LIKE , ids);
                	for(int i =0 ;i < limit2;i++){
                		entityOpCounts.add(entityOpCountsSupplement.get(i));
                		logger.info("###########"+entityOpCountsSupplement.get(i).getEntityId()+"########");
                	}
                }
                MemcachedUtil.getInstance().cacheYesterdayEntityOpCount(entityOpCounts);
            }
        }
        return entityOpCounts;
    }

	public boolean mark(int iUid, long eid, int eType) {
		try {
			entityuserdao.addUserLinks(eid, eType, iUid, Constants.MARK);
			entityuserdao.addEntityLinks(eid, eType, iUid, Constants.MARK);
			// msgService.sendMsg(iUid, desUid, "", eid, Constants.MSG_MARK);
		} catch (Exception e) {
			System.out.println("// mark 失败" + e.getLocalizedMessage());
			return false;
		}
		return true;
	}

	public int getLikeCount(long en_id, int etype) {
		return entityuserdao.getLikeUserCount(en_id, etype, Constants.LIKE);
	}

	public List<Integer> getLikeUsers(long en_id, int etype) {
		return entityuserdao.getLikeUser(en_id, etype, Constants.LIKE);
	}

	public int getMarkCount(int uid) {
		return entityuserdao.getEnCount(uid, Constants.MARK);
	}

	public int getLikeCount(int uid) {
		return entityuserdao.getEnCount(uid, Constants.LIKE);
	}

	public int getMarkUserCount(long en_id, int etype) {
		return entityuserdao.getUserCount(en_id, etype, Constants.MARK);
	}

	public int getLikeUserCount(long en_id, int etype) {
		return entityuserdao.getUserCount(en_id, etype, Constants.LIKE);
	}

	public List<Integer> getLikeUsers(long en_id, int en_type, int like_count) {

		return entityuserdao.getLikeUser(en_id, en_type, Constants.LIKE,
				like_count);
	}

	public List<Integer> getMarkUsers(long en_id, int en_type, int mark_count) {

		return entityuserdao.getLikeUser(en_id, en_type, Constants.MARK,
				mark_count);
	}

	public List<Entity> getMarkEn(int uid, int offset, int count) {
		return entityuserdao.getEn(uid, Constants.MARK, offset, count);

	}

	public List<Long> getMarkEnId(int uid, int en_type, int offset, int count) {
		return entityuserdao.getEnId(uid, en_type, Constants.MARK, offset,
				count);

	}

	public List<Long> getLikeEnId(int uid, int en_type, int offset, int count) {
		return entityuserdao.getEnId(uid, en_type, Constants.LIKE, offset,
				count);

	}

	public boolean unmark(int uid, long eid, int type) {
		entityuserdao.deleteEntityLinks(eid, type, uid, Constants.MARK);
		entityuserdao.deleteUserLinks(eid, type, uid, Constants.MARK);
		return true;
	}
	
	public void removeShare(long shareId) {
		entityuserdao.deleteShareForEntityUser(shareId);
		entityuserdao.deleteShareForUserEntity(shareId);
	}
	
	public boolean watch(int iUid, long eid, int eType) {
		try {
			entityuserdao.addUserLinks(eid, eType, iUid, Constants.WATCH);
			entityuserdao.addEntityLinks(eid, eType, iUid, Constants.WATCH);
			// msgService.sendMsg(iUid, desUid, "", eid, Constants.MSG_MARK);
		} catch (Exception e) {
			System.out.println("// watch 失败" + e.getLocalizedMessage());
			return false;
		}
		return true;
	}
	
	public int getWatchCount(long en_id, int etype){
		return entityuserdao.getUserCount(en_id, etype, Constants.WATCH);
	}
	
	public static void main(String [] args){
		int offsetUser=(int) (Math.random()*190);
		System.out.println(offsetUser);
	}
	
	public int countLike(String beginTime,String endTime){
		return entityuserdao.countLike(beginTime, endTime);
	}
	
	public List<EntityOpCount> getTopOperatedEntityIgnoreTime(String opType,int limit){
		return entityuserdao.getTopOperatedEntityIgnoreTime(opType, limit);
	}
}
