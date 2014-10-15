package com.renren.ntc.sg.dao;

import com.renren.ntc.video.utils.Constants;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = Constants.DB_TOKEN)
public interface shopDAO {

	//用于查询被分享到人人和分享到新浪的视频数，运营人员需求
	@SQL("select count(id) from asyncJob where type='syc_rrs' and create_time between :1 and :2")
	public int getShareToRRCount(String beginTime, String endTime);
	
	@SQL("select count(id) from asyncJob where type='syc_sns' and create_time between :1 and :2")
	public int getShareToSinaCount(String beginTime, String endTime);
	
	@SQL("select count(id) from asyncJob where uid=:1 and type='syc_rrs' and create_time between :2 and :3")
	public int getShareToRRCountByUid(int uid, String beginTime, String endTime);
	
	@SQL("select count(id) from asyncJob where uid=:1 and type='syc_sns' and create_time between :2 and :3")
	public int getShareToSinaCountByUid(int uid, String beginTime, String endTime);
	
	@SQL("select count(id) from asyncJob where uid=:1 and type='syc_comm' and create_time between :2 and :3")
	public int getCommentCountByUid(int uid, String beginTime, String endTime);
}
