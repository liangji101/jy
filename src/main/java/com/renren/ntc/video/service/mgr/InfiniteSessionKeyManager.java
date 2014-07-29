package com.renren.ntc.video.service.mgr;

import com.renren.ntc.video.biz.home.InfiniteSkHome;
import com.renren.ntc.video.biz.model.AccessToken;
import com.renren.ntc.video.biz.model.InfiniteSk;
import com.renren.ntc.video.biz.model.SessionKeyInfo;
import com.renren.ntc.video.biz.model.SessionKeyValidationResult;
import com.renren.ntc.video.utils.SKUtil;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class InfiniteSessionKeyManager {
	
    private static ApplicationContext applicationContext;
	
    private static InfiniteSessionKeyManager instance ;

    @Autowired
	public void setApplicationContext(ApplicationContext ac) {
    	InfiniteSessionKeyManager.applicationContext = ac;
	}

	public static InfiniteSessionKeyManager getInstance() {
		if (instance == null) {
			instance =  (InfiniteSessionKeyManager) BeanFactoryUtils.beanOfType(applicationContext, InfiniteSessionKeyManager.class);
		}
		return instance;
	}
	
	private InfiniteSessionKeyManager() {
	}

	public AccessToken getInfiniteSessionKey(int userId, int appId) {
		InfiniteSk inSk = InfiniteSkHome.getInstance().get(userId, appId);
		if (inSk == null) {
			inSk = generateInfiniteSessionKey(userId, appId);
		}
		AccessToken sk = new AccessToken();
		sk.setExpiresTime(-1);
		sk.setSessionKey(inSk.getSessionKey());
		sk.setSessionSecret(inSk.getSessionSecret());
		return sk;
	}

	private InfiniteSk generateInfiniteSessionKey(int userId, int appId) {
		String sessionKey = SKUtil.getUUID() + '-' + userId;
		String sessionSecret = SKUtil.getUUID();
		InfiniteSk inSk = new InfiniteSk();
		inSk.setAppId(appId);
		inSk.setUserId(userId);
		inSk.setSessionKey(sessionKey);
		inSk.setSessionSecret(sessionSecret);
		InfiniteSkHome.getInstance().generateKey(inSk);
		return inSk;
	}

	public SessionKeyValidationResult validateInfiniteSessionKey(int appId, String sessionKey, boolean needSessionSecret) {
		SessionKeyValidationResult validationResult = new SessionKeyValidationResult();
		SessionKeyInfo skInfo = SKUtil.parseInfiniteSessionKey(sessionKey);
		if (skInfo == null) {
			validationResult.setValidationCode(SessionKeyValidationResult.SK_VALIDATION_INVALID);
			return validationResult;
		}

		InfiniteSk inSk = InfiniteSkHome.getInstance().get(skInfo.getUid(), appId);
		int validationCode = SessionKeyValidationResult.SK_VALIDATION_INVALID;
		if (inSk != null && inSk.getSessionKey().equals(sessionKey)) {
			validationCode = SessionKeyValidationResult.SK_VALIDATION_OK;
		}
		validationResult.setValidationCode(validationCode);
		if (validationCode == SessionKeyValidationResult.SK_VALIDATION_OK) {
			validationResult.setUserId(skInfo.getUid());
			if (needSessionSecret) {
				validationResult.setSessionSecret(inSk.getSessionSecret());
			}
		}
		return validationResult;
	}
	

	public boolean expireInfiniteSessionKey(int appId, String sessionKey) {
		SessionKeyInfo skInfo = SKUtil.parseInfiniteSessionKey(sessionKey);
		if (skInfo == null) {
			return false;
		}
		InfiniteSkHome.getInstance().delete(appId, skInfo.getUid());
		return true;
	}
	
	/**
	 * 直接删除用户的持久化session_key
	 * @param appId
	 * @param userId
	 * @return
	 */
	public boolean expireInfiniteSessionKey(int appId, int userId) {
		InfiniteSkHome.getInstance().delete(appId, userId);
		return true;
	}

}
