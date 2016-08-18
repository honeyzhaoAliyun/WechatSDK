package com.ifp.wechat.token.server.impl;

import org.apache.log4j.Logger;

import com.ifp.wechat.token.Token;
import com.ifp.wechat.token.TokenProxy;
import com.ifp.wechat.token.server.CustomerServer;
import com.ifp.wechat.util.ConfigFileUtil;
import com.ifp.wechat.util.PropertiesUtils;

public class CustomerJsApiTicketServer extends CustomerServer {
	private static Logger logger = Logger.getLogger(CustomerJsApiTicketServer.class);

	private static final String AccessTokenTicketFile = "wechat_accesstoken_ticket.properties";
	@Override
	public boolean save(Token token) {
		logger.info("=========ticket save()============"+token.getToken());
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		PropertiesUtils.writeProperties(filepath, "jsapi_ticket", token.getToken());
		return true;
	}

	@Override
	protected String find() {
		logger.info("=========jsapiTicket find()============");
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		String  jsapi_ticket = PropertiesUtils.readValue(filepath, "jsapi_ticket");
		return jsapi_ticket;
	}

}
