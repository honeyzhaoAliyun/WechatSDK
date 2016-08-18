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
import com.ifp.wechat.service.UserService;

@WebServlet(urlPatterns = {"/cervejas/*"})
public class CervejaServlet extends HttpServlet {
	
	private UserService userService;
	private UserWeiXin userWeiXin;

	private static final long serialVersionUID = 7756097845779586921L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject openIDresult = new JSONObject();
		openIDresult.accumulate("openIdUser", escreveJSON(req, resp));
		PrintWriter out = resp.getWriter();
	    out.write(openIDresult.toString());
	}

	@SuppressWarnings("static-access")
	private JSONObject escreveJSON(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String openid = req.getParameter("openid");
		JSONObject result = new JSONObject();
		userWeiXin = new UserWeiXin();
		if(openid !=null){
			userWeiXin = userService.getUserInfo(openid);
			result.accumulate("openid", userWeiXin.getOpenid());
			result.accumulate("nickname", userWeiXin.getNickname());
			result.accumulate("sex", userWeiXin.getSex());
			result.accumulate("city", userWeiXin.getCity());
			result.accumulate("country", userWeiXin.getCountry());
			result.accumulate("province", userWeiXin.getProvince());
			result.accumulate("language", userWeiXin.getLanguage());
			result.accumulate("headimgurl", userWeiXin.getHeadimgurl());
			result.accumulate("subscribe_time", userWeiXin.getSubscribe_time());
			result.accumulate("subscribe", userWeiXin.getSubscribe());
		}
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
