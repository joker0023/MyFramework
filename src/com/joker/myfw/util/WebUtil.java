package com.joker.myfw.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class WebUtil {

	private static final String DEFAULT_CHARSET = "UTF-8";
	private static int connectTimeout = 15000;	//15秒
	private static int readTimeout = 15000;	//15秒
	
	public static String doPost(String url, Map<String, Object> params, Map<String, String> headerMap) throws Exception{
		String resp = null;
	
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		RequestConfig.Builder reqConfigBuilder = RequestConfig.custom();
		reqConfigBuilder.setConnectTimeout(connectTimeout);
		reqConfigBuilder.setSocketTimeout(readTimeout);
		reqConfigBuilder.setExpectContinueEnabled(false);
		
		HttpPost post = new HttpPost(url);
		post.setConfig(reqConfigBuilder.build());
		
		String ctype = "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARSET;
		//post.addHeader("Accept", "text/xml,text/javascript,text/html,text/json");
    	//post.addHeader("User-Agent", "TDZ TRADE API/Java " + System.getProperty("java.version"));
    	//post.addHeader("Content-Type", ctype);
    	if (null != headerMap) {
    		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
    			post.addHeader(entry.getKey(), entry.getValue());
			}
    	}
    	
    	if (null != params) {
    		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			NameValuePair pair = null;
			Object obj = null;
			for (String key : params.keySet()) {
				obj = params.get(key);
				if (obj.getClass().isArray()) {
					String arr[] = (String[]) obj;
					for (String value : arr) {
						pair = new BasicNameValuePair(key, value);
						parameters.add(pair);
					}
				} else if (obj instanceof String){
					pair = new BasicNameValuePair(key, (String) obj);
					parameters.add(pair);
				} else {
					throw new RuntimeException("http get not support parameter: " + obj.toString());
				}
			}
			HttpEntity entity = new UrlEncodedFormEntity(parameters, DEFAULT_CHARSET);
			post.setEntity(entity);
    	}
    	
    	try {
    		resp = getResponseAsString(httpClient, post);
		} catch (IOException e) {
			throw e;
		}
		
		return resp;
	}
	
	/**
	 * 获取request的header
	 * @param request
	 * @return
	 */
	public static Map<String, String> getHeaderMap(HttpServletRequest request){
		Map<String, String> headerMap = new HashMap<String, String>();
		
		Enumeration<String> headNames = request.getHeaderNames();
		
		if(null != headNames){
			while(headNames.hasMoreElements()){
				String name = headNames.nextElement();
				headerMap.put(name, request.getHeader(name));
			}
		}
		
		return headerMap;
	}
	
	protected static String getResponseAsString(HttpClient httpClient, HttpPost post) throws Exception {
		String resp = null;
		
		HttpResponse httpResp = httpClient.execute(post);
    	if (httpResp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
    		throw new Exception("请求失败! code: " + httpResp.getStatusLine().getStatusCode());
    	} else {
//    		Header header = null;
//    		String respContentType = null;
    		
    		HttpEntity respEntity = httpResp.getEntity();
    		
    		if(null == respEntity){
//    			header = httpResp.getFirstHeader("Content-Type");
//    			if(null != header) {
//    				respContentType = header.getValue();
//    			}
//    			
//    			resp = respContentType;
    			return null;
    		}else{
//    			header = respEntity.getContentType();
//    			if(null != header) {
//    				respContentType = header.getValue();
//    			} else {
//    				respContentType = "text/html";
//    			}
    			
    			InputStream stream = respEntity.getContent();
    			try {
    				
    				
    				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    				
    				
    				IOUtils.copy(stream, byteStream);
    				
    				
//                    byte[] buffer = new byte[1024];
//                    int len = 0;
//                    while ((len = stream.read(buffer)) != -1) {
//                    	byteStream.write(buffer, 0, len);
//                    }
                    resp = new String(byteStream.toByteArray(), DEFAULT_CHARSET);
    			} catch (RuntimeException e) {
    				post.abort();
    				throw e;
    			} finally {
    				stream.close();
    			}
    		}
    	}
    	
    	return resp;
	}
	
}
