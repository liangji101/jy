package com.renren.ntc.video.biz.dao;

import com.renren.ntc.video.entity.DeviceToken;
import com.renren.ntc.video.utils.Constants;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * @author dapeng.zhou email:dapeng.zhou@renren-inc.com
 *         与分享相关的dao
 */

@DAO(catalog = Constants.DB_TOKEN)
public interface DeviceTokenDAO {


    final static String FIELDS = "id, uid, device_token, client_version";

    final static String TABLE_NAME = "device_token";

    @SQL("insert into " + TABLE_NAME + "(uid, device_token, client_version) values (:1, :2, :3)")
    public int addDeviceToken(int uid, String deviceToken, String clientVersion);

    @SQL("update " + TABLE_NAME + " set uid = :1 where device_token = :2")
    public int updateUid(int uid, String deviceToken);
    
    @SQL("update " + TABLE_NAME + " set client_version = :1 where uid = :2")
    public int updateClient(String verNum, int uid);

    @SQL("update " + TABLE_NAME + " set uid = :1, client_version = :3 where device_token = :2")
    public int updateUidAndClientVersion(int uid, String deviceToken, String clientVersion);

    @SQL("select " + "uid, device_token" + " from " + TABLE_NAME + " where device_token = :1")
    public DeviceToken getByDeviceToken(String deviceToken);

    @SQL("select device_token from " + TABLE_NAME + " where uid = :1")
    public List<String> getDeviceTokensByUid(int uid);

    @SQL("select device_token from " + TABLE_NAME + " where uid in ( :1 )")
    public List<String> getDeviceTokensByUids(List<Integer> uids);

    @SQL("update " + TABLE_NAME + " set uid = 0 where device_token = :1")
    public int resetDevice(String deviceToken);

    @SQL("select device_token from " + TABLE_NAME + " where client_version != :1 limit :2, :3")
    public List<String> getDeviceTokenByNotTheClientVersion(String clientVersion, int offset, int count);

    @SQL("select count(*) from " + TABLE_NAME + " where client_version != :1")
    public int getDeviceTokenCountByNotTheClientVersion(String clientVersion);

    @SQL("select uid, device_token, client_version  from " +TABLE_NAME + " where uid !=0 and uid %100 = :1")
	public List<DeviceToken> getDeviceTokens(int model);
    
    @SQL("select uid, device_token, client_version  from " +TABLE_NAME + " where uid in (:1)")
   	public List<DeviceToken> getDeviceTokensByIds(List<Integer> ids);

    //后台查询用户行为数据分析
    @SQL("select uid from " +TABLE_NAME +" where uid in (select id from user where create_time between :1 and :2)")
    public List<Integer> getNewAPPUserIds(String createTime,String endTime);

    @SQL("select uid from " + TABLE_NAME + " where uid != 0")
	public List<Integer> getUids();
}