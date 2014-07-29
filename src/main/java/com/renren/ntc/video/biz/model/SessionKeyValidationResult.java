package com.renren.ntc.video.biz.model;

/**
 * @author Zhang Tielei
 * 代表Session Key校验结果的类，顺带包含一个Session Secret
 */
public class SessionKeyValidationResult {

	/**
	 * session key 验证通过
	 */
	public static final int SK_VALIDATION_OK = 1;
	/**
	 * 无效的session key
	 */
	public static final int SK_VALIDATION_INVALID = 2;
	/**
	 * session key已过期 
	 */
	public static final int SK_VALIDATION_EXPIRED = 3;
	
	/**
	 * validationCode: session key验证结果码，可选值有：
	 * SK_VALIDATION_OK, 
	 * SK_VALIDATION_INVALID,
	 * SK_VALIDATION_EXPIRED
	 */
	private int validationCode;
	private String sessionSecret;
	private int userId;
	
	public String getSessionSecret() {
		return sessionSecret;
	}
	public void setSessionSecret(String sessionSecret) {
		this.sessionSecret = sessionSecret;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getValidationCode() {
		return validationCode;
	}
	public void setValidationCode(int validationCode) {
		this.validationCode = validationCode;
	}
}
