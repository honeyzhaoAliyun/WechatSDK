
package com.ifp.wechat.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author honey.zhao@aliyun.com
 * @date   2015年02月8日
 */
public class Config {
	
	private static Logger logger = Logger.getLogger(Config.class);
	
	private static final String configFile = "/wechat.properties";
	
	private String token;
	private String appid;
	private String appSecret;
	private String accessTokenServer;
	private String jsApiTicketServer;
	private static Config config = new Config();
	
	private Config(){
		Properties p = new Properties();
		InputStream inStream = this.getClass().getResourceAsStream(configFile);
		if(inStream == null){
			logger.error("根目录下找不到wechat.properties文件");
			return;
		}
		try {
			p.load(inStream);
			this.token = p.getProperty("token").trim();
			this.appid = p.getProperty("appId").trim();
			this.appSecret = p.getProperty("appSecret").trim();
			this.accessTokenServer = p.getProperty("wechat.accessToken.server.class").trim();
			this.jsApiTicketServer = p.getProperty("wechat.ticket.jsapi.server.class").trim();
			inStream.close();
		} catch (IOException e) {
			logger.error("load wechat.properties error,class根目录下找不到wechat.properties文件");
			e.printStackTrace();
		}
		logger.info("load wechat.properties success");
	}
	
	public static Config instance(){
		return config;
	}
	public String getToken() {
		return token;
	}
	public String getAppid() {
		return appid;
	}
	public String getAppSecret() {
		return appSecret;
	}

	public String getAccessTokenServer(){
		return accessTokenServer;
	}

	public String getJsApiTicketServer() {
		return jsApiTicketServer;
	}
	
	
}
