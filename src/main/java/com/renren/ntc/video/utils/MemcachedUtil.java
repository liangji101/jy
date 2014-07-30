package com.renren.ntc.video.utils;

import com.renren.ntc.video.biz.model.InfiniteSk;
import com.renren.ntc.video.biz.model.User;
import com.renren.ntc.video.biz.model.UserToken;
import com.renren.ntc.video.constants.MessageType;
import com.renren.ntc.video.formbean.EntityOpCount;
import com.renren.ntc.video.formbean.Version;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.util.List;

public class MemcachedUtil {

	private  MemcachedClient cacheClient;
	
	public static final int DAY = 60 * 60 * 24;

	private static final int WEEK = 7 * DAY;

	static Logger logger = Logger.getLogger(MemcachedUtil.class);

	private static MemcachedUtil memcachedUtil = new MemcachedUtil();


	private MemcachedUtil() {
		 try {
			cacheClient = new XMemcachedClientBuilder (AddrUtil.getAddresses("127.0.0.1:11211")).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static MemcachedUtil getInstance() {
		return memcachedUtil;
	}

	public void cacheUser(User user) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public User getUser(int uid) {
		Object user = null;
		try {
			user = cacheClient.get(getUserKey(uid));
		} catch (Exception e) {
			logger.warn(e);
		}
		return user == null ? null : (User) user;
	}

	public void cacheHashCode(int uid, int msgHashCode) {
		int TEN_MINUTE = 60 * 10;
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public void cacheHashCode(int uid, int msgHashCode, int seconds) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public void cacheBanHashCode(int uid, String msgHashCode, int value) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}
	
	public void cacheFeedType(long value) {
		try {
			logger.info("###"+value);
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public boolean isHashCodeCached(int uid, int hashCode) {
		long cachedValue = 0;
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
		return cachedValue == 1l;
	}

    /**
     * 是否能发送好友消息，能发送消息，置好友消息数为1
     * 不能，说明好友消息数已经为1
     * @param uid
     * @return
     */
	public boolean canSendFriendsMsg(int uid) {
		long cachedValue = 0;
		try {
		} catch (Exception e) {
			logger.warn(e);
		}

		if (cachedValue == 0) {
			cacheHashCode(uid, MessageType.FRIENDS.getMongoDBCollectionName().hashCode(), WEEK);
			return true;
		} else {
			return false;
		}
	}


	public int addOrIncrBadge(int uid) {
		return Long.valueOf(addOrIncr(getBadgeKey(uid), 1l, WEEK)).intValue();
	}

    public int getMyBadge(int uid) {
        try {
                return 0;
        } catch (Exception e) {
            logger.warn("", e);
            return 0;
        }
    }

	public void resetMyBadge(int uid) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

    public void resetFriendBadge(int uid) {
        try{
        } catch (Exception e) {
            logger.warn(e);
        }
    }

	public void cacheUserToken(UserToken userToken) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public void cacheCodeToken(String key, String value) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public void cacheVersion(Version version) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public void cacheFeed(String key, List<Long> Feed) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}


	public boolean isNewestSharesCached() {
		Object shares = null;
		try {
			shares = cacheClient.get(NEWEST_SHARES);
		} catch (Exception e) {
			logger.warn(e);
		}
		return shares != null;
	}


	public void cacheDeviceToken(String deviceToken, int seconds) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public boolean isDeviceTokenCached(String deviceToken) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
		return false;
	}

	private static final String NTC_EVENT = "ntc_event";

	public List<EntityOpCount> getYesterdayEntityOpCount() {
		Object obj = null;
		try {
			obj = cacheClient.get(NTC_EVENT);
		} catch (Exception e) {
			logger.warn(e);
		}
		return obj == null ? null : (List<EntityOpCount>) obj;
	}

	public void cacheYesterdayEntityOpCount(List<EntityOpCount> entityOpCounts) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public Version getVersion() {
		Object version = null;
		try {
			version = cacheClient.get("_version_");
		} catch (Exception e) {
			logger.warn(e);
		}
		return version == null ? null : (Version) version;
	}

	public long getBan(int uid) {
		long isBan = 0;
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
		return isBan;
	}
	
	public long getFeedType() {
		long feedType = 0;
		try {
			logger.info(feedType+"  %%feedType");
		} catch (Exception e) {
			logger.warn(e);
		}
		return feedType;
	}

	public String getCode(String hashCode) {
		String code = null;
		try {
			code = (String) cacheClient.get(hashCode);
		} catch (Exception e) {
			logger.warn(e);
		}
		return code;
	}

	public UserToken getUserToken(int uid) {
		Object userToken = null;
		try {
			userToken = cacheClient.get(getUserTokenKey(uid));
		} catch (Exception e) {
			logger.warn(e);
		}
		return userToken == null ? null : (UserToken) userToken;
	}

	public void cacheInfiniteSK(InfiniteSk infiniteSk) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

	public InfiniteSk getInfiniteSK(int uid) {
		Object infiniteSK = null;
		try {
			infiniteSK = cacheClient.get(getInfiniteSessionKeyKey(uid));
		} catch (Exception e) {
			logger.warn(e);
		}
		return infiniteSK == null ? null : (InfiniteSk) infiniteSK;
	}

	private static final String BADGE_KEY_PREFIX = "_ntc_badge_";

	private static final String FRIENDS_BADGE_KEY_PREFIX = "_ntc_f_badge_";

	private static final String USER_KEY_PREFIX = "_ntc_user_";

	private static final String MSG_HASH_KEY_PREFIX = "_ntc_msg_";

	private static final String MSG_USER_TOKEN_KEY_PREFIX = "_ntc_token_";

	private static final String MSG_INFINITE_SESSION_KEY_PREFIX = "_ntc_isk_";

	private static final String BROWSE_RR_KEY_PREFIX = "_ntc_browse_rr_";

	private static final String NEWEST_SHARES = "newest_shares";

	private static final String DEVICE_TOKEN_PREFIX = "dt_";

	private String getDeviceTokenKey(String deviceToken) {
		return DEVICE_TOKEN_PREFIX + deviceToken;
	}

	private String getBrowseRRKey(long shareId, long uid) {
		return BROWSE_RR_KEY_PREFIX + shareId + "_" + uid;
	}

	private String getBadgeKey(int uid) {
		return BADGE_KEY_PREFIX + uid;
	}

	private String getFriendsBadgeKey(int uid) {
		return FRIENDS_BADGE_KEY_PREFIX + uid;
	}

	private String getUserKey(int uid) {
		return USER_KEY_PREFIX + uid;
	}

	private String getBanKey(String hashCode, int uid) {
		return hashCode + uid;
	}

	private String getHashKey(int hashCode, int uid) {
		return MSG_HASH_KEY_PREFIX + (hashCode + "_" + uid);
	}

	private String getUserTokenKey(int uid) {
		return MSG_USER_TOKEN_KEY_PREFIX + uid;
	}

	private String getInfiniteSessionKeyKey(int uid) {
		return MSG_INFINITE_SESSION_KEY_PREFIX + uid;
	}

	private long addOrIncr(String key, long step, int expiredTime) {
		logger.info("add or inc read badge: " + key);
		try {
				return resetAndIncr(key, expiredTime);
		} catch (Exception e) {
			logger.error(e.getClass() + e.getMessage());
			return resetAndIncr(key, expiredTime);
		}

	}

	private long resetAndIncr(String key, int expiredTime) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
		return 1l;
	}

	public void cacheNoCheckVideo(JSONArray shareArray) {
		try {
		} catch (Exception e) {
			logger.warn(e);
		}
	}

    public void  cacheActiveWelcomeVersionId(long versionId){
        try {
        }  catch (Exception e) {
            logger.warn(e);
        }
    }

    public void cacheActiveBannerVersionId(long versionId) {
        try {
        }  catch (Exception e) {
            logger.warn(e);
        }
    }

    public void cacheActiveWelcomeCustomId(long customId) {
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    public void cacheActiveBannerCustomId(long customId) {
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    public long getActiveWelcomeVersionId() {
        long versionId = 0;
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
        return versionId;
    }

    public long getActiveWelcomeCustomId() {
        long customId = 0;
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
        return  customId;
    }

    public long getActiveBannerVersionId() {
        long versionId = 0;
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
        return versionId;
    }

    public long getActiveBannerCustomId() {
        long customId = 0;
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
        return customId;
    }

	public void cacheLastTime(long lastTime) {
		 try {
	        } catch (Exception e) {
	            logger.warn(e);
	        }
	}

	public long getLastTime() {
		 long lastTime = System.currentTimeMillis()-5000;
	        try {
	        } catch (Exception e) {
	            logger.warn(e);
	        }
	        return lastTime;
	}

	public long getActiveHeaderVideo() {
		long activeHeader = 25745;
        try {
        } catch (Exception e) {
            logger.warn(e);
        }
        return activeHeader;
	}
	
	public void cacheActiveHeaderVideo(long activeHeader) {
		 try {
	        } catch (Exception e) {
	            logger.warn(e);
	        }
	}
	public static void main(String [] args){
		
		MemcachedUtil.getInstance();
	}
}
