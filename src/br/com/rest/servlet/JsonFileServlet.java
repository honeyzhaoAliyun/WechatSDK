package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifp.wechat.entity.attached.LocationPosition;
import com.ifp.wechat.util.JsonFileUtil;

import net.sf.json.JSONObject;

/**
 * 
 * 通过java 操作json文件 实现 本地数据存储读取
 * @author honey.zhao@aliyun.com
 * @Date 2016-08-11
 *
 */

@WebServlet(urlPatterns = {"/jsonFile/*"})
public class JsonFileServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("static-access")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonobject = new JSONObject();
		
		LocationPosition locationPosition = new LocationPosition();
		String openid  = req.getParameter("openid");
		
		String longitude  = req.getParameter("longitude");
		String latitude  = req.getParameter("latitude");
		String speed  = req.getParameter("speed");
		String accuracy  = req.getParameter("accuracy");
		
		String type = req.getParameter("type"); 
		if(openid !=null && !openid.equals("")  && type !=null && !type.equals("")){
			
			if(type.equals("write")){
				if(longitude !=null && !longitude.equals("") && latitude !=null && !latitude.equals("")){
					String result = JsonFileUtil.writeJsonFile(openid, latitude, longitude, speed, accuracy);
					if(result.equals("success")){
						jsonobject.accumulate("status", "success");
					}else{
						jsonobject.accumulate("status", "error");
					}
				}else{
					jsonobject.accumulate("message", "Please add the parameters 'openid','lng','lat','speed','accuracy' and 'type', where 'type' parameter value is 'write' or 'read' .");
				}
				
			}else if(type.equals("read")){
				locationPosition = JsonFileUtil.readJsonFile(openid);
				if(locationPosition != null){
					jsonobject.accumulate("status", "success");
					jsonobject.accumulate("locationPosition", new JSONObject().fromObject(locationPosition));
				}else{
					jsonobject.accumulate("status", "error");
				}
				
			}else{
				jsonobject.accumulate("message", "Please add the parameters 'openid','lng','lat','speed','accuracy' and 'type', where 'type' parameter value is 'write' or 'read' .");
			}
		}else{
			jsonobject.accumulate("message", "Please add the parameters 'openid','lng','lat','speed','accuracy' and 'type', where 'type' parameter value is 'write' or 'read' .");
		}
		PrintWriter out = resp.getWriter();
		out.write(jsonobject.toString());
	}
	
}
