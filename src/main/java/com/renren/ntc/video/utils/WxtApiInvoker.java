package com.renren.ntc.video.utils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.renren.ntc.video.utils.renren.utils.Md5Utils;
import com.renren.ntc.video.utils.sina.weibo4j.http.Response;

/**
 * 
 * @author Administrator
 * 
 */
public final class WxtApiInvoker {

	private static String user = "6653626126";
	private static String partner = "20131211154443";
	private static String appKey = "b908dafe499347f89a5d5fb468dcacc7";
	private org.apache.commons.httpclient.HttpClient client;

	private static WxtApiInvoker instance = new WxtApiInvoker();
	
	public WxtApiInvoker() {
		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = connectionManager.getParams();
		params.setDefaultMaxConnectionsPerHost(20);
		params.setConnectionTimeout(5000);
		params.setSoTimeout(2000);

		HttpClientParams clientParams = new HttpClientParams();
		clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		client = new org.apache.commons.httpclient.HttpClient(clientParams, connectionManager);
	}

	
	public static WxtApiInvoker getInstance(){
		return instance;
	}
	public String sendPostRestRequest(Map<String, String> params, String url) {
		System.out.println(url +  "?" + sig(params));
		sigParams(params);
		Response content = null;
		try {
			content = post(url, params);
			return content.getResponseAsString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private Map<String, String> sigParams(Map<String, String> params) {

		List ls = new ArrayList<String>();
		for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			StringBuffer sb = new StringBuffer();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			ls.add(sb.toString());
		}

		String key = constructeSigCalculatingString(ls, appKey);
		params.put("sign", Md5Utils.md5(key));
		return params;
	}
	
	public String sig(Map<String, String> params) {

		List ls = new ArrayList<String>();
		StringBuffer pas = new StringBuffer();
		for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			StringBuffer sb = new StringBuffer();
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			ls.add(sb.toString());
			pas.append(sb);
			pas.append("&");
		}
		String key = constructeSigCalculatingString(ls, appKey);
		pas.append("sign=" + Md5Utils.md5(key));
		return pas.toString();
	}



	public Response post(String url, Map<String, String> params) throws Exception {
		PostMethod postMethod = new PostMethod(url);
		Set<String> keys = params.keySet();
		Iterator<String> kk = keys.iterator();
		while (kk.hasNext()) {
			String k = kk.next();
			postMethod.addParameter(k, params.get(k));
		}
		HttpMethodParams param = postMethod.getParams();
		param.setContentCharset("UTF-8");
		return httpRequest(postMethod);
	}

	public Response httpRequest(HttpMethod method) throws Exception {
		InetAddress ipaddr;
		int responseCode = -1;
		try {
			ipaddr = InetAddress.getLocalHost();
			List<Header> headers = new ArrayList<Header>();

			method.getParams()
					.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			client.executeMethod(method);
			Header[] resHeader = method.getResponseHeaders();
			responseCode = method.getStatusCode();

			Response response = new Response();
			response.setResponseAsString(method.getResponseBodyAsString());
            System.out.println(String.format("Request [%s] response [%s]",method.getURI(), response.getResponseAsString()));
			if (responseCode != 200)
			{
				throw new Exception();
			}
			return response;

		} catch (Exception ioe) {
			throw new Exception(ioe.getMessage());
		} finally {
			method.releaseConnection();
		}

	}

	public String constructeSigCalculatingString(List<String> params, String secret) {
		StringBuffer buffer = new StringBuffer();
		Collections.sort(params);
		for (String param : params) {
			buffer.append(param);
			buffer.append("&");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append(secret);
		return buffer.toString();
	}
   
	public static void main(String[] args) {
		WxtApiInvoker jy = new WxtApiInvoker();
		Map<String, String> params = new HashMap<String, String>();

		params.put("partner", partner);
		params.put("timestamp", System.currentTimeMillis() + "");
		params.put("uniqueUserId", "1116667885");
		params.put("uniqueCourseId", "2332731168");
		String response = jy.sig(params);
		System.out.println("response : " + response);

	}
}
