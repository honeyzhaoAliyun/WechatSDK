package br.com.rest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ifp.wechat.service.MenuService;

@WebServlet(urlPatterns = {"/menu/*"})
public class menuServlet extends HttpServlet {
	
	private static final long serialVersionUID = 7756097845779586921L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		//创建临时菜单---begin-------
		MenuService.deleteMenu();
		MenuService.createMenuKlcar();
		//创建临时菜单---end---------
		JSONObject jsonObject  = new JSONObject();
		jsonObject.accumulate("success", "菜单刷新成功……");
		PrintWriter out = resp.getWriter();
	    out.write(jsonObject.toString());
	}

}
