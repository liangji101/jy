package com.renren.ntc.video.service;

import com.renren.ntc.video.biz.dao.SequenceIdDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SequenceService {
	
	@Autowired
	SequenceIdDAO sequenceIdDAO;
 
	public long getCommentId() {
		return sequenceIdDAO.getSendInvNextId("ntc_user_id_seq");
	}
	
	public  long getShareId() {
		return sequenceIdDAO.getSendInvNextId("ntc_share_id_seq");
	} 
	
	public  long getMsgId() {
		return sequenceIdDAO.getSendInvNextId("ntc_feed_id_seq");
	} 
}
