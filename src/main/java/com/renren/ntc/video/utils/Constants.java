package com.renren.ntc.video.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Constants {
	public static final int RAYDV_UID = 1802;
	
	//大开关，用于判断用户是否同步了第三方
	public static final int BASIC_THIRD_SYCTYPE = 3;
	
	public static final int DEFAULT_RR_SYCTYPE = 0x71;
	
	public static final int DEFAULT_SINA_SYCTYPE = 0x1c02;
	
	public static final int DEFAULT_PUSHTYPE = 31;
	
	public static final int SHOW_LIKE_MAX_SIZE = 6;
	
	public static final int FEED_UPLOAD_RR = 9603;
	
	public static final String DEFAULT_VIDEO_TITLE = "无标题";
	
	//对评论分页时没一页的最大记录数
	public static final int PAGE_MAX_SIZE = 8;
	
	//热门视频每次返回的视频个数
	public static final int HOT_VIDEO_SIZE = 10;
	
	public static final int NEWEST_VIDEO_SIZE = 10;
    
    public static final String WEB_PATH = System.getProperty("socialcam.root");

    public static final String WEB_CURRENT_USER = "currentUser";

    public static final String WEB_SINA_STATE = "webSina";

    public static final String WEB_SINA_STATE_DISPLAY = "webSinaDisplay";

    public static final String WEB_RENREN_STATE = "webRenRen";

    public static final String WEB_SYN_TYPE = "synType";

    public static final String WEB_SINA_WATCHER = "";

    public static final String WEB_RENREN_STATE_DISPLAY = "webRenRenDisplay";

    public static final String WEB_FIRST_VISIT = "webFirstVisit";

    public static final String CLASS_PATH_ROOT = WEB_PATH + File.separator + "WEB-INF" + File.separator + "classes";
	// 视频的状态，按位来啦
	// 状态1： 0
    public static final int VIDEO_STATUS_PENDING = 0x00000000;
    // 状态2： 1
    public static final int VIDEO_STATUS_OK = 0x00000001;
    // 状态3： 2
    public static final int VIDEO_STATUS_TRANSCODED = 0x00000002;
    // 状态4或5： 4
    private static final int VIDEO_STATUS_MODIFY =  0x00000004;
    // 可能存在状态4： 2 | 4 = 6
    public static final int VIDEO_STATUS_TRANSCODED_MODIFY = VIDEO_STATUS_MODIFY | VIDEO_STATUS_TRANSCODED;
    // 暂时未用： 1 | 4 = 5
    public static final int VIDEO_STATUS_OK_MODIFY = VIDEO_STATUS_MODIFY | VIDEO_STATUS_OK;
    
    //异步处理的时候用到的type
    public static final String SYC_LIKE = "syc_like";
    
    public static final String SYC_MARK = "syc_mark";
    
    public static final String SYC_COMMENT = "syc_comm";       
    
    public static final String SYC_UPLOAD = "syc_upload";

    public static final String DB_FEED = "ntc_feed";
    
    public static final String DB_SHARE = "ntc_user";
    
    public static final String DB_TOKEN = "ntc_token";
    
    public static final String WEB_HOST = "www.uume.com";
    // 
    public static final String MAIL_TEXT = "hi，我刚用iPhone上的 %s 拍了一段视频 ，速来围观！观看地址 ：http://%s/share/%s";
    
    public static final String SINA_SHARE_TEXT = "我分享了@%s 上的一个视频《%s》: http://%s/share/%s";
    
    public static final String SINA_COMMENT_TEXT = "我评论了@%s 上的一个视频《%s》: %s   http://%s/share/%s";
    
    public static final String SINA_BROWSE_TEXT = "我观看了@%s 上的一个视频《%s》: http://%s/share/%s";
    
    public static final String SINA_LIKE_TEXT = "我喜欢了@%s 上的一个视频《%s》:  http://%s/share/%s";
    
    public static final String SINA_UPLOAD_TEXT = "我用iPhone上的@%s 拍摄了一个视频《%s》:  http://%s/share/%s";
    
    public static final String MAIL_TITILE = "来 %s 看看我新拍的视频吧！";
    
    public static final String WEB_NAME = "光影DV";
    /**
	 * upload  from  web
	 */
	public static final int VIDEO_FROM_WEB = 0; 
	/**
	 * upload  from  wap
	 */
	public static final int VIDEO_FROM_WAP = 1; 
 
	/**
	 * FILE_NAME  from  wap
	 */
	public static final String FILE_NAME = "%s_%s_%s.%s";
	
	public static final String SC = "sc";
	
	public static final String RR = "rr";
	
	public static final String SINA = "sina";
	
	public static final String QQ = "qq";
	
	
    public static final String RR_PRE = "rr_";
	
	public static final String SINA_PRE = "sina_";
	
	public static final String QQ_PRE = "qq_";
	
	public static final String BAN = "ban";
    
	
    public static final int VIDEO_SEND_REQUEST_INFO_TIMES = 5;  //与上传服务器同步信息，和请求上传路径等信息，同步和请求成功前所能重复请求的次数
    
    public static final String VIDEO_DAO_SOURCES="rr_video";
    
    public static final String VIDEO_DOMAIN = "http://tv.renren.com";
    
    public static final String VIDEO_UPLOAD_DOMAIN = "http://upload.tv.renren.com";
    
    
    private static final String VIDEO_STATIC_DOMAIN = "http://s.xnimg.cn";

    public static final String ATTENTIONSPASS = "passattentions";
    
    public static final String ATTENTIONSACTIVE = "activeattentions";
    
	public static final String PENDINGATTENTION = "pendingattentions";
    
    public static final String CONNECTIONS = "connections";
    
    public static final String COMMENTS = "comments";
    
    public static final String CLIENT_VERSION = "client_version";
    
   
    public static final String COMMENT_INDEX = "comment_index";

    public static final String MSG_COLL_NAME = "messages";

    public static final String MY_MSG_COLL_NAME ="my_message";
    
    public static final int MSG_MINE = 0;
    
    public static final int MSG_FRIENDS = 1;

    public static final int MSG_THRESHOLD = 100;

    public static final String MSG_SIZE = "message-size";

    public static final String MSG_ARRAY_KEY = "messages";

    public static final String MID = "mid";

    public static final String MSG_TITLE = "title";
    
    public static final int MSG_TITLE_MAX_LENGTH = 5;

    public static final String MSG_CONTENT = "content";
    
    public static final String MSG_READ = "read";

    public static final String MSG_DES_UID = "des_uid";

    public static final String MSG_CREATE_AT = "create_at";

    public static final String MSG_SRC_UID = "src_uid";

    public static final String MSG_SRC_USER_NAME = "src_user_name";

    public static final String MSG_DES_USER_NAME = "des_user_name";

    public static final String MSG_SRC_HEAD_URL = "src_head_url";

    public static final String MSG_DES_HEAD_URL = "des_head_url";
    
    public static final int RESPONSE_INDENT = 4;

    public static final int MSG_IS_READ = 1;
    
    public static final int MSG_UN_READ = 0;
    
    public static final int MSG_PUSH_THREADS = 50;
    
    public static final String MSG_PUSH_PASSWORD = "renren";

    public static final boolean IS_PRODUCT = true;
    
    public static final String MSG_PUSH_CERT_ADDR = "classpath:apns-distribute-cert.p12";

    public static final int DEVICE_TOKEN_LENGTH = 64;

    public static final String DEVICE_TOKEN_KEY = "device_token";

    public static final String DEVICE_COLL_NAME = "devices";

    /**
     * 消息类型:1 关注，2 评论回复，3 喜欢，4 分享，5 标记，6 发布
     */
    public static final String MSG_TYPE = "type";

    public static final String MSG_ACTION = "action";
    
    public static final int NULL_USER_ID = 0;

    public static final int NULL_ENTITY_ID = 0;


    /**
     * 消息拓展,消息的其他信息存于此
     */
    public static final String MSG_VIDEO = "video";

    public static final String MSG_VIDEO_TITLE = "title";

    public static final String MSG_VIDEO_ID = "en_id";

    public static final String UID = "UID";

    public static final String NEWFEED = "NEWFED";

    public static final String MINIFEED = "MINIFEED";
    
    public static final String SSNEWFEED = "SSNEWFED";

    public static final String SSMINIFEED = "SSMINIFEED";

    public static final String HOST = "10.7.18.134";

    public static final String DBNAME = "minitime";

	public static final String APP_ID = "app_id";

	public static final String TOKEN = "token";

    public static final String CLIENT_MAC = "mac";
	
    public static final String UID_SPILITTER = ",";

    public static final String ATT_CYPHER = ":!:!:!:";

    public static interface ParamName {

        public static final String IP = "ip";

        public static final String OP = "op";

        public static final String USER_ID = "userId";

        public static final String ITEM_CODE = "itemCode";

        public static final String TITLE = "title";

        public static final String DESC = "description";

        public static final String TAGS = "tags";

        public static final String PIC_URL = "picUrl";

        /**
         * 视频播放总时间
         */
        public static final String TOTAL_TIME = "totalTime";

        public static final String CHANNEL_ID = "channelId";

        /**
         * 站外播放路径
         */
        public static final String OUTER_PALYER_URL = "outerPlayerUrl";   //
        
        
        public static final String VIDEO_SRC_URL = "srcUrl";

        /**
         * 站内播放路径
         */
        public static final String ITEM_URL = "itemUrl";      // 

        /**
         * 视频类型
         */
        public static final String MEDIA_TYPE = "mediaType";  //

        public static final String SECRET = "secret";

        public static final String TYPE = "type";

        public static final String DELETE_TYPE = "deleteType";

		/**
		 * 转码判断是否是视频，还是音频
		 */
		public static final String  IS_AUDIO = "audio";
		
		public static final String CODE = "code";
		
		public static final String RESULT = "result";

		public static final String MESSAGE = "msg";
		
		/**
		 * 提供3G调用用的
		 */
		public static final String MP4_ADDRESS = "mp4Address";

		public static final String VIEW_TIMES = "viewTimes";
    }
    

    public static interface ErrorCode {

    	int FAIL = -1;
    	
        int SUCCESS = 0;

        int AUTH_FAILED = 1;

        int PUB_FAILED = 2;

        int MOD_FAILED = 3;

        int DEL_FAILED = 4;

        int VIDEO_NOT_EXIST = 5;
        
        int VIDEO_IS_NULL = 6;
        
        int VIDEO_TITLE_NULL = 7;

        int VIDEO_TITLE_TOO_BIG = 8;

        int VIDEO_DESC_NULL = 9;

        int VIDEO_DESC_TOO_BIG = 10;
        
        int VIDEO_TAGS_NULL = 11;

        int VIDEO_TAGS_TOO_BIG = 12;

        int VIDEO_ALREADY_EXIST = 13;
        
        int USER_ID_INVALID = 14;
        
        int VIDEO_COMPLETE_FAILED = 15;
        
        int VIDEO_ID_GET_ERROR = 16;

        int INPUT_CODE_NULL = 17;

        int INPUT_CODE_INVALID = 18;
        
        int TOO_MANY_UPLOAD_REQUEST = 19;
        
        int SERVER_UNKNOW_EXCEPTION = 20;
        
        int FOR_SYNC_DELETE_VIDEO = 21;   // 系统内标识， 用来转码失败时删除

        int ERROR_UNKNOWN = 1000;   
        
        int OP_ERROR = 30;   

        int SAFE = SUCCESS; //安全

        
        int VALID_TITLE = 1002; //标题不合法

        int VALID_TAGS = 1003; //标签不合法

        int VALID_DESCRIPTION = 1004; //简介不合法

        int ITEM_CODE_NULL = 1005;
        
        int COMMENT_CREATE_FAIL = 1006;
        
        int COMMENT_GET_FAIL = 1007;
        
        int COMMENT_DEL_FAIL = 1008;
        
        int SHARE_GETJSON_FAIL = 1009;
        
        int ERROR_NO_TICKET = 1010;
        
        int COMMENT_NO_SAFE = 1011;
        
        int ITEM_ACTIVITY_NEGATIVE = 1012;
        
        int CHANNEL_ID_NOT_EXIST = 1013;
        
        int VIDEO_PARAMETERS_NULL = 1014;
        
        int COMMENT_REPEAT = 1015;
        
        int COMMENT_NO_POWER = 1016;
        
        int FILE_NOT_EXIST = 2000;

		int FILE_CAN_NOT_MOVE = 2001;

		int VIDEO_UPLOAD_SAVED_EVER = 3000;
		
		int VIDEO_TRANSCODE_ERROR = 3001;

		int VIDEO_UPLOAD_IS_NULL = 3002;

		int VIDEO_PUBLISH_ERROR = 4001;

		int USER_PASSWD_INVALID = 23;
		
		int USER_ALREADY_USED = 24;
		
		int PARMATERS_INVALID = 25;
		
		int APP_ID_INVALID = 26;
		
		int NEED_LONGIN = 28;
		
		int MISS_TOKRN = 29;
		
		int ACCESSTOKEN_INVALID= 31;
		
		int ACCOUNT_ALLREAY_BINDED= 32;
		
		int BINDDING_ERROR = 33;
		
		int USERNAME_DUPLICATED = 34;
		
		int PUBLISH_FAIL = 35;

		int MISS_BOUND = 36;
		
		int LIKE_ERROR=37;
		
        int MARK_ERROR=38;
        
        int Video_ERROR=39;
        
        int TOKEN_INVALID = 44;
        
        int DEVICE_TOKEN_INVALID = 45;
        
        int ILLAGEL_ATTRNTION = 46;
        
        int MISS_SHARE = 47;
        
        
        //增加两种ErrorCode
        int VIDEO_PRIVATE = 48;
        int VIDEO_FANS_ONLY = 49;
        

		int MISS_BINDING = 50;
		
		int ALLREADY_BINDING = 55;
		
		int NE_RELOGIN = 51;
		
		int RARE_LIMIT = 60;
		
		int EXT_ERROR = 123456;

		int ALLREADY_RR_BINDING = 56;

		int ALLREADY_SINA_BINDING = 57;
		
		int  PROHIBITED  = 70;

		int SHAREMISS = 78;
		
		int  HEAD_NAME_INVALID = 80;

		int SHAREFORBIDOWNER = 85;
		
		int SHAREFORBIDFRIENDS = 87;

		int PRIVACYCHANGE = 86;
		int MARK_NO_POWER = 88;
		int MARK_HUFENG = 89;
    }
    
    


    /**
     * 30分钟的过期时间
     */
    public final static int expiredTime = 1000 * 60 * 30;

    /**
     * sign在memcache中的key前缀
     */
    public final static String cacheKey = "ugc_video_sign_";
    
    /**
     * 视频浏览时增加浏览次数的KEY和过期时间
     */
    public final static String VIDEO_CACHE_KEY="ugc_video_viewtimes_";
    
    public final static int VIDEO_CACHE_TIME=1000 * 60 * 30;

    /**
     * appKey
     */
    public final static String appKey = "f0feeba967537e40";

    /**
     * appSecret
     */
    public final static String appSecret = "414a76f6c703f09d6e34fe3bf5c65c00";

    public static final String OK = "OK";

    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";

    public static final String API_OP_UPLOAD = "item.upload";

    public static final String API_OP_COMPLETE = "item.complete";

    public static final String API_OP_MODIFY = "item.modify";

    public static final String API_OP_DELETE = "item.delete";
    
    public static final String API_OP_TRANSCODED = "item.transcoded";

    public static final String API_OP_INFO = "item.info";

    public static final int SUCC = 0;

    public static final int ERR = 1;

    public static final String API_OP_PUB = "item.pub";

    public static final String MINI = "mini";
    public static final String NEW = "new";
    /**
     * 每页显示个数
     */
    public static final int ITEM_PER_PAGE = 10;
    


    public static final int DEL_TYPE_RENREN_USER = 1; //人人用户主动删除
    
    public static final int DEL_TYPE_RENREN_DUP = 2;//重复的视频上传，删除它
    
    public static final int DEL_TYPE_RENREN = 3; //人人审核删除,目前都认为他们传这个值，没有区分 05/16/2011 作了细分，参考DEL_TYPE_RENREN_TRANSCODE等
    
    public static final int DEL_TYPE_RENREN_TRANSCODE = 4; //转码失败，删除它
    
    public static final int DEL_TYPE_EXCEED_5_HOUR = 5; //时长太长啦，最长支持5小时

    public static final int DEL_TYPE_LESS_1_SECOND = 6; //时长太短啦，时长不能小于1秒

    public static final int DEL_TYPE_SIZE_TOO_SMALL = 7; //视频文件太小啦，源文件需要大于50K

    public static final int DEL_TYPE_TYPE_NOT_SUPPORT = 8; //目前暂不支持您上传的文件类型
    
    public static final int DEL_TYPE_NOT_VIDEO_FILE = 9; //不是视频文件 
    
    public static final int DEL_TYPE_USER = 1001;

    public static final int DEL_TYPE_RENREN_ADMIN = 1002;

    public static final int DEL_TYPE_RENREN_ADMIN_REPORT = 1006; //人人举报删除
    
    public static final int VIDEO_UPLOAD_INFO_NOT_VALID = 2001;
    
    public static final int VIDEO_MODIFY_INFO_NOT_VALID = 2002;
    

    public static Map<Integer, String> infoMap = new HashMap<Integer, String>();

	public static String FEEDTYPE = "feedType";

    

    //字符最大长度限制
    public static final int MAX_LENGTH_TITLE = 80;

    public static final int MAX_LENGTH_DESC = 500;

    public static final int MAX_LENGTH_TAGS = 100;
    
    public static final int MAX_COUNT_NUM = 10000;
    
    public static final int VIDEO_COUNT_LEFT = 3;
    
    public static final int VIDEO_COMMENT_COUNT=2;
    
    public static final String DEFAULT_TAGS="人人视频";
    
    public static final String DEFAULT_PHONE_TAGS="人人手机视频";
    
    public static final String DEFAULT_FROM_NAME="人人管理员";
    
    /**
     * 人人视频的终端页面的地址 
     */
    public static final String VIDEO_HOME_URL_MODEL= VIDEO_DOMAIN + "/video/%d/video-%d";
    /**
     * 人人用户个人主页 
     */
    public static final String USER_URL_MODEL="http://www.renren.com/profile.do?id=%d";
    
    /**
     * 人人视频的真正统一显示地址 
     */
    public static final String VIDEO_REAL_URL_MODEL = VIDEO_DOMAIN + "/v/%d/%s/v.swf";
    
    /**
     * 发webpager的代号
     */
    public static final int VIDEO_NOTIFY_COMMENT_CODE = 195;
    
    public static final int VIDEO_NOTIFY_REPLY_CODE = 199;
    
    public static final int VIDEO_NOTIFY_AUDIT_CODE = 200;
    
    public static final int VIDEO_NOTIFY_AT_CODE = 203;
    
    /**
     * 新版视频新鲜事的code
     */
    public static final int TV_FEED_SEND_CODE = 8211;
    
    /**
     * 发布新鲜事的code
     */
    public static final int VIDEO_FEED_SEND_CODE = TV_FEED_SEND_CODE; //旧版的新鲜事已经不能用了： 8210;
    
    /**
     * 评论同步到新鲜事的code
     */
    public static final int VIDEO_COMMENT_TO_FEED = TV_FEED_SEND_CODE;
    
    /**
     * 新鲜事获取评论的默认长度
     */
    public static final int VIDEO_COMMENT_FEED_DEFAULT_LENGTH=100;
  
    
    
    /**
     * 视频的长和宽
     */
    public static final int VIDEO_LENGTH = 458;
    
    public static final int VIDEO_WIDTH = 610;
    
    public static final int VIDEO_2_FEED_COMM_MAX_LENTGH = 100;
    /**
     * 视频分享的的type
     */
    public static final int VIDEO_SHARE_CODE = 10;
    
    /**key map 备用*/
    public static final Map<String, String> APP_KEY_MAP = new HashMap<String, String>();
    
    static {
        APP_KEY_MAP.put("cc862344a00efd7afb3ef9d7d88e8a1d", "236683791d58817ca0258a845baaabeb");
    }

    public static final int MESSAGE_ADMIN_ID = 223158817;






    /**
	 * 用户上传的是音频
	 */
	public static final int USER_UPLOAD_AUDIO = 1;
	
	/**
	 * 用于音频的统一图片展示，用于feed
	 */
	public static final String IMAGE_FOR_AUDIO_DISPLAY = VIDEO_STATIC_DOMAIN + "/apps/tv/css/cssimg/tv-m.jpg";







	public static final int TYPE_MD5 = 1;

	/**
	 * form 表单 上传 没有 md5
	 * 空文件 的md5值为 d41d8cd98f00b204e9800998ecf8427e
	 */
	public static final String NO_CHECKSUM = "d41d8cd98f00b204e9800998ecf8427e";
	
	/**
	 * 人人新鲜事的的那个source 图片
	 */
	public static final String RENREN_VIDEO_FEED_SOURCE_FLAG = "http://app.xnimg.cn/application/20110527/15/00/LGAds116a018153.gif";
		
		
	
	public static final String SC_RELATIONSHIP = "SC_RS";
	
	public static final String SIG = "sig";

	public static final String SHARE = "VIDEO";

	public static final String FEED = "FEED";

	public static final int PLAYURLOK = 1;

	public static final String CONTENT = "content";

	public static final int VIDOE = 1;

	public static final String LIKE = "like";

	public static final String MARK = "mark";
	public static final String WATCH = "watch";

	public static final int FEED_ALL = 3;
	
	public static final int FEED_ME = 1;
	
	public static final int FEED_FRIENDS = 2;

	public static final int NONE = 0;

	public static final String SYC_3RD_SHARE = "syc_share";
	
	public static final String SYC_3RD_COMMENT = "syc_comment";
	
	public static final String SYC_3RD_LIKE = "syc_like";

	public static final int SYC_RR = 1;
	public static final int SYC_SINA = 2;

	public static final String SYC_RR_SHARE = "syc_rrs";
	
	public static final String SYC_SINA_SHARE = "syc_sns";

	public static final String SYC_UNMARK = "syc_umark";

	


    
    static {
        infoMap.put(ErrorCode.VALID_TITLE, "发表失败，信息包含不合适内容，请检查");
        infoMap.put(ErrorCode.VALID_TAGS, "发表失败，信息包含不合适内容，请检查");
        infoMap.put(ErrorCode.VALID_DESCRIPTION, "发表失败，信息包含不合适内容，请检查");
        infoMap.put(ErrorCode.ITEM_CODE_NULL, "itemCode不能为空");
        infoMap.put(ErrorCode.COMMENT_CREATE_FAIL, "发布评论失败");
        infoMap.put(ErrorCode.COMMENT_NO_POWER, "该视频被设置为私密视频，评论失败");
        infoMap.put(ErrorCode.COMMENT_GET_FAIL, "获取评论失败");
        infoMap.put(ErrorCode.COMMENT_DEL_FAIL, "删除评论失败");
        infoMap.put(ErrorCode.COMMENT_REPEAT, "重复评论");
        infoMap.put(ErrorCode.SHARE_GETJSON_FAIL, "获取分享JSON失败");
        infoMap.put(ErrorCode.VIDEO_TITLE_NULL, "标题不能为空");
        infoMap.put(ErrorCode.VIDEO_DESC_NULL, "视频介绍不能为空");
        infoMap.put(ErrorCode.VIDEO_COMPLETE_FAILED, "视频信息同步发生系统错误，请联系客服人员");
        infoMap.put(ErrorCode.INPUT_CODE_NULL, "验证码不能为空");
        infoMap.put(ErrorCode.INPUT_CODE_INVALID, "验证码输入有误");
        infoMap.put(ErrorCode.AUTH_FAILED, "审核失败");
        infoMap.put(ErrorCode.PUB_FAILED, "发布失败");
        infoMap.put(ErrorCode.MOD_FAILED, "编辑失败");
        infoMap.put(ErrorCode.DEL_FAILED, "删除失败");
        infoMap.put(ErrorCode.VIDEO_NOT_EXIST, "该视频不存在");
        infoMap.put(ErrorCode.VIDEO_IS_NULL, "该视频不存在或为空");
        infoMap.put(ErrorCode.VIDEO_TITLE_NULL, "视频标题不能为空");        
        infoMap.put(ErrorCode.VIDEO_TITLE_TOO_BIG, "视频标题太长,最多不能超过40个字");
        infoMap.put(ErrorCode.VIDEO_DESC_NULL, "视频描述不能为空");
        infoMap.put(ErrorCode.VIDEO_DESC_TOO_BIG,"视频描述超出长度限制，最多不能超过"+MAX_LENGTH_DESC+"字" );
        infoMap.put(ErrorCode.VIDEO_TAGS_NULL, "视频标签不能为空");
        infoMap.put(ErrorCode.VIDEO_TAGS_TOO_BIG, "视频标签超过长度限制，最多不能超过"+MAX_LENGTH_TAGS+"字");
        infoMap.put(ErrorCode.VIDEO_ALREADY_EXIST, "此视频已存在");
        infoMap.put(ErrorCode.USER_ID_INVALID, "用户ID不合法");
        infoMap.put(ErrorCode.VIDEO_COMPLETE_FAILED, "视频上传失败");
        infoMap.put(ErrorCode.VIDEO_ID_GET_ERROR, "获取ID失败");
        infoMap.put(ErrorCode.INPUT_CODE_NULL, "验证码不能为空");
        infoMap.put(ErrorCode.INPUT_CODE_INVALID, "验证码不正确");
        infoMap.put(ErrorCode.TOO_MANY_UPLOAD_REQUEST, "上传请求次数过多，请稍候再试");
        infoMap.put(ErrorCode.USER_PASSWD_INVALID, "用户名或密码不正确");
        infoMap.put(ErrorCode.FOR_SYNC_DELETE_VIDEO, "【系统内标识】当转码失败时，这个用来同步删除 video 表记录");
        infoMap.put(ErrorCode.SERVER_UNKNOW_EXCEPTION, "服务器忙，请稍候重试");
        infoMap.put(ErrorCode.ERROR_NO_TICKET, "非法提交");
        infoMap.put(ErrorCode.COMMENT_NO_SAFE, "发布评论失败：你的评论不适合在本网站发布或操作过于频繁");
        infoMap.put(ErrorCode.ITEM_ACTIVITY_NEGATIVE, "线上活动类型不能为负");
        infoMap.put(ErrorCode.CHANNEL_ID_NOT_EXIST, "视频分类ID不合法");
        infoMap.put(ErrorCode.VIDEO_PARAMETERS_NULL, "参数不能不空");
        
        infoMap.put(ErrorCode.FILE_NOT_EXIST, "不存在文件");
        infoMap.put(ErrorCode.FILE_CAN_NOT_MOVE, "文件不能移动");
        
        infoMap.put(ErrorCode.VIDEO_UPLOAD_SAVED_EVER, "该视频已经保存过，请不要再保存");
        infoMap.put(ErrorCode.VIDEO_TRANSCODE_ERROR, "视频格式错，上传视频文件可能被破坏");
        infoMap.put(ErrorCode.VIDEO_UPLOAD_IS_NULL, "不存在上传视频请求记录，请稍后上传。如果再出现，请联系人人管理员");
        
        infoMap.put(ErrorCode.VIDEO_PUBLISH_ERROR, "视频审核发布错误");
        
        infoMap.put(ErrorCode.USER_ALREADY_USED, "您填写的邮箱已被注册，请换用其他邮箱重试");
        infoMap.put(ErrorCode.PARMATERS_INVALID, "非法输入参数 %s ");
        infoMap.put(ErrorCode.APP_ID_INVALID, "参数校验错误");
        infoMap.put(ErrorCode.SUCCESS, "done");
        infoMap.put(ErrorCode.NEED_LONGIN,"用户未登录");
        infoMap.put(ErrorCode.MISS_TOKRN,"缺少token 参数");
        infoMap.put(ErrorCode.OP_ERROR,"操作异常，请稍候再试");
        infoMap.put(ErrorCode.ACCESSTOKEN_INVALID,"状态异常，请重新登录");
        infoMap.put(ErrorCode.BINDDING_ERROR,"绑定失败请稍候再试");
        infoMap.put(ErrorCode.FAIL,"操作失败,请稍候再试");
        infoMap.put(ErrorCode.USERNAME_DUPLICATED,"邮箱已经被占用已经被占用。");
        infoMap.put(ErrorCode.PUBLISH_FAIL,"新鲜事发布失败");
        infoMap.put(ErrorCode.MISS_BOUND,"请先绑定第三方帐号");
        infoMap.put(ErrorCode.TOKEN_INVALID,"状态异常,请重新登录");
        infoMap.put(ErrorCode.DEVICE_TOKEN_INVALID, "无效的设备识别号，必须为64个ascii字符");
        infoMap.put(ErrorCode.ILLAGEL_ATTRNTION, "没有必要关注自己");
        infoMap.put(ErrorCode.MISS_SHARE, "没有权限或当前分享不存在");
        infoMap.put(ErrorCode.MISS_BINDING, "未绑定第三方帐号，请先绑定");
        infoMap.put(ErrorCode.MARK_ERROR, "圈人失败");
        infoMap.put(ErrorCode.ALLREADY_RR_BINDING, "此人人帐号已经绑定过，您可以直接通过该帐号登录");
        infoMap.put(ErrorCode.ALLREADY_SINA_BINDING, "此新浪帐号已经绑定过，您可以直接通过该帐号登录");
        infoMap.put(ErrorCode.ALLREADY_BINDING, "第三方帐号已经绑定过。请先解绑");
        infoMap.put(ErrorCode.NE_RELOGIN,"未绑定第三方帐号，请先绑定");
        infoMap.put(ErrorCode.RARE_LIMIT,"接口调用频率限制，请稍后再试");
        infoMap.put(ErrorCode.PROHIBITED,"抱歉，某些信息是不能发布的哦：）谢谢您的谅解");
        infoMap.put(ErrorCode.SHAREMISS,"该视频不存在或已被删除");
        infoMap.put(ErrorCode.HEAD_NAME_INVALID,"头像或昵称格式错误");
        infoMap.put(ErrorCode.SHAREFORBIDOWNER,"该视频被设置为私密视频，您无权执行操作");
        infoMap.put(ErrorCode.SHAREFORBIDFRIENDS, "该视频被设置为仅好友可见，您无权执行操作");
        infoMap.put(ErrorCode.VIDEO_PRIVATE, "该视频被设置为私密视频,您无权执行操作");
        infoMap.put(ErrorCode.VIDEO_FANS_ONLY, "该视频被设置为仅好友可见,您无权执行操作");
        infoMap.put(ErrorCode.MARK_NO_POWER, "该视频被设置为私密视频，无法圈人");
        infoMap.put(ErrorCode.MARK_HUFENG, "该视频只能圈出互粉的好友"); 
    }
	public static final int MESSAGE_EXPIRY_TIME = 1000 * 60;//message缓存信息失效时间,一分钟
	public final static int COMMENT_EXPIRE_TIME = 1000 * 60;//一分钟过期
	public static final String[] MEMCACHE_SERVER = {"10.32.16.90:11211"};//装memcache的机器列表

	public final static long APPSTORE1 = 8112300; 
	
	public static final int BROWSECACHE = 30 * 60;
	
	public static final String TITLE = "title";

	public static final String SYC_BROWSE = "syc_browse";

	public static final int SYC_PENDING = 3;
	
	public static final int SYC_RRPENDING = 4;

    public static final int SYC_WEB_SINA_BLOCK = 5;

	public static final int EDIT_VIDEO_RECORD_NUMBER_PER_PAGE = 10;//后台编辑视频页面一页最多显示的记录数
	public static final int EDIT_VIDEO_BAR_TERM_NUMBER = 10;//后台编辑视频的分页条的显示多少个阿拉佰数字让用户点

	public static final String SYC_SHSRE_DEL = "syc_s_del";
	
	public static final String collaborator = "renren_mobile_ext1";

	public static final String EVENT = "event";

	public static final String SHOW56 = "show56";

	public static final String GODSHOW = "godShow";

	public static final String VARIOUSSHOW = "variousShow";

	public static final String MYSELFSHOW = "myselfShow";
	
    public static String getMessage(int code){
    	String message = infoMap.get(code);
    	if(null == message){
    		message = "";
    	}
    	return message;
    }
    
    public static final String VALIDATECODE = "validateCode";
    
    public static void main(String[] args) {
        String template = "hello %s";
        System.out.println(String.format(template, "world"));
    }
    
}
