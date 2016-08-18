package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ifp.wechat.entity.user.UserWeiXin;
import com.ifp.wechat.service.UserService;
import com.ifp.wechat.util.ConfigFileUtil;
import com.ifp.wechat.util.PropertiesUtils;

@WebServlet(urlPatterns = {"/jsapiticket/*"})
public class JsApiTicketServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(JsApiTicketServlet.class);
	private static final String wechatSystemFile = "wechat.properties";
	private static final String AccessTokenTicketFile = "wechat_accesstoken_ticket.properties";
	
	private UserService userService;
	private UserWeiXin userWeiXin;

	private static final long serialVersionUID = 7756097845779586921L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject openIDresult = new JSONObject();
		openIDresult.accumulate("jsapitoken", JsApiTicketJSON(req, resp));
		PrintWriter out = resp.getWriter();
	    out.write(openIDresult.toString());
	}

	private JSONObject JsApiTicketJSON(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		logger.info("jsapiTicket 调用API");
		JSONObject result = new JSONObject();
		//wechatSystem
		String filepathsystem = ConfigFileUtil.getPath(wechatSystemFile);
		//jsapi_ticketFile
		String filepath = ConfigFileUtil.getPath(AccessTokenTicketFile);
		
		String  appId = PropertiesUtils.readValue(filepathsystem, "appId");
		String  appSecret = PropertiesUtils.readValue(filepathsystem, "appSecret");
		String  token = PropertiesUtils.readValue(filepathsystem, "token");
		String  jsapi_ticket = PropertiesUtils.readValue(filepath, "jsapi_ticket");
		
		result.accumulate("appId", appId);
		result.accumulate("appSecret", appSecret);
		result.accumulate("token", token);
		result.accumulate("jsapi_ticket", jsapi_ticket);
		
		return result;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserWeiXin getUserWeiXin() {
		return userWeiXin;
	}

	public void setUserWeiXin(UserWeiXin userWeiXin) {
		this.userWeiXin = userWeiXin;
	}
	
	
}
