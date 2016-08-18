package com.ifp.wechat.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.log4j.Logger;


public class HttpUtil {
	public static Logger logger = Logger.getLogger(HttpUtil.class);

	public static String getInvoke(String urlStr,String method){
		URL url;
		InputStream inputStream = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		HttpURLConnection connection = null;
		String is;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-type", "text/html");
			connection.setRequestProperty("Accept-Charset", "utf-8");
			connection.setRequestProperty("contentType", "utf-8");
			
			connection.setReadTimeout(3000);
			logger.info("Connect, URL : " + urlStr);
			connection.connect();
			int responseCode=connection.getResponseCode();            
            System.out.println("-responseCode->>"+responseCode);
            if(responseCode==200){               
            	inputStream = connection.getInputStream();
            	isr = new InputStreamReader(inputStream,"UTF-8");
            	br = new BufferedReader(isr);
            	int c = -1;
            	StringBuilder isb = new StringBuilder();
            	while ((c = br.read()) >= 0) {
            		isb.append((char) c);
            	}
            	is = isb.toString();
            	return is;
            }else{
            	return null;
            }
		} catch (FileNotFoundException e2) {
			System.out.println("====FileNotFoundException=====连接服务器或数据库异常=================");
			logger.error("Read return info error : " + urlStr + "\n", e2);
			return null;
		} catch(ConnectException e3){
			System.out.println("====ConnectException=====连接服务器或数据库异常=================");
			logger.error("Read return info error : " + urlStr + "\n", e3);
			return null;
		}catch (SocketTimeoutException e) {
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
