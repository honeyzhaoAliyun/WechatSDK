package com.ifp.wechat.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.log4j.Logger;
/**
 * 给-微信公众号网页端-提供接口调用工具类
 * @author zhaoguoqing
 * @Date 2015-01-07
 */
public class RestUtil {
	private static final Logger logger = Logger.getLogger(RestUtil.class);

	/**
	 * REST HTTP RESPUEST
	 * @param method
	 * @param field
	 * @return String
	 */
	public static String getInvoke(String field, String value){

		StringBuffer url = new StringBuffer();
		url.append(ConstantUtil.get("REST_IP"));
		url.append("/WechatSDK/cervejas");
		if (null != field){
			url.append("?").append(field).append("=").append(value);
		}
		return getInvoke(url.toString());
	}
	
	public static String getInvoke(String urlStr){
		URL url;
		InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection connection = null;
		String is;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setReadTimeout(30000);
			logger.info("Connect, URL : " + urlStr);
			connection.connect();
			inputStream = connection.getInputStream();
			isr = new InputStreamReader(inputStream,"utf-8");
			br = new BufferedReader(isr);
			int c = -1;
			StringBuilder isb = new StringBuilder();
			while ((c = br.read()) >= 0) {
				isb.append((char) c);
			}
			is = isb.toString();
			return is;
		} catch (FileNotFoundException e2) {
			logger.error("Read return info error : " + urlStr + "\n", e2);
			return null;
		} catch (SocketTimeoutException e) {
			logger.error("Wait timeout, pass it.\n" + urlStr, e);
			return null;
		} catch (IOException e1) {
			logger.error("IOException." + urlStr, e1);
			InputStream errStream = connection.getErrorStream();
			if (null == errStream)
				return null;
			BufferedReader errbr = new BufferedReader(new InputStreamReader(errStream));
			StringBuilder errisb = new StringBuilder();
			int c = -1;
			try {
				while ((c = errbr.read()) >= 0) {
					errisb.append((char) c);
				}
				String errinfo = errisb.toString();
				logger.error(errinfo);
			} catch (IOException e) {
				logger.error("Get error info IOException.", e1);
				return null;
			} finally {
				try {
					errStream.close();
					errbr.close();
				} catch (IOException e) {
					logger.error("Get error info close stream IOException.", e);
				}
			}
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				logger.error("Dispacher IOException", e);
			}
			connection.disconnect();
		}
	}

}
