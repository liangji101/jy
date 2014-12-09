package com.renren.ntc.sg.geo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.renren.ntc.sg.mongo.MongoDBUtil;
import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public abstract class AbstractGeoMongodbService {

	protected final Logger logger = Logger.getLogger(getClass());
	
	protected DB geoDb;
	protected DBCollection geoTable;
	
	protected AbstractGeoMongodbService() {
		try {

			geoDb = MongoDBUtil.getInstance().getDB();
            if(geoDb != null) {
				geoTable = MongoDBUtil.getInstance().getCollection();
			}
		} catch (Exception e) {
			logger.error("init geo mongodb fail", e);
		}
	}
	
	protected abstract ShopLocation parseUserLocation(DBObject obj);
	
	protected abstract DBObject makeLocationPoint(ShopLocation uloc);
	
	protected abstract DBObject makeLocationQuery(ShopLocation uloc, int maxDistance);
	
	protected boolean update(ShopLocation uloc) {
		if(geoTable != null) {
			DBObject user = new BasicDBObject();
			user.put("shop_id", uloc.getShop_id());
			user.put("loc", makeLocationPoint(uloc));
			user.put("uptime", new Date());
			geoTable.update(new BasicDBObject("shop_id", uloc.getShop_id()), user, true, false);
			return true;
		} else {
			logger.error("can not update because geo mongodb not inited");
			return false;
		}
	}
	
	protected boolean remove(long uid){
		if(geoTable != null) {
			WriteResult re = geoTable.remove(new BasicDBObject("uid", uid));
			return re.getN() > 0 ;
		} else {
			logger.error("can not remove because geo mongodb not inited");
			return false;
		}
	}
	
	
	protected ShopLocation queryByUid(long uid) {
		if(geoTable == null) {
			logger.error("can not queryByUid because geo mongodb not inited");
			return null;
		}
		DBObject query = new BasicDBObject();
		query.put("uid", uid);
		DBObject result = geoTable.findOne(query);
		if(result != null) {
			return this.parseUserLocation(result);
		} else {
			return null;
		}
	}
	
	protected Map<Long, ShopLocation> queryLocationMap(List<Long> uids) {
		if(geoTable == null) {
			logger.error("can not queryLocationMap because geo mongodb not inited");
			return Collections.emptyMap();
		}
		DBObject uidQuery = new BasicDBObject("$in", uids);
		DBObject query = new BasicDBObject();
		query.put("uid", uidQuery);
		// for performance, limit is 500
		DBCursor cursor = geoTable.find(query).limit(500);
		try {
			Map<Long, ShopLocation> map = new HashMap<Long, ShopLocation>();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				ShopLocation ul = this.parseUserLocation(obj);
				map.put(ul.getShop_id(), ul);
			}
			return map;
		} finally {
			cursor.close();
		}
	}
	
	protected List<GeoQueryResult> query(ShopLocation uloc, int maxDistance, int maxNum) {
		if(geoTable == null) {
			logger.error("can not query because geo mongodb not inited");
			return Collections.emptyList();
		}
		DBObject uidQuery = new BasicDBObject("$ne", uloc.getShop_id());
		DBObject query = new BasicDBObject();
		query.put("loc", makeLocationQuery(uloc, maxDistance));
		query.put("uid", uidQuery);
		DBCursor cursor = geoTable.find(query).limit(maxNum);
		try {
			List<GeoQueryResult> list = new ArrayList<GeoQueryResult>();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				ShopLocation ul = this.parseUserLocation(obj);
				GeoQueryResult gr = new GeoQueryResult();
				gr.setShopLocation(ul);
				list.add(gr);
			}
			return list;
		} finally {
			cursor.close();
		}
	}
	
}



