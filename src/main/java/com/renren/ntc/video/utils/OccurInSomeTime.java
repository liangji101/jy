package com.renren.ntc.video.utils;

/**
 * 所有在一段时间只发生一次的业务逻辑的枚举类
 */
public enum OccurInSomeTime {

	BAN_USER("ban", 30 * 24 * 60 * 60),
    PUSH("upload_push", 30);
		
	String name;

    private int expiredTime;
	
	public String getName() {
		return name;
	}

    /**
     *
     * @param name 业务名称
     * @param expiredTime 过期时间
     */
	private OccurInSomeTime(String name, int expiredTime) {
	        this .name = name;  
	}
}
