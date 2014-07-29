package com.renren.ntc.video.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renren.ntc.video.biz.dao.RelationShipDAO;

@Service
public class RelationShipMysqlService implements RelationShipService {
	  @Autowired 
	  private RelationShipDAO relationShipDAO;
	    
	    // 这里会自动merge 新关注好友的新鲜事
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#addAttention(int, int)
		 */
		@Override
		public boolean addAttention(int uid, int to){
			if(!isAttention(uid,to)){
				try{        
					relationShipDAO.addActiveAttention(uid,to);
					relationShipDAO.addPassAttention(uid,to);
				}catch(Exception e){
					relationShipDAO.delActiveAttention(uid,to);
					relationShipDAO.delPassAttention(uid,to);
					return false;
				}
				return true;
			}else{
				return false;
			}
			
		}

		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#delAttention(int, int)
		 */
		@Override
		public boolean delAttention(int uid, int to){
			try{
				relationShipDAO.delActiveAttention(uid, to);
			}catch(Exception e){
				return false;
			}
			try{
				relationShipDAO.delPassAttention(uid, to);
			}catch(Exception e){
				relationShipDAO.addActiveAttention(uid, to);
				return false;
			}
			return true;
		}
		
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getPassAttentionsCount(int)
		 */
		@Override
		public int getPassAttentionsCount(int to){
			int count = relationShipDAO.getPassAttentionsCount(to);
			return count;
		}
		
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getActiveAttentionsCount(int)
		 */
		@Override
		public int getActiveAttentionsCount(int uid){
			int count = relationShipDAO.getActiveAttentionsCount(uid);
			return count;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#addPendingAttention(int, int)
		 */
		@Override
		public boolean addPendingAttention (int uid, int to){
			try{
				relationShipDAO.addPendingAttention(uid, to);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#delPendingAttention(int, int)
		 */
		@Override
		public boolean delPendingAttention (int uid, int to){
			try{
				relationShipDAO.delPendingAttention(uid, to);
			}catch(Exception e){
				return false;
			}
			return true;
		}
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#isPendingAttention(int, int)
		 */
		@Override
		public boolean isPendingAttention(int uid,int to){
			return relationShipDAO.isPendingAttention(uid,to) > 0;
		}

		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getActiveAttentions(int, int, int)
		 */
		@Override
		public List<Integer>  getActiveAttentions(int uid,int offset ,int count){
			List<Integer> uids = relationShipDAO.getActiveAttentions(uid,offset,count);
			return uids;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getPassAttentions(int, int, int)
		 */
		@Override
		public List<Integer>  getPassAttentions(int to,int offset ,int count){
			List<Integer> uids = relationShipDAO.getPassAttentions(to,offset,count);
			return uids;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getPendingAttentionsCount(int)
		 */
		@Override
		public int getPendingAttentionsCount(int uid){
			int count =  relationShipDAO.getPendingAttentionsCount(uid);
			return count;
		}
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getPendingAttentions(int, int, int)
		 */
		@Override
		public List<Integer> getPendingAttentions(int uid, int offset, int count) {
			List<Integer> uids = relationShipDAO.getPendingAttentions(uid,offset,count);
			return uids;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getPassAttentions(int)
		 */
		@Override
		public List<Integer>  getPassAttentions(int to){
			List<Integer> uids = relationShipDAO.getPassAttentions(to);
			return uids;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getActiveAttentions(int)
		 */
		@Override
		public List<Integer>  getActiveAttentions(int uid){
			List<Integer> uids = relationShipDAO.getActiveAttentions(uid);
			return uids;
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#isAttention(int, int)
		 */
		@Override
		public boolean isAttention(int uid,int to){
			int active = relationShipDAO.isActiveAttention(uid,to);
			int pass = relationShipDAO.isPassAttention(uid,to);
			if(active > 0 && pass >0){
				return true;
			}else{
				relationShipDAO.delActiveAttention(uid, to);
				relationShipDAO.delPassAttention(uid, to);
				return false;
			}
		}
		
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getHuFengUids(int)
		 */
		 @Override
		public List<Integer> getHuFengUids(int uid) {
			 List<Integer> attentionActiveList = getActiveAttentions(uid);
			 List<Integer> attentionPass = getPassAttentions(uid);
			 attentionActiveList.retainAll(attentionPass);
			 return attentionActiveList;
		    }
		 
		 /* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#getHuFengUids(int, int, int)
		 */
		@Override
		public List<Integer> getHuFengUids(int uid ,int offset ,int count) {
			 List<Integer> attentionActiveList = getActiveAttentions(uid);
			 List<Integer> resultList = new ArrayList<Integer>();
			 List<Integer> attentionPass = getPassAttentions(uid);
			 attentionActiveList.retainAll(attentionPass);
			 if(offset > attentionActiveList.size()){
				 return resultList;
			 }
			 for(int i =0 ;i< count;i++){
				 if((offset + i) >= attentionActiveList.size()){
					 break;
				 }
				 resultList.add(attentionActiveList.get(offset+i));
			 }
			 return resultList;
		    } 
		 
		/* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#isFans2EachOther(int, int)
		 */
		 @Override
		public boolean isFans2EachOther(int uid1, int uid2) {
			 return (isAttention(uid1, uid2)&&isAttention(uid2, uid1));
		    }
		 
	    /* (non-Javadoc)
		 * @see com.renren.ntc.video.service.RelationShipService#isFans(int, int)
		 */
	    @Override
		public boolean isFans(int uid, int target) {
	        return isAttention(uid, target);
	    }
}
