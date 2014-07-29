package com.renren.ntc.video.utils;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;


/**
 * @author allen
 *
 */
//todo  mongo db 这些代码都需要优化
public class MongoDBUtil {

	private Mongo m;
	private DB db;

	static Log logger = LogFactory.getLog(MongoDBUtil.class);

	private static MongoDBUtil instance = new MongoDBUtil();

	private MongoDBUtil() {
		try {
			m = new Mongo(Constants.HOST, 27017);
			db = m.getDB(Constants.DBNAME);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public static MongoDBUtil getInstance() {
		return instance;
	}

	//todo  需要添加超长list 对应。by yunming.zhu
	public boolean addAttention(int uid, int friend) {
		
		if (!append(friend, uid , Constants.ATTENTIONSPASS)) {
			return false;
		}
		boolean ok = append(uid,friend, Constants.ATTENTIONSACTIVE);
			return ok;
		//todo 删除失败的话就打个日志，让守门员来处理
	}
	
	//todo  需要添加超长list 对应。by yunming.zhu
	public boolean addPendingAttention(int uid, int friend) {
		return append(uid,friend  , Constants.PENDINGATTENTION) ;
		//todo 删除失败的话就打个日志，让守门员来处理
	}
	
	public boolean delPendingAttention(int uid, int target) {
		return remove(uid, target, Constants.PENDINGATTENTION);
	}
	
	
	public boolean remove(int uid, int target , String key){
		DBCollection coll = db.getCollection(key);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.UID, uid);
		BasicDBObject foj = new BasicDBObject(Constants.UID, target);
		BasicDBObject tbj = new BasicDBObject();
		tbj.put("$pull", new BasicDBObject(Constants.CONNECTIONS,
				foj));
		WriteResult re = coll.update(query, tbj);
		return reCheck(re);
	} 
	
	private boolean append(int uid, int target, String key) {
		DBCollection coll = db.getCollection(key);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.UID, uid);
		BasicDBObject foj = new BasicDBObject(Constants.UID, target);
		BasicDBObject tbj = new BasicDBObject();
		tbj.put("$addToSet", new BasicDBObject(Constants.CONNECTIONS, foj));
		WriteResult re = coll.update(query, tbj, true, false);
		return reCheck(re);
	}
	
	
	private boolean reCheck(WriteResult re) {
		if (null == re.getError()) {
			return true;
		}
		logger.info(re.getError());
		return false;
	}

	public Set<String> dbbrowser() {
		return db.getCollectionNames();
	}

	public boolean delAttention(int uid, int target) {
		if(!remove(uid, target, Constants.ATTENTIONSACTIVE)){
           return false;			
		}
		if(!remove(target, uid, Constants.ATTENTIONSPASS)){
			append(target, uid, Constants.ATTENTIONSPASS);
			return false;
		}
		return true;
	}
	
	 public boolean isAttention(int uid , int target,String type ) {
	    	DBCollection coll = db.getCollection(type);
			BasicDBObject query = new BasicDBObject();
			query.put(Constants.UID, uid);
			DBCursor cur = coll.find(query);
			BasicDBObject boj = null;
			BasicDBList e = null;
			if (cur.hasNext()) {
				boj = (BasicDBObject) cur.next();
			}
			if(null == boj){
				return false;
			}
			e =  (BasicDBList) boj.get(Constants.CONNECTIONS);
			if(null == e){
				return false;
			}
			for (int i = 0; i < e.size(); i++) {
				BasicDBObject obj = (BasicDBObject) e.get(i);
				if ((Integer)obj.get(Constants.UID) == target) {
					return true;
				}
			}
			return false;
		}
	
    public boolean isAttention(int uid , int target) {
    	return isAttention(uid,target , Constants.ATTENTIONSACTIVE);
	}


	public int getAttentionsCount(int uid, String type) {
		DBCollection coll = db.getCollection(type);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.UID, uid);
		DBCursor cur = coll.find(query);
		if (!cur.hasNext()) {
		    return 0;
		}
	    BasicDBObject ob = ((BasicDBObject) cur.next());
	    BasicDBList e = (BasicDBList) ob.get(Constants.CONNECTIONS);
		return e.size();
	}
    
	public BasicDBList getAttentions(int uid, String type,int offset ,int count) {
		DBCollection coll = db.getCollection(type);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.UID, uid);
		BasicDBList attentions = new BasicDBList();
		BasicDBList lls = new BasicDBList();
        try {
        Reverser reverser = reverseOffset(getAttentionsCount(uid, type), offset, count);
		lls.add(reverser.getOffset());
		lls.add(reverser.getCount());
        } catch (IllegalArgumentException e) {
            logger.warn(e);
            return attentions;
        }
		BasicDBObject limit = new BasicDBObject();
		limit.put("$slice", lls);
		BasicDBObject b =   new BasicDBObject();
		b.put(Constants.CONNECTIONS,limit);
		DBCursor  cur = coll.find(query,b);
		if (cur.hasNext()) {
			BasicDBObject ob = ((BasicDBObject) cur.next());
			attentions = (BasicDBList) ob.get(Constants.CONNECTIONS);
		}
		// 需要添加超长list 对应。by yunming.zhu
		if (null == attentions) {
			attentions =  new BasicDBList();
		}
        Collections.reverse(attentions);
		return attentions;
	}

	public BasicDBList getAttentions(int uid, String type) {
		DBCollection coll = db.getCollection(type);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.UID, uid);
		DBCursor cur = coll.find(query);
		BasicDBObject boj = null;

		if (cur.hasNext()) {
			boj = (BasicDBObject) cur.next();
		}
		BasicDBList e = new BasicDBList();

		// 需要添加超长list 对应。by yunming.zhu
		if (null != boj) {
			e = (BasicDBList) boj.get(Constants.CONNECTIONS);
		}
		return e;
	}
	
	//todo comment_id 重复是要抛出异常  添加评论 
//	public boolean addComment(String comment_index, long comment_id, int uid,
//			String content) {
//		
//		DBCollection coll = db.getCollection(Constants.COMMENTS);
//		BasicDBObject query = new BasicDBObject();
//		query.put(Constants.COMMENT_INDEX, comment_index);
//		DBCursor cur = coll.find(query);
//		BasicDBObject boj = null;
//		if (cur.hasNext()) {
//			boj = (BasicDBObject) cur.next();
//		}
//		
//		Long time = System.currentTimeMillis();
//		DBObject comment = new BasicDBObject();
//		comment.put("comment_id", comment_id);
//		comment.put("uid", uid);
//		
//		if(content == null){
//			logger.warn("connent is null");
//			return false;
//		}
//		content.replace("\"", "\\\"");//不进行替换,会报异常
//		comment.put("content", content.trim());
//		comment.put("time", time);
//
//		if (null == boj) {
//			BasicDBObject bo = new BasicDBObject();
//			BasicDBList comments = new BasicDBList();
//			comments.add(comment);
//			bo.put(Constants.COMMENT_INDEX, comment_index);
//			bo.put(Constants.COMMENTS, comments);
//			WriteResult re = coll.save(bo);
//			return reCheck(re);
//		}
//		BasicDBObject co= getComment2(comment_index,  comment_id);
//		if (null != co) {
//			//已经插入完成
//			return true;
//		}
//		BasicDBObject tbj = new BasicDBObject();
//		tbj.put("$push", new BasicDBObject(Constants.COMMENTS, comment));
//		WriteResult re = coll.update(query, tbj);
//		return reCheck(re);
//	}
	
	public boolean addComment(String comment_index, long comment_id, int uid,
			String content,int desUid) {
		
		DBCollection coll = db.getCollection(Constants.COMMENTS);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.COMMENT_INDEX, comment_index);
		DBCursor cur = coll.find(query);
		BasicDBObject boj = null;
		if (cur.hasNext()) {
			boj = (BasicDBObject) cur.next();
		}
		
		Long time = System.currentTimeMillis();
		DBObject comment = new BasicDBObject();
		comment.put("comment_id", comment_id);
		comment.put("uid", uid);
		if(desUid!=0){
			comment.put("desUid",desUid);
		}
		
		if(content == null){
			logger.warn("connent is null");
			return false;
		}
		content.replace("\"", "\\\"");//不进行替换,会报异常
		comment.put("content", content.trim());
		comment.put("time", time);

		if (null == boj) {
			BasicDBObject bo = new BasicDBObject();
			BasicDBList comments = new BasicDBList();
			comments.add(comment);
			bo.put(Constants.COMMENT_INDEX, comment_index);
			bo.put(Constants.COMMENTS, comments);
			WriteResult re = coll.save(bo);
			return reCheck(re);
		}
		BasicDBObject co= getComment2(comment_index,  comment_id);
		if (null != co) {
			//已经插入完成
			return true;
		}
		BasicDBObject tbj = new BasicDBObject();
		tbj.put("$push", new BasicDBObject(Constants.COMMENTS, comment));
		WriteResult re = coll.update(query, tbj);
		return reCheck(re);
	}

	// 根据comment_index,comment_id删除某条评论
	public boolean delComment(String comment_index, long comment_id) {
		DBCollection coll = db.getCollection(Constants.COMMENTS);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.COMMENT_INDEX, comment_index);
		DBCursor cur = coll.find(query);
		BasicDBObject boj = null;
		if (cur.hasNext()) {
			boj = (BasicDBObject) cur.next();
		}
		if (boj != null) {
			BasicDBObject obj = getComment(comment_index, comment_id);
			if (null != obj) {
				BasicDBObject tbj = new BasicDBObject();
				tbj.put("$pull", new BasicDBObject(Constants.COMMENTS, obj));
				WriteResult re = coll.update(query, tbj);
				return reCheck(re);
			}
		}
		return true;
	}

	public boolean updateComment(String comment_index, long comment_id, int uid,
			String content) {
		return delComment(comment_index, comment_id)
				&& addComment(comment_index, comment_id, uid, content,0);
	}

	// 根据某个comment_index获取针对该comment_index的所有评论
	public BasicDBList getComments(String comment_index) {
		DBCollection coll = db.getCollection(Constants.COMMENTS);
		BasicDBObject query = new BasicDBObject();
		query.put(Constants.COMMENT_INDEX, comment_index);
		BasicDBList comments = null;
		BasicDBObject boj = null;
		DBCursor cur = coll.find(query);
		if (cur.hasNext()) {
			boj = (BasicDBObject) cur.next();
		}
		if (boj != null) {
			comments = (BasicDBList) boj.get(Constants.COMMENTS);
		}
		if (null == comments) {
			comments = new BasicDBList();
		}
		return comments;
	}
	
	public BasicDBList getCommentsReverse(String comment_index, int offset, int count) {
		BasicDBList comments = getComments(comment_index);
		int length = comments.size();
		logger.info("Comments count: " + length);
		BasicDBList ret = new BasicDBList();
		if((count+offset)>length) {
			count = length-offset;
		}
		for(int i=0; i<count; i++) {
			ret.add(comments.get(length-i-offset-1));
		}
		return ret;
	}
	
	
	// 根据某个comment_index获取针对该comment_index的所有评论
	public BasicDBList getComments(String comment_index,int offset, int count) {
		DBCollection coll = db.getCollection(Constants.COMMENTS);
		BasicDBObject query = new BasicDBObject();
		//这里的查询很可能可以省略掉
		query.put(Constants.COMMENT_INDEX, comment_index);
		BasicDBList comments = new BasicDBList();;
		BasicDBObject boj = null;
		DBCursor cur = coll.find(query);
		if (cur.hasNext()) {
			boj = (BasicDBObject) cur.next();
		}
		if (boj != null) {
			//subset of array;
			//在mongo中存评论时是按照时间排序的，但是显示到界面时需要逆序显示，所以取评论的时候从后面取起
			int length = this.getComments(comment_index).size();
			BasicDBList lls = new BasicDBList();
            try {
                Reverser reverser = reverseOffset(length, offset, count);
                lls.add(reverser.getOffset());
                lls.add(reverser.getCount());
            } catch (IllegalArgumentException e) {
                logger.warn(e.getMessage());
                return comments;
            }
			BasicDBObject limit = new BasicDBObject();
			limit.put("$slice", lls);
			BasicDBObject b =   new BasicDBObject();
			b.put(Constants.COMMENTS,limit);
			cur = coll.find(query,b);
			if (cur.hasNext()) {
				BasicDBObject ob = ((BasicDBObject) cur.next());
				comments = (BasicDBList) ob.get(Constants.COMMENTS);
			}
		}
		if (null == comments) {
			comments = new BasicDBList();
		}
		// 这个逻辑是谁加的 。。。找我一下。朱允铭
		//Collections.reverse(comments);
	    //
		return comments;
	}


	//这段逻辑需要修改
	public BasicDBObject getComment(String comment_index, long comment_id) {
		BasicDBList comments = getComments(comment_index);
		//没有针对此comment_index的评论
		if(comments.size() == 0){
			return new BasicDBObject();
		}
		BasicDBObject comment = null;
		int i;
		for (i = 0; i < comments.size(); i++) {
			BasicDBObject obj = (BasicDBObject) comments.get(i);
			if ((Long)obj.get("comment_id") == comment_id ) {
				comment = (BasicDBObject) comments.get(i);
				return comment;
			}
		}
		return null;		
	}

	/*
	 * 查询一个视频是否存在指定comment_id的评论,为了不影响上面那个方法,我这里新写一个,by chenwenbiao
	 */
	public BasicDBObject getComment2(String comment_index, long comment_id) {
		BasicDBObject condition = new BasicDBObject();
		condition.put("comment_index", comment_index);
		condition.put("comments.comment_id", comment_id);
		DBCollection coll = db.getCollection(Constants.COMMENTS);
		DBCursor dbCursor = coll.find(condition);
		BasicDBObject result = null;
		while(dbCursor.hasNext()){
			result = (BasicDBObject) dbCursor.next();
		}
		
		return result;
	}
	
	
    /**
     * 根据集合名获取集合对象
     * @param collName 集合名称
     * @return
     */
    public DBCollection getCollection(String collName) {
        return db.getCollection(collName);
    }
	
	/**
	 * @param args
	 * @throws java.net.UnknownHostException
	 * @throws com.mongodb.MongoException
	 */
    
    public static void update(int uid){
    	MongoDBUtil mongoDBUtil = MongoDBUtil.getInstance();
		DBCollection msgColl = mongoDBUtil.getCollection("messages");
		BasicDBObject condition = new BasicDBObject();
		condition.put("UID", uid);
		DBCursor dbCursor = msgColl.find(condition);
		BasicDBObject result = null;
		while(dbCursor.hasNext()){
			result = (BasicDBObject) dbCursor.next();
//			System.out.println(result);
			Object _id = result.get("_id");
			BasicDBObject condition2 = new BasicDBObject();
			condition2.put("_id", _id);
			int message_size = result.getInt("message-size");
			System.out.println("message-size is "+message_size);
			Object obj = result.get("messages");
			JSONArray array = JSONArray.fromObject(obj);
			int realSize = array.size();
			System.out.println("realSize is "+realSize);
			BasicDBObject newData = new BasicDBObject();
			newData.put("message-size", realSize);
			BasicDBObject update = new BasicDBObject();
			update.put("$set", newData);
			WriteResult rs = msgColl.update(condition2,update);
			if(rs.getError() != null){
				System.out.println(rs.getError());
			}
		}
//		int uid = result.getInt("UID");
		System.out.println("the uid is "+ uid +"---------------------------------------------------------------");
//		System.out.println(result);
		
    }
    
    
	public static void main(String[] args) throws UnknownHostException,
			MongoException {
//		int uid = 213;
//		System.out.println(MongoDBUtil.getInstance().addAttention(uid, 23));
//        System.out.println(MongoDBUtil.getInstance().addAttention(uid, 24));
//        System.out.println(MongoDBUtil.getInstance().addAttention(uid, 24));
//        System.out.println(MongoDBUtil.getInstance().addAttention(uid, 25));
//        System.out.println(MongoDBUtil.getInstance().addAttention(uid, 2423));
//        System.out.println(MongoDBUtil.getInstance().delAttention(uid, 2423));
//		System.out.println(MongoDBUtil.getInstance().getAttentions(uid, Constants.ATTENTIONSACTIVE,0,2));
//		System.out.println(MongoDBUtil.getInstance().getAttentions(uid, Constants.ATTENTIONSPASS,0,10));
//		System.out.println(MongoDBUtil.getInstance().getAttentions(uid, Constants.ATTENTIONSPASS,1,3));
//			
//		System.out.println(MongoDBUtil.getInstance().isAttention(uid, 2423));
//		System.out.println(MongoDBUtil.getInstance().isAttention(uid, 25));
		
//		System.out.println(MongoDBUtil.getInstance().addComment("SHARE_4961115982468143", 122, 123123, "sfsd"));
//		
//		System.out.println(MongoDBUtil.getInstance().addComment("SHARE_4961115982468143", 132, 123123, "sfsd"));
//		System.out.println(MongoDBUtil.getInstance().addComment("SHARE_4961115982468143", 1332, 123123, "sfsd"));
//		System.out.println(MongoDBUtil.getInstance().addComment("SHARE_4961115982468143", 142, 123123, "sfsd"));
//		System.out.println(MongoDBUtil.getInstance().getComments("SHARE_4961115982468143"));
//		System.out.println(MongoDBUtil.getInstance().getComments("VIDEO_12126"));
		
		MongoDBUtil mongoDBUtil = MongoDBUtil.getInstance();
		DBCollection msgColl = mongoDBUtil.getCollection("messages");
		BasicDBObject limit = new BasicDBObject();
		limit.put("$gte", 50);
		BasicDBObject b =   new BasicDBObject();
		b.put("message-size",limit);
		DBCursor dbCursor = msgColl.find(b);
		BasicDBObject result = null;
		int n=0;
		while(dbCursor.hasNext() && n<2){
			result = (BasicDBObject) dbCursor.next();
			if(result.get("UID") == null){
				continue;
			}
			int uid = result.getInt("UID");
			Object obj = result.get("messages");
			JSONArray array = JSONArray.fromObject(obj);
			System.out.println(array);
			n++;
//			int message_size = result.getInt("message-size");
//			int realSize = array.size();
//			if(message_size != realSize){
//				System.out.println("uid 为 "+uid+"的用户的数据异常,message_size is "+message_size+",realSize is "+realSize);
//				update(uid);
//				n++;
//			}
		}	
//			BasicDBObject condition =new BasicDBObject();
//			condition.put("UID", uid);
//			BasicDBObject newData = new BasicDBObject();
//			newData.put("message-size", realSize);
//			BasicDBObject update = new BasicDBObject();
//			update.put("$set", newData);
//			msgColl.update(condition,update);
//		}
//		System.out.println("every thing is ok!");
//		System.out.println(n+"个用户有异常");
	}

    public static Reverser reverseOffset(int size, int offset, int count) {
        int reverseOffset = size - offset - count;
        int reverseCount = count;
        if(reverseOffset < 0) {
            reverseCount = reverseOffset + count;
            if(reverseCount <= 0){
                throw new IllegalArgumentException("reverse count is le than 0");
            }
            reverseOffset = 0;
        }
        return new Reverser(reverseOffset, reverseCount);
    }
}
	