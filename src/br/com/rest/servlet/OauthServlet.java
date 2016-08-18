package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ifp.wechat.entity.user.UserWeiXin;
import com.ifp.wechat.service.OAuthService;
import com.ifp.wechat.service.UserService;

@WebServlet(urlPatterns = {"/oauth/*"})
public class OauthServlet extends HttpServlet {
	
	private UserService userService;
	private UserWeiXin userWeiXin;
	private OAuthService oAuthService;

	private static final long serialVersionUID = 7756097845779586921L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonobject = new JSONObject();
		String uri = req.getParameter("uri");
		String  OauthUrl = OAuthService.getOauthUrl(uri, "utf-8", "snsapi_base");
		
		PrintWriter out = resp.getWriter();
	    out.write(OauthUrl.toString());
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

	public OAuthService getoAuthService() {
		return oAuthService;
	}

	public void setoAuthService(OAuthService oAuthService) {
		this.oAuthService = oAuthService;
	}
	
	
}
