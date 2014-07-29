package com.renren.ntc.video.controllers.api;

import com.renren.ntc.video.utils.Constants;
import com.renren.ntc.video.utils.JsonResponse;
import net.paoding.rose.web.ControllerErrorHandler;
import net.paoding.rose.web.Invocation;
import org.springframework.web.util.WebUtils;

public class ValidationExceptionHandler implements ControllerErrorHandler {
	
	public Object onError(Invocation inv, Throwable ex) throws Throwable {
		 ex.printStackTrace();
		 inv.getRequest().removeAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
		 inv.getResponse().setHeader("Content-Type", "text/json; charset=UTF-8");
         
         return "@" + JsonResponse.formFailResponse(Constants.ErrorCode.SERVER_UNKNOW_EXCEPTION);
		 
	}

}
