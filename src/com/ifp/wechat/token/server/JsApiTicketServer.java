/**
 * 
 */
package com.ifp.wechat.token.server;

import com.ifp.wechat.util.ConfigFileUtil;
import com.ifp.wechat.util.PropertiesUtils;
import com.ifp.wechat.utils.Config;


/**
 * Ticket server适配器
 * @author honey.zhao@aliyun.com
 * @date   2015年1月29日
 */
public class JsApiTicketServer extends AbsServer implements TicketServer  {

	private static final String AccessTokenTicketFile = "wechat_accesstoken_ticket.properties";
	
	/**
	 * 将保存在 properties 文件中的 jsapi_ticket 读取出来
	 */
	public String ticket() {
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		String  jsapi_ticket = PropertiesUtils.readValue(filepath, "jsapi_ticket");
		return jsapi_ticket;
	}

	/**
	 * 
	 */
	@Override
	protected String getCustomerServerClass() {
		return Config.instance().getJsApiTicketServer();
	}

	/**
	 * 
	 */
	@Override
	public IServer defaultServer() {
		return JsApiTicketMemServer.instance();
	}

}
