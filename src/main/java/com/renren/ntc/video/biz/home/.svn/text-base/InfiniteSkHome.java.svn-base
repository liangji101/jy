package com.renren.ntc.video.biz.home;


import com.renren.ntc.video.biz.dao.InfiniteSkDAO;
import com.renren.ntc.video.biz.model.InfiniteSk;
import com.renren.ntc.video.utils.MemcachedUtil;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InfiniteSkHome {

	private static ApplicationContext applicationContext;
	
    private static InfiniteSkHome instance ;
    
    @Autowired
    private InfiniteSkDAO infiniteDAO ;
    
	@Autowired
	public void setApplicationContext(ApplicationContext ac) {
		InfiniteSkHome.applicationContext = ac;
		}

		public static InfiniteSkHome getInstance() {
			if (instance == null) {
				instance =  (InfiniteSkHome) BeanFactoryUtils.beanOfType(applicationContext, InfiniteSkHome.class);
			}
			return instance;
		}
	
	public InfiniteSk get(int userId, int appId) {
        InfiniteSk infiniteSk = MemcachedUtil.getInstance().getInfiniteSK(userId);
        if(infiniteSk != null) {
            return infiniteSk;
        } else {
            infiniteSk = infiniteDAO.get(userId, appId);

            if(infiniteSk != null) {
                MemcachedUtil.getInstance().cacheInfiniteSK(infiniteSk);
            }
            return infiniteSk;
        }
	}
	
	public void generateKey(InfiniteSk inSK) {
	    infiniteDAO.generateKeys(inSK);
	}
	
	public void delete(int appId,int userId){
			infiniteDAO.delete(userId, appId);
	}
}
