package com.renren.ntc.video.constants;

import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;

/**
 * Author: Weiliang Shuai
 * Contact: weiliang.shuai@renren-inc.com
 * Date: 12-8-13
 * Time: 上午11:10
 */
public enum AttentionStatus {
    NONE(JsonResponse.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION)),
    FOCUSED(JsonResponse.formSuccessResponse()),
    WAITING("{\"op\": \"wait\"}");

    private String iosResponse;

    private AttentionStatus(String iosResponse) {
        this.iosResponse = iosResponse;
    }

    public String getIosResponse() {
        return iosResponse;
    }
}
