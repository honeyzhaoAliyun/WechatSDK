package com.ifp.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ifp.wechat.service.CoreService;
import com.ifp.wechat.service.SignService;

/**
 * 处理微信请求servlet
 * @author honey.zhao@aliyun.com
 * @version 1.0
 * 
 */
public class WeChatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 随机字符串
		String echostr = request.getParameter("echostr");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
			if (SignService.checkSignature(request)) {
				out.print(echostr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processWebchatRequest(request);

		// 响应消息
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(respMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}

}
