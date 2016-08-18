package br.com.rest.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ifp.wechat.entity.user.UserWeiXin;
import com.ifp.wechat.service.FileService;
import com.ifp.wechat.service.OAuthService;
import com.ifp.wechat.service.UserService;
import com.ifp.wechat.utils.HttpPostUtil;

@WebServlet(urlPatterns = {"/notifydownload/*"})
public class NotifyDownLoadServlet extends HttpServlet {
	
	private UserService userService;
	private UserWeiXin userWeiXin;
	private OAuthService oAuthService;
	private FileService fileService;

	private static final long serialVersionUID = 7756097845779586921L;

	@SuppressWarnings("static-access")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonobject = new JSONObject();
		String serverId = req.getParameter("serverId");
		@SuppressWarnings("unused")
		String imageType = req.getParameter("imageType");//暂时不判断
		
		String vehicleCode = req.getParameter("vehicleCode");
		String lodFileDate = req.getParameter("lodFileDate");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//String restUrl = "http://127.0.0.1:8080/gps/monitor?m=uploadVhcImg&vhcCode="+vehicleCode+"&oldFileDate="+lodFileDate+"&username="+username+"&password="+password+"";
		String restUrl = "http://127.0.0.1:8080/gps/monitor?m=uploadVhcImg&vhcCode="+vehicleCode+"&oldFileDate=20160722&username=test&password=test";
		System.out.println("NotifyDownLoadServlet--->doget--->restUrl:"+restUrl);
		
		String fileName =Long.toString(new Date().getTime());
		String saveFilePath = "D:\\Programs\\WechatTempImages\\"+fileName+".jpg";
		fileService.downloadFile(saveFilePath,serverId);
		try {
			HttpPostUtil.formSubmitFile(fileName,restUrl);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		jsonobject.accumulate("status", "success");
		PrintWriter out = resp.getWriter();
		out.write(jsonobject.toString());
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
	public FileService getFileService() {
		return fileService;
	}
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	
}
