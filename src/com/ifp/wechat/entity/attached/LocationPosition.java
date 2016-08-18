package com.ifp.wechat.entity.attached;

/**
 * 
 * 用户地理位置获取
 * @author honey.zhao@aliyun.com
 * @Date 2016-08-17
 *
 */
public class LocationPosition {
	
	private String openid;
	
	private String latitude;
	
	private String longitude;
	
	private String speed;
	
	private String accuracy;

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public LocationPosition(String openid, String latitude, String longitude,
			String speed, String accuracy) {
		super();
		this.openid = openid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.accuracy = accuracy;
	}

	public LocationPosition() {
		
	}
	

}
