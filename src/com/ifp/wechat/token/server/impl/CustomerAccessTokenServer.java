package com.ifp.wechat.token.server.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jboss.weld.servlet.ServletApiAbstraction;

import com.ifp.wechat.token.Token;
import com.ifp.wechat.token.server.CustomerServer;
import com.ifp.wechat.util.ConfigFileUtil;
import com.ifp.wechat.util.PropertiesUtils;

public class CustomerAccessTokenServer extends CustomerServer {

	private static Logger logger = Logger.getLogger(CustomerAccessTokenServer.class);
	private static final String AccessTokenTicketFile = "wechat_accesstoken_ticket.properties";
	ServletRequest request = null;
	ServletResponse response = null;
	
	@Override
	public boolean save(Token token) {
		logger.info("=========token save()============"+token.getToken());
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		PropertiesUtils.writeProperties(filepath, "access_token", token.getToken());
		return true;
	}

	@Override
	protected String find() {
		logger.info("=========token find()============");
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		String  access_token = PropertiesUtils.readValue(filepath, "access_token");
		return access_token;
	}

}
