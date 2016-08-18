package com.ifp.wechat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.ifp.wechat.util.WeixinUtil;

/**
 * 文件上传下载
 * @author honey.zhao@aliyun.com
 * @version 1.0
 * 
 */
public class FileService {

	public static Logger log = Logger.getLogger(FileService.class);

	/**
	 * 上传文件URL
	 */
	private static String uploadFileUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	/**
	 * 下载文件URL
	 */
	private static String dwonloadFileURL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 上传多媒体文件
	 * 
	 * @param fileType
	 *            文件类型,分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param filename
	 *            文件名称
	 * @param filePath
	 *            文件路径
	 * @return json
	 */
	public static JSONObject uploadFile(String fileType, String filename, String filePath) {

		String requestUrl = uploadFileUrl.replace("ACCESS_TOKEN",
				WeixinUtil.getToken()).replace("TYPE", fileType);
		File file = new File(filePath);
		String result = "";
		String end = "\r\n";
		String twoHyphens = "--"; // 用于拼接
		String boundary = "*****"; // 用于拼接 可自定义
		URL submit = null;
		try {
			submit = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) submit
					.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，准备上传文件
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"" + file
					+ "\";filename=\"" + filename + ";filelength=\"" + filePath
					+ ";" + end);
			dos.writeBytes(end);
			// 对文件进行传输
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			fis.close(); // 关闭文件流

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();
			dos.close();
			is.close();
		} catch (MalformedURLException e) {
			log.error("File upload fail..." + e);
		} catch (IOException e) {
			log.error("File upload fail..." + e);
		}
		return JSONObject.fromObject(result);
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @param mediaId
	 * @return 媒体url地址
	 */
	public static String downloadFile(String mediaId) {
		return dwonloadFileURL.replace("ACCESS_TOKEN", WeixinUtil.getToken())
				.replace("MEDIA_ID", mediaId);
	}
	/**
     * 多媒体下载接口
     * @comment 不支持视频文件的下载
     * @param fileName  素材存储文件路径
     * @param token     认证token
     * @param mediaId   素材ID（对应上传后获取到的ID）
     * @return 素材文件
     */
	public static void downloadFile(String fileName,String mediaId) {
		InputStream inputStream = getInputStream(mediaId);
	       byte[] data = new byte[1024];
	       int len = 0;
	       FileOutputStream fileOutputStream = null;
	       try {
	           fileOutputStream = new FileOutputStream(fileName);
	           while ((len = inputStream.read(data)) != -1) {
	               fileOutputStream.write(data, 0, len);
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       } finally {
	           if (inputStream != null) {
	               try {
	                   inputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	           if (fileOutputStream != null) {
	               try {
	                   fileOutputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
		}
	
	   public static  InputStream getInputStream(String mediaId) {
	       InputStream is = null;
	       String url = dwonloadFileURL.replace("ACCESS_TOKEN", WeixinUtil.getToken()).replace("MEDIA_ID", mediaId);
	       try {
	           URL urlPOST = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlPOST.openConnection();
	           http.setRequestMethod("POST"); // 必须是post方式请求
	           http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
	           http.connect();
	           // 获取文件转化为byte流
	           is = http.getInputStream();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }

	       return is;

	   }
}
