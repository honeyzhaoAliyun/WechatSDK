/**
 * 
 */
package com.ifp.wechat.token.server;

import com.ifp.wechat.util.ConfigFileUtil;
import com.ifp.wechat.util.PropertiesUtils;
import com.ifp.wechat.utils.Config;

/**
 * 适配器
 * @author honey.zhao@aliyun.com
 * @date   2015年1月30日
 */
public class AccessTokenServer extends AbsServer implements TokenServer {
	
	private static final String AccessTokenTicketFile = "wechat_accesstoken_ticket.properties";
	/**
	 * 将保存在 properties 文件中的 accesstoken 读取出来
	 */
	public String token(){
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		String  access_token = PropertiesUtils.readValue(filepath, "access_token");
		return access_token;
	}

	@Override
	protected String getCustomerServerClass() {
		return Config.instance().getAccessTokenServer();
	}

	@Override
	public IServer defaultServer() {
		return AccessTokenMemServer.instance();
	}

}
