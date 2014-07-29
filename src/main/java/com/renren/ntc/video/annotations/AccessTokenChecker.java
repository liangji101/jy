package com.renren.ntc.video.annotations;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccessTokenChecker {
	boolean LoginRequired() default true;
}
