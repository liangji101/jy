package com.renren.ntc.video.utils;

import com.mongodb.*;
import com.renren.ntc.video.formbean.Version;
import org.apache.log4j.Logger;

/**
 * Author: Weiliang Shuai Contact: weiliang.shuai@renren-inc.com Date: 12-8-8
 * Time: 下午2:43
 */
public final class ClientVersionUtils {

	private static final String VERSION_NUMBER = "verNum";
	private static final String VERSION_STRING = "verStr";
	private static final String VERSION_DESCRIPTION = "verDes";
	private static final String VERSION_URL = "verUrl";

	private static final Logger logger = Logger.getLogger(ClientVersionUtils.class);

	private ClientVersionUtils() {

	}

	public static boolean storeVersion(Version version) {
		
		DBCollection coll = MongoDBUtil.getInstance().getCollection(
				Constants.CLIENT_VERSION);
		BasicDBObject save = new BasicDBObject();
		save.put(VERSION_NUMBER, version.getVerNum());
		save.put(VERSION_STRING, version.getVerStr());
		save.put(VERSION_DESCRIPTION, version.getVerDes());
		save.put(VERSION_URL, version.getVerUrl());
		WriteResult re = coll.save(save);
		if(reCheck(re)){
			MemcachedUtil.getInstance().cacheVersion(version);
			return true;
		}
		return false;
	}
	
	public static Version getNewestVersion(){
        Version version  = MemcachedUtil.getInstance().getVersion();
        if(version == null) {
            version = parseVersion((BasicDBObject) getNewestVersionFromMongo());
            MemcachedUtil.getInstance().cacheVersion(version);
        }
		return version;
	}

	private static Version parseVersion(BasicDBObject s) {
        Version version = new Version();
        version.setVerStr(s.getString("verStr"));
        version.setVerNum(s.getLong("verNum"));
        version.setVerUrl(s.getString("verUrl"));
        version.setVerDes(s.getString("verDes"));
        return version;
    }
	
	private static boolean reCheck(WriteResult re) {
		if (null == re.getError()) {
			return true;
		}
		logger.info(re.getError());
		return false;
	}

	public static boolean isNewerThan(long version) {
        DBObject object = getNewestVersionFromMongo();
        return object != null && ((BasicDBObject) object).getLong("verNum") > version;
	}

    private static DBObject getNewestVersionFromMongo() {
        DBCursor cursor = MongoDBUtil.getInstance().getCollection(Constants.CLIENT_VERSION).find().sort(new BasicDBObject("verNum", -1)).limit(1);
        return cursor.next();
    }

}
