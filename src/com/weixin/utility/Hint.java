package com.weixin.utility;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wjh
 *
 * @date 2013-9-17
 * 
 * 跳转封装的类
 * 
 * 1.alert方法
 * 2.刷新页面的方法
 * 
 * 参数：
 * path:跳转的地址
 * hinValue:alert的提示文字
 */
public class Hint {
	public static void hint(String hintValue, String path,HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (path != null)
			out.println("<script>alert('" + hintValue + "');top.location='" + path + "';</script>");
		else
			out.println("<script>alert('" + hintValue + "');</script>");
	}

	public static void reflash(String path, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>top.location='" + path + "';</script>");
	}
}