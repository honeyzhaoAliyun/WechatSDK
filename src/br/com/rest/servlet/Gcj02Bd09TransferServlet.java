package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * 
 * 关于 GCJ-02 和 BD-09  算法转换如下:
 * <p>
 * 国测局GCJ-02坐标体系（谷歌、高德、腾讯），与百度坐标BD-09体系的转换，在CSDN上有很详细的讲解：http://blog.csdn.net/coolypf/article/details/8569813
 * <p>
 * 其中 bd_encrypt 将 GCJ-02 坐标转换成 BD-09 坐标， bd_decrypt 反之。
 * @author honey.zhao@aliyun.com
 * @Date 2016-08-11
 *
 */

@WebServlet(urlPatterns = {"/gcj02Bd09Transfer/*"})
public class Gcj02Bd09TransferServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	private Map<String, Object> mapbdgg = new HashMap<String, Object>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		JSONObject jsonobject = new JSONObject();
		JSONObject mapObject = new JSONObject();
		String lng  = req.getParameter("lng");
		String lat  = req.getParameter("lat");
		String type = req.getParameter("type"); 
		if(type !=null && !type.equals("") && lng !=null && !lng.equals("") && lat !=null && !lat.equals("")){
			jsonobject.accumulate("status", "success");
			double dlng = Double.parseDouble(lng);
			double dlat = Double.parseDouble(lat);
			if(type.equals("ggtobd")){
				mapbdgg = new HashMap<String, Object>();
				mapbdgg = this.bd_encrypt(dlat, dlng);//高德、 腾讯、谷歌坐标转换百度坐标
				mapObject.accumulate("lat", mapbdgg.get("bd_lat"));
				mapObject.accumulate("lng", mapbdgg.get("bd_lon"));
				
				jsonobject.accumulate("position", mapObject);
			}else if(type.equals("bdtogg")){
				mapbdgg = new HashMap<String, Object>();
				mapbdgg = this.bd_decrypt(dlat, dlng);//百度坐标转换 高德、腾讯、谷歌坐标
				mapObject.accumulate("lat", mapbdgg.get("gg_lat"));
				mapObject.accumulate("lng", mapbdgg.get("gg_lon"));
				
				jsonobject.accumulate("position", mapObject);
			}else{
				jsonobject.accumulate("message", "Please add the parameters 'lng','lat','type', where 'type' parameter value is' ggtobd' or 'bdtogg' .");
			}
		}else{
			jsonobject.accumulate("message", "Please add the parameters 'lng','lat','type', where 'type' parameter value is' ggtobd' or 'bdtogg' .");
		}
		PrintWriter out = resp.getWriter();
		out.write(jsonobject.toString());
	}
	
	/**
	 * 将 GCJ-02 坐标转换成 BD-09 坐标
	 * @param gg_lat
	 * @param gg_lon
	 * @param bd_lat
	 * @param bd_lon
	 */
	public Map<String, Object> bd_encrypt(double gg_lat, double gg_lon)  
	{  
		mapbdgg = new HashMap<String, Object>();
	    double x = gg_lon, y = gg_lat;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    
	    double bd_lon = z * Math.cos(theta) + 0.0065;  
	    double bd_lat = z * Math.sin(theta) + 0.006;  
	    
	    mapbdgg.put("bd_lon", bd_lon);
	    mapbdgg.put("bd_lat", bd_lat);
	    
	    return mapbdgg;
	    
	}  
	  
	/**
	 * 将  BD-09 坐标转换成GCJ-02 坐标
	 * @param bd_lat
	 * @param bd_lon
	 * @param gg_lat
	 * @param gg_lon
	 */
	public Map<String, Object> bd_decrypt(double bd_lat, double bd_lon)  
	{  
		mapbdgg = new HashMap<String, Object>();
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;  
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);  
	    
	    double gg_lon = z * Math.cos(theta);  
	    double gg_lat = z * Math.sin(theta);  
	    
	    mapbdgg.put("gg_lon", gg_lon);
	    mapbdgg.put("gg_lat", gg_lat);
	    
	    return mapbdgg;
	}

	public Map<String, Object> getMapbdgg() {
		return mapbdgg;
	}

	public void setMapbdgg(Map<String, Object> mapbdgg) {
		this.mapbdgg = mapbdgg;
	} 
	
	
}
