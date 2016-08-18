package com.ifp.wechat.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ifp.wechat.entity.attached.LocationPosition;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonFileUtil {
	
	public static final String JSONFILEPATH = "D:\\Programs\\apache-tomcat-7.0.69\\webapps\\WechatSDK\\getlocations\\WxGetLocation.json";
	private static LocationPosition locationPosition;
	private static List<LocationPosition> locations ;
	 //从给定位置读取Json文件
    public static String readJson(String path){
        //从给定位置获取文件
        File file = new File(path);
        BufferedReader reader = null;
        //返回值,使用StringBuffer
        StringBuffer data = new StringBuffer();
        //
        try {
            reader = new BufferedReader(new FileReader(file));
            //每次读取文件的缓存
            String temp = null;
            while((temp = reader.readLine()) != null){
                data.append(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭文件流
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
    //给定路径与Json文件，存储到硬盘
    public static void writeJson(String path,Object json){
        BufferedWriter writer = null;
        File file = new File(path);
        //如果文件不存在，则新建一个
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //写入
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("文件写入成功！");
    }
    
    /**
    * 从json数组中得到相应java数组
    * JSONArray下的toArray()方法的使用
    * @param str
    * @return
    */
    public static Object[] getJsonToArray(String str) {
       JSONArray jsonArray = JSONArray.fromObject(str);
       return jsonArray.toArray();
   }
    
    /**
	 * 操作json文件 write
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String writeJsonFile(String openid,String latitude,String longitude,String speed,String accuracy){
		
		JSONObject jsonObject = new JSONObject();
		JSONArray locationsArray = new JSONArray();
		String result = "error";
		String path = JsonFileUtil.JSONFILEPATH;
		String jsonDataResult = readJson(path);
		jsonObject = JSONObject.fromObject(jsonDataResult);
		locationsArray = JSONArray.fromObject(jsonObject.getString("locations"));
		Object[] obj=getJsonToArray(locationsArray.toString());
		
		locations = new ArrayList<LocationPosition>();
		List openidArray = new ArrayList<String>();
		
		for(int i=0;i<obj.length;i++){
			 JSONObject locationObject = JSONObject.fromObject(obj[i]);
			 openidArray.add(locationObject.getString("openid"));
			 
			 locationPosition = new LocationPosition();
			 locationPosition.setOpenid(locationObject.getString("openid"));
			 locationPosition.setLatitude(locationObject.getString("latitude"));
			 locationPosition.setLongitude(locationObject.getString("longitude"));
			 locationPosition.setSpeed(locationObject.getString("speed"));
			 locationPosition.setAccuracy(locationObject.getString("accuracy"));
			 locations.add(locationPosition);
		}
		//包含openid
		if(openidArray.contains(openid)){
			for(LocationPosition lp : locations){
				if(lp.getOpenid().equals(openid)){
					lp.setOpenid(openid);
					lp.setLatitude(latitude);
					lp.setLongitude(longitude);
					lp.setSpeed(speed);
					lp.setAccuracy(accuracy);
				}
				
			}
		}else{//不包含openid
			 locationPosition = new LocationPosition();
			 locationPosition.setOpenid(openid);
			 locationPosition.setLatitude(latitude);
			 locationPosition.setLongitude(longitude);
			 locationPosition.setSpeed(speed);
			 locationPosition.setAccuracy(accuracy);
			 locations.add(locationPosition);
		}
		//覆盖重新赋值
		jsonObject = new JSONObject();
		locationsArray = new JSONArray();
		locationsArray.addAll(locations);
		jsonObject.accumulate("locations", locationsArray);
		
		try {
			writeJson(path, jsonObject);
			System.out.println("文件读取成功！");
			result = "success";
		} catch (Exception e) {
			result = "error";
		}
		return result;
	}
	
	
	/**
	 * 操作json文件 write
	 */
	public static LocationPosition readJsonFile(String openid){
		String path = JsonFileUtil.JSONFILEPATH;
		String jsonDataResult = readJson(path);
		LocationPosition locationPosition = new LocationPosition();
		JSONObject jsonObject = JSONObject.fromObject(jsonDataResult);
		JSONArray locationsArray = JSONArray.fromObject(jsonObject.getString("locations"));
		List<LocationPosition> locations = new ArrayList<LocationPosition>();
		Object[] obj=getJsonToArray(locationsArray.toString());
		for(int i=0;i<obj.length;i++){
			 JSONObject locationObject = JSONObject.fromObject(obj[i]);
			 locationPosition = new LocationPosition();
		     locationPosition.setOpenid(locationObject.getString("openid"));
		     locationPosition.setLatitude(locationObject.getString("latitude"));
		     locationPosition.setLongitude(locationObject.getString("longitude"));
		     locationPosition.setSpeed(locationObject.getString("speed"));
		     locationPosition.setAccuracy(locationObject.getString("accuracy"));		     
		     locations.add(locationPosition);
		}
		for(LocationPosition locationPositionTemp : locations){
			if(locationPositionTemp.getOpenid().equals(openid)){
				locationPosition = locationPositionTemp;
			}
		}
		return locationPosition;
	}
    
    
    public static void main(String[] args) {
    	String path = JsonFileUtil.JSONFILEPATH;
		String jsonDataResult = readJson(path);
		JSONObject jsonObject = JSONObject.fromObject(jsonDataResult);
		JSONArray locationsArray = JSONArray.fromObject(jsonObject.getString("locations"));
		List<LocationPosition> locations = new ArrayList<LocationPosition>();
		Object[] obj=getJsonToArray(locationsArray.toString());
		for(int i=0;i<obj.length;i++){
			 JSONObject locationObject = JSONObject.fromObject(obj[i]);
			 LocationPosition locationPosition = new LocationPosition();
		     
		     locationPosition.setOpenid(locationObject.getString("openid"));
		     locationPosition.setLatitude(locationObject.getString("latitude"));
		     locationPosition.setLongitude(locationObject.getString("longitude"));
		     locationPosition.setSpeed(locationObject.getString("speed"));
		     locationPosition.setAccuracy(locationObject.getString("accuracy"));		     
		     locations.add(locationPosition);
		     
		}
	}
	public static LocationPosition getLocationPosition() {
		return locationPosition;
	}
	public static void setLocationPosition(LocationPosition locationPosition) {
		JsonFileUtil.locationPosition = locationPosition;
	}
	public static List<LocationPosition> getLocations() {
		return locations;
	}
	public static void setLocations(List<LocationPosition> locations) {
		JsonFileUtil.locations = locations;
	}
	
}
