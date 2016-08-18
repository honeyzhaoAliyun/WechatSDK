
package com.ifp.wechat.token;


import org.apache.log4j.Logger;

import com.ifp.wechat.util.ConfigUtil;
import com.ifp.wechat.utils.ConstantUtil;


/**
 * Access token实体模型
 * @author honey.zhao@aliyun.com
 * @date   2014年12月12日
 */
public class AccessToken extends Token {
	
	private static Logger logger = Logger.getLogger(AccessToken.class);
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	

	@Override
	protected String tokenName() {
		return "access_token";
	}

	@Override
	protected String expiresInName() {
		return "expires_in";
	}

	/**
	 * 组织accesstoken的请求utl
	 */
	@Override
	protected String accessTokenUrl() {
		String appid = ConfigUtil.get("appId");
		String appsecret = ConfigUtil.get("appSecret");
		String url = ACCESS_TOKEN_URL + "&appid=" + appid + "&secret=" + appsecret;
		logger.info("创建获取access_token url");
		return url;
	}
	
	
}
