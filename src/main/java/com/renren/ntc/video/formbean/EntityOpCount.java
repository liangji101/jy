package com.renren.ntc.video.formbean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: lanch
 * Date: 12-9-25
 * Time: 上午11:19
 */
public class EntityOpCount implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 934344723343302277L;

    public static final String MONGODB_COLUMN_ENTITY_ID = "entity_id";
    public static final String MONGODB_COLUMN_OP_COUNT = "op_count";
    public static final String MONGODB_COLUMN_OP_TYPE = "op_type";
    public static final String MONGODB_COLUMN_SCORE = "score";
    public static final String MONGODB_COLUMN_CREATE_TIME = "create_time";

	private long entityId;

    private long opCount;

    private String opType = "";

    private double score;

    private long createTime = Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getOpCount() {
        return opCount;
    }

    public void setOpCount(long opCount) {
        this.opCount = opCount;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public long getEntityId() {

        return entityId;

    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public EntityOpCount(long entityId, long opCount, String opType) {
        this.entityId = entityId;
        this.opCount = opCount;
        this.opType = opType;
    }

    public EntityOpCount() {
    }

    @Override
    public String toString() {
        return "EntityOpCount{" +
                "entityId=" + entityId +
                ", opCount=" + opCount +
                ", opType='" + opType + '\'' +
                ", score=" + score +
                '}';
    }

    public static EntityOpCount fromMongoDBObject(DBObject object) {
        EntityOpCount entityOpCount = new EntityOpCount();
        entityOpCount.setCreateTime(Long.valueOf(object.get(MONGODB_COLUMN_CREATE_TIME).toString()));
        entityOpCount.setEntityId(Long.valueOf(object.get(MONGODB_COLUMN_ENTITY_ID).toString()));
        entityOpCount.setOpCount(Long.valueOf(object.get(MONGODB_COLUMN_OP_COUNT).toString()));
        entityOpCount.setOpType(object.get(MONGODB_COLUMN_OP_TYPE).toString());
        entityOpCount.setScore(Double.valueOf(object.get(MONGODB_COLUMN_SCORE).toString()));
        return entityOpCount;
    }

    public static DBObject toMongoDBObject(EntityOpCount entityOpCount) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put(MONGODB_COLUMN_OP_TYPE, entityOpCount.getOpType());
        basicDBObject.put(MONGODB_COLUMN_SCORE, entityOpCount.getScore());
        basicDBObject.put(MONGODB_COLUMN_CREATE_TIME, entityOpCount.getCreateTime());
        basicDBObject.put(MONGODB_COLUMN_ENTITY_ID, entityOpCount.getEntityId());
        basicDBObject.put(MONGODB_COLUMN_OP_COUNT, entityOpCount.getOpCount());
        return basicDBObject;
    }

    public static List<DBObject> toMongoDBObjects(List<EntityOpCount> entityOpCounts) {
        List<DBObject> dbObjects = new ArrayList<DBObject>();
        if(entityOpCounts != null) {
            for(EntityOpCount entityOpCount : entityOpCounts) {
                dbObjects.add(toMongoDBObject(entityOpCount));
            }
        }
        return dbObjects;
    }
}
