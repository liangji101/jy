package com.renren.ntc.video.constants;

import org.apache.log4j.Logger;


/**
 * User: 帅伟良
 * Mail: weiliang.shuai@renren-inc.com
 * Date: 12-10-10
 * Time: 下午6:21
 */
public enum VideoPrivacy {
    //公开视频，所有人都能看
    PUBLIC,
    //粉丝能观看的视频
    FANS,
    //私有视频，仅自己能观看
    PRIVATE;

    private static final Logger logger = Logger.getLogger(VideoPrivacy.class);

    public static final VideoPrivacy fromInt(int privacy) {
        if(privacy < 0 || privacy >= VideoPrivacy.values().length) {
            logger.error("错误的参数" + privacy);
            return PRIVATE;
        } else {
            return VideoPrivacy.values()[privacy];
        }
    }
}
