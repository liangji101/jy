package com.renren.ntc.video.utils;

/**
 * User: 帅伟良
 * Mail: weiliang.shuai@renren-inc.com
 * Date: 12-10-26
 * Time: 下午8:39
 */
public class Reverser {
    private int offset;

    private int count;

    /**
     * 取反转之后的offset和count
     * @param offset
     * @param count
     */
    public Reverser(int offset, int count) {
        this.offset = offset;
        this.count = count;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }
}
