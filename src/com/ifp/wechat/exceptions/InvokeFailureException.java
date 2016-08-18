package com.ifp.wechat.exceptions;
/**
 * 
 * Title: Communication Exception
 * 
 * Author:  zhaoguoqing  
 *
 * Date: 2014-09-11
 * 
 * Description:
 * 
 */
public class InvokeFailureException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvokeFailureException() {
		super("访问失败！");
	}
	
	public InvokeFailureException(String message) {
		super(message);
	}
}
