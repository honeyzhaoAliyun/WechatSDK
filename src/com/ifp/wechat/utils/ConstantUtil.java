package com.ifp.wechat.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


/**
 * 货运物流调用接口参数配置
 * @author honey.zhao@aliyun.com
 */

public class ConstantUtil {
	
	private static Properties props = new Properties();

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("wechat_setting.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}

	public static void setProps(Properties p) {
		props = p;
	}
}
