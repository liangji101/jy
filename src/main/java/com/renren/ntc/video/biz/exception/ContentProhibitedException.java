package com.renren.ntc.video.biz.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-3
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
public class ContentProhibitedException extends RuntimeException {

    public ContentProhibitedException() {
    }

    public ContentProhibitedException(String message) {
        super(message);
    }

    public ContentProhibitedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentProhibitedException(Throwable cause) {
        super(cause);
    }
}
