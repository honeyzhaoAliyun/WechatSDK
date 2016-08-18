/**
 *  Copyright (c) 2014 honey.zhao@aliyun.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ifp.wechat.service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ifp.wechat.constant.ConstantWeChat;
import com.ifp.wechat.entity.message.resp.NewsMessage;
import com.ifp.wechat.entity.message.resp.TextMessage;
import com.ifp.wechat.util.MessageUtil;

/**
 * 调用核心业务类接收消息、处理消息
 * @author honey.zhao@aliyun.com
 * @version 1.0
 * 
 */
public class CoreService {

	public static Logger log = Logger.getLogger(CoreService.class);
	
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return String
	 */
	
	public static String processWebchatRequest(HttpServletRequest request){
		String respMessage = null;
		
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = (TextMessage) MessageService
					.bulidBaseMessage(requestMap,
							ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = (NewsMessage) MessageService
					.bulidBaseMessage(requestMap,
							ConstantWeChat.RESP_MESSAGE_TYPE_NEWS);

			String respContent = "";
			
			//---关注多图文处理
			String respContent_subscribe= "";
			respContent_subscribe=request.getParameter("respContent_subscribe")==null ? "" : request.getParameter("respContent_subscribe");
			
			// 文本消息
			if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				respContent = "智能放牧系统运营中心感谢您的咨询，请致电12349客服将为您提供详细服务内容。";
				textMessage.setContent(respContent);
				respMessage = MessageService.bulidSendMessage(textMessage,ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				
				
			} else if (msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 事件KEY值，与创建自定义菜单时指定的KEY值对应
				String eventKey = requestMap.get("EventKey");
				if (eventType.equals(ConstantWeChat.EVENT_TYPE_SUBSCRIBE)) {
					// 关注
					respContent = "“阿拉善智能放牧”科技改变生活、移动智能放牧。/亲亲 /亲亲 么么哒！";
					textMessage.setContent(respContent);
					
				} else if (eventType.equals(ConstantWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息
				} else if (eventType.equals(ConstantWeChat.EVENT_TYPE_CLICK)) {
					
				}
				respMessage = MessageService.bulidSendMessage(textMessage,ConstantWeChat.RESP_MESSAGE_TYPE_TEXT);
				
			}else if(msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_LOCATION)){//获取到地址信息
				System.out.println("-----获取用户位置信息------");
			}else if(msgType.equals(ConstantWeChat.REQ_MESSAGE_TYPE_VOICE)){//获取到音频信息
				
			}
			
		} catch (Exception e) {
			log.error("the wechatSDK OF WECHAT error !\n", e);
		}
		return respMessage;
	}
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	/** 
	 * 判断是否是QQ表情 
	 *  
	 * @param content 
	 * @return 
	 */  
	public static boolean isQqFace(String content) {  
	    boolean result = false;  
	  
	    // 判断QQ表情的正则表达式  
	    String qqfaceRegex = "/::\\)|/::~|/::B|/::\\||/:8-\\)|/::<|/::$|/::X|/::Z|/::'\\(|/::-\\||/::@|/::P|/::D|/::O|/::\\(|/::\\+|/:--b|/::Q|/::T|/:,@P|/:,@-D|/::d|/:,@o|/::g|/:\\|-\\)|/::!|/::L|/::>|/::,@|/:,@f|/::-S|/:\\?|/:,@x|/:,@@|/::8|/:,@!|/:!!!|/:xx|/:bye|/:wipe|/:dig|/:handclap|/:&-\\(|/:B-\\)|/:<@|/:@>|/::-O|/:>-\\||/:P-\\(|/::'\\||/:X-\\)|/::\\*|/:@x|/:8\\*|/:pd|/:<W>|/:beer|/:basketb|/:oo|/:coffee|/:eat|/:pig|/:rose|/:fade|/:showlove|/:heart|/:break|/:cake|/:li|/:bome|/:kn|/:footb|/:ladybug|/:shit|/:moon|/:sun|/:gift|/:hug|/:strong|/:weak|/:share|/:v|/:@\\)|/:jj|/:@@|/:bad|/:lvu|/:no|/:ok|/:love|/:<L>|/:jump|/:shake|/:<O>|/:circle|/:kotow|/:turn|/:skip|/:oY|/:#-0|/:hiphot|/:kiss|/:<&|/:&>";  
	    Pattern p = Pattern.compile(qqfaceRegex);  
	    Matcher m = p.matcher(content);  
	    if (m.matches()) {  
	        result = true;  
	    }  
	    return result;  
	}  
	
}
