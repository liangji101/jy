package com.renren.ntc.video.biz.dao;


import com.renren.ntc.video.biz.model.InfiniteSk;
import com.renren.ntc.video.utils.Constants;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO(catalog = Constants.DB_TOKEN)
public interface InfiniteSkDAO {

	
    final String SELECT_FILEDS = " user_id, app_id, session_key, session_secret ";
    
	final String INSERT_FILEDS = " user_id, app_id, session_key, session_secret ";
	
	final String TABLE = " sk_infinite ";
	
	@SQL("insert into  " +TABLE + "(" + INSERT_FILEDS + ")"+
    " values (:inSK.userId,:inSK.appId,:inSK.sessionKey,:inSK.sessionSecret)")
	public int generateKeys(@SQLParam("inSK") InfiniteSk inSK) ;
	
	@SQL("delete from  " +TABLE + " where user_id=:userid and app_id=:appId " )
	public void delete(@SQLParam("userId") int userId, @SQLParam("appId") int appId);

	@SQL("select  " + SELECT_FILEDS + " from "+ TABLE + " where user_id=:userId and app_id=:appId " )
	public InfiniteSk get(@SQLParam("userId") int userId, @SQLParam("appId") int appId);
	

}
