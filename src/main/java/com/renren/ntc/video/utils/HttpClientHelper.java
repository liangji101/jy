package com.renren.ntc.video.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Chris Gao
 * @version Create Time：2011-4-11 下午05:42:31
 * @desc 具备可复用连接的HttpClient
 */
public class HttpClientHelper extends HttpClient {

    static Log logger = LogFactory.getLog(HttpClientHelper.class);

    static class SingletonHolder {

        static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

        static HttpClientHelper instance = new HttpClientHelper();

        static {
            instance.setHttpConnectionManager(connectionManager);
            instance.getHttpConnectionManager().getParams().setDefaultMaxConnectionsPerHost(100);
            instance.getHttpConnectionManager().getParams().setMaxTotalConnections(100);
        }
    }

    public static HttpClientHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 发起请求 Get方式
     * 
     * @param url
     * @param parameters
     * @return
     */
    public String sendRequestGet(String url, Map<String, String> parameters) {
        long dt = System.currentTimeMillis();
        if (logger.isDebugEnabled()) {
            logger.debug("sendRequestGet with: [" + url + "]");
        }
       
        String params = null;
        if (parameters != null) {
            for (Entry<String, String> e : parameters.entrySet()) {
                params += e.getKey() +"="+ e.getValue();
                params += "&";
            }
        }
       
        GetMethod get = new GetMethod(url);
        if (logger.isDebugEnabled()) {
            logger.debug("sendRequestGet with: [" + url + "]");
        }
       
        try {
        	int status =  getInstance().executeMethod(get);
        	logger.info(String.format("status is :%d",status));
            return new String(get.getResponseBody());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            get.releaseConnection();
            if (logger.isDebugEnabled()) {
                logger.debug("sendRequestGet with: [" + url + "] cost time:" + (System.currentTimeMillis() - dt) + "ms");
            }
        }
    }

    /**
     * 发起Http请求 Post方式
     * 
     * @param url
     * @param parameters
     * @return
     */
    public String sendRequestPost(String url, Map<String, String> parameters) {
        long dt = System.currentTimeMillis();
        if (logger.isInfoEnabled()) {
            logger.info("sendRequestPost with: [" + url + "]");
        }
        PostMethod post = new PostMethod(url);
        if (parameters != null) {
            for (Entry<String, String> e : parameters.entrySet()) {
                post.setParameter(e.getKey(), e.getValue());
            }
        }
        try {
            // 设置Post的编码为utf-8
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            HttpConnectionManagerParams managerParams = getInstance().getHttpConnectionManager().getParams();
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(5000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(5000);
            getInstance().executeMethod(post);
            return new String(post.getResponseBody());
        }catch (HttpException e) {
        	logger.error("sendRequestPost with: [" + url + "] HttpException message :" + e.getLocalizedMessage());
            return null;
        } 
        catch (IOException e) {
        	logger.error("sendRequestPost with: [" + url + "] IOException message :" + e.getLocalizedMessage());
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } finally {
            post.releaseConnection();
            if (logger.isDebugEnabled()) {
                logger.debug("sendRequestPost with: [" + url + "] cost time:" + (System.currentTimeMillis() - dt) + "ms");
            }
        }
    }
}
