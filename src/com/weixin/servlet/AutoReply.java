package com.weixin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixin.service.AutoReplyService;

/** 
 * @author wjh E-mail: 472174314@qq.com
 * @version 创建时间：2013年10月11日 下午1:23:36 
 * 
 *
 */
public class AutoReply extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private AutoReplyService arService = new AutoReplyService();
	
	public AutoReply(){
		super();
	}
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//测试用1
		Integer unitID = 1;
		arService.autoReply(unitID);
	}

}
