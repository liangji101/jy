package com.renren.ntc.video.utils;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

public class ApiSignatureUtil {
	private static ApiSignatureUtil instance=new ApiSignatureUtil();
	
	public static ApiSignatureUtil getInstance(){
		return instance;
	}
	
	private ApiSignatureUtil(){
	}
	
	public  boolean verifySignature(List<String> sigParams,String secret, String expected) {
		if (null == expected || "".equals(expected))
			return false;
		String signature = generateSignature(sigParams, secret);
		return expected.equals(signature);
	}

	/**
	 * 生成sig
	 * @param params
	 * @param secret
	 * @return
	 */
	public  String generateSignature(List<String> params, String secret) {
		String calculatingString = constructeSigCalculatingString(params, secret);
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			StringBuffer result = new StringBuffer();
			try {
				for (byte b : md.digest(calculatingString.getBytes("UTF-8"))) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			} catch (UnsupportedEncodingException e) {
				for (byte b : md.digest(calculatingString.getBytes())) {
					result.append(Integer.toHexString((b & 0xf0) >>> 4));
					result.append(Integer.toHexString(b & 0x0f));
				}
			}
			return result.toString();
		} catch (java.security.NoSuchAlgorithmException ex) {
			System.err.println("MD5 does not appear to be supported" + ex);
			return "";
		}
	}

    public String constructeSigCalculatingString(List<String> params, String secret) {
        StringBuffer buffer = new StringBuffer();
    	Collections.sort(params);
		for (String param : params) {
			buffer.append(param);
		}

		buffer.append(secret);
        return buffer.toString();
    }
}
