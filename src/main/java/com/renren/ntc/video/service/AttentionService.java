package com.renren.ntc.video.service;

import com.renren.ntc.video.constants.AttentionStatus;

/**
 */
public interface AttentionService {
    /**
     * 关注用户逻辑
     * @param uid
     * @param target
     * @return
     */
    AttentionStatus attention(int uid, int target);

    AttentionStatus hotUserAttention(int uid, int target);
}
