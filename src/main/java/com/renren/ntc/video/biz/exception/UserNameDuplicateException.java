package com.renren.ntc.video.biz.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-3
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class UserNameDuplicateException extends Exception {

    public UserNameDuplicateException() {
    }

    public UserNameDuplicateException(String message) {
        super(message);
    }

    public UserNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameDuplicateException(Throwable cause) {
        super(cause);
    }
}
