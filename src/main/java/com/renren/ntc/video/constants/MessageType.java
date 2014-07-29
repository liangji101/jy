package com.renren.ntc.video.constants;

/**
 * User: 帅伟良
 * Mail: weiliang.shuai@renren-inc.com
 * Date: 12-10-18
 * Time: 上午10:50
 */
public enum MessageType {
    MY("my_message"), FRIENDS("messages"), NONE("");

    private String mongoDBCollectionName;

    private MessageType(String mongoDBCollectionName) {
        this.mongoDBCollectionName = mongoDBCollectionName;
    }

    public String getMongoDBCollectionName() {
        return mongoDBCollectionName;
    }
}
